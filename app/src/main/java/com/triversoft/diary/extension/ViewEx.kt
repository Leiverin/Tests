package com.triversoft.diary.extension

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.content.Context
import android.content.res.Resources
import android.graphics.Rect
import android.util.DisplayMetrics
import android.util.Log
import android.util.TypedValue
import android.view.View
import android.view.ViewTreeObserver
import android.view.animation.LinearInterpolator
import android.view.inputmethod.InputMethodManager
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat

fun View.beGone(){
    this.visibility = View.GONE
}

fun View.beInvisible(){
    this.visibility = View.INVISIBLE
}

fun View.beVisible(){
    this.visibility = View.VISIBLE
}
fun Float.roundTo2Decimals(): String {
    return String.format("%.2f", this)
}
fun TextView.setColorRes(resId: Int){
    this.setTextColor(ContextCompat.getColor(this.context, resId))
}

fun ImageView.setColorRes(resId: Int){
    this.setColorFilter(ContextCompat.getColor(this.context, resId))
}

fun View.setPreventDoubleClick(timeDelay: Long = 500, action: (View) -> Unit){
    this.setOnClickListener {
        if (this.isEnabled){
            this.isEnabled = false
            action(it)
            this.postDelayed({this.isEnabled = true}, timeDelay)
        }
    }
}

fun View.showKeyboard() {
    val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.showSoftInput(this, 0)
}
fun View.hideKeyboard() {
    val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(windowToken, 0)
}

fun View.onGlobalLayout(callback: () -> Unit) {
    viewTreeObserver?.addOnGlobalLayoutListener(object : ViewTreeObserver.OnGlobalLayoutListener {
        override fun onGlobalLayout() {
            if (viewTreeObserver != null) {
                viewTreeObserver.removeOnGlobalLayoutListener(this)
                callback()
            }
        }
    })
}

fun View.makeAnimRotation(time: Long = 3000L, isFinite: Boolean = true){
    ObjectAnimator.ofFloat(this, "rotation", 0f, 360f).apply {
        duration = time
        if (isFinite) repeatCount = ValueAnimator.INFINITE
        interpolator = LinearInterpolator()
    }.start()
}

fun View.makeAnimScale(time: Long = 1500L, scale: Float = 1.3f, isFinite: Boolean = true){
    val animator = ValueAnimator.ofFloat(1f, scale, 1f).apply {
        duration = time
        interpolator = LinearInterpolator()
        addUpdateListener { animation ->
            val value = animation.animatedValue as Float
            this@makeAnimScale.scaleX = value
            this@makeAnimScale.scaleY = value
        }
        if (isFinite) repeatCount = ValueAnimator.INFINITE
    }
    animator.start()
}

fun View.dp(number: Number): Float {
    val metric =
        getDisplayMetric(context)

    return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, number.toFloat(), metric)
}

fun getDisplayMetric(context: Context?): DisplayMetrics {
    return if (context != null) context.resources.displayMetrics else Resources.getSystem().displayMetrics
}

fun View.visibleAnimTranslate(isVisible: Boolean, duration: Long = 300, onEnd: (() -> Unit)? = null) {
    animate().cancel()
    if (isVisible) {
        visibility = View.VISIBLE
        animate()
            .alpha(1f)
            .translationY(0f)
            .setDuration(duration)
            .setListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator) {
                    super.onAnimationEnd(animation)
                    onEnd?.invoke()
                }
            })
    } else {
        animate()
            .alpha(0.0f)
            .translationY((height * 1).toFloat())
            .setDuration(duration)
            .setListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator) {
                    super.onAnimationEnd(animation)
                    onEnd?.invoke()
                    visibility = View.GONE
                }
            })
    }
}

fun View.observeKeyboardVisibility(onKeyboardVisibilityChanged: (Boolean) -> Unit) {
    val rootView = this
    var isKeyboardVisible = false
    rootView.viewTreeObserver.addOnGlobalLayoutListener {
        val rect = Rect()
        rootView.getWindowVisibleDisplayFrame(rect)
        val keypadHeight = screenHeight - rect.bottom
        val isVisibleNow = keypadHeight > screenHeight * 0.15

        if (isVisibleNow != isKeyboardVisible) {
            isKeyboardVisible = isVisibleNow
            onKeyboardVisibilityChanged(isKeyboardVisible)
        }
    }
}