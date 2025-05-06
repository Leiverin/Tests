package com.triversoft.diary.util

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.graphics.Bitmap
import android.graphics.Typeface
import android.graphics.drawable.Drawable
import android.net.Uri
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.airbnb.epoxy.EpoxyController
import com.airbnb.epoxy.EpoxyRecyclerView
import com.bumptech.glide.Glide
import com.triversoft.diary.itemPhotoSelected
import com.triversoft.diary.ui.create_diary.adapters.ImageUploadedAdapter
import java.io.File
import java.nio.ByteBuffer

@BindingAdapter("visible")
fun View.visible(isVisible: Boolean) {
    visibility = if (isVisible) View.VISIBLE else View.GONE
}

@BindingAdapter("invisible")
fun View.invisible(isInvisible: Boolean) {
    visibility = if (isInvisible) View.INVISIBLE else View.VISIBLE
}

@BindingAdapter("gone")
fun View.gone(isGone: Boolean) {
    visibility = if (isGone) View.GONE else View.VISIBLE
}

@BindingAdapter("marquee")
fun TextView.setMarquee(isMarquee: Boolean?) {
    if (isMarquee == true) {
        marqueeRepeatLimit = -1
        isSingleLine = true
        isSelected = true
    }
}
@BindingAdapter("textRes")
fun TextView.textRes(text: Int) {
    setText(text)
}

@BindingAdapter("visibleAnimAlpha")
fun View.visibleAnimAlpha(isVisible: Boolean) {
    animate().cancel()
    if (isVisible) {
        visibility = View.VISIBLE
        animate()
            .alpha(1f)
            .setDuration(300)
            .setListener(null)
    } else {
        animate()
            .alpha(0.0f)
            .setDuration(300)
            .setListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator) {
                    super.onAnimationEnd(animation)
                    visibility = View.GONE
                }
            })
    }
}

@BindingAdapter(
    value = ["visibleAnimTranslate", "onEndAnim"],
    requireAll = false
)
fun View.visibleAnimTranslate(isVisible: Boolean, onEnd: (() -> Unit)? = null) {
    animate().cancel()
    if (isVisible) {
        visibility = View.VISIBLE
        animate()
            .alpha(1f)
            .translationY(0f)
            .setDuration(300)
            .setListener(null)
    } else {
        animate()
            .alpha(0.0f)
            .translationY((height * 1).toFloat())
            .setDuration(300)
            .setListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator) {
                    super.onAnimationEnd(animation)
                    onEnd?.invoke()
                    visibility = View.GONE
                }
            })
    }
}

@BindingAdapter(
    value = ["url", "uri", "file", "drawableRes", "drawable", "bitmap", "byteArray", "byteBuffer"],
    requireAll = false
)
fun ImageView.loadImage(
    url: String? = null,
    uri: Uri? = null,
    file: File? = null,
    drawableRes: Int? = null,
    drawable: Drawable? = null,
    bitmap: Bitmap? = null,
    byteArray: ByteArray? = null,
    byteBuffer: ByteBuffer? = null,
) {
    when {
        url != null -> Glide.with(context).load(url).into(this)
        uri != null -> Glide.with(context).load(uri).into(this)
        file != null -> Glide.with(context).load(file).into(this)
        drawableRes != null -> Glide.with(context).load(drawableRes).into(this)
        drawable != null -> Glide.with(context).load(drawable).into(this)
        bitmap != null -> Glide.with(context).load(bitmap).into(this)
        byteArray != null -> Glide.with(context).load(byteArray).into(this)
        byteBuffer != null -> Glide.with(context).load(byteBuffer).into(this)
    }
}

@BindingAdapter("onPreventDoubleClick")
fun View.onPreventDoubleClick(action: () -> Unit){
    this.setOnClickListener {
        if (this.isEnabled){
            this.isEnabled = false
            action()
            this.postDelayed({this.isEnabled = true}, 500)
        }
    }
}

@BindingAdapter(
    value = ["fillData", "onClickPath", "onRemovePhoto"],
    requireAll = true
)
fun EpoxyRecyclerView.fillData(list: List<String>, onClickPath: (String) -> Unit, onRemovePhoto: (String) -> Unit){
    val controller = object : EpoxyController(){
        override fun buildModels() {
            list.forEachIndexed { _, s ->
                itemPhotoSelected {
                    id(s)
                    path(s)
                    isDefault(s == Constants.DEFAULT)
                    onClick { _ ->
                        onClickPath.invoke(s)
                    }
                    onDelete{ _ ->
                        onRemovePhoto.invoke(s)
                    }
                }
            }
        }
    }
    setController(controller)
    controller.requestModelBuild()
//    layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
//    adapter = ImageUploadedAdapter().apply {
//        submitData(list.toMutableList())
//        onClickPhoto = onClickPath
//    }
}

@BindingAdapter("loadDrawableWithAnim")
fun ImageView.loadDrawableWithAnim(resId: Int){
    animate().cancel()
    this.animate()
        .alpha(0f)
        .scaleY(0f)
        .scaleX(0f)
        .setDuration(150)
        .setListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator) {
                super.onAnimationEnd(animation)
                Glide.with(context).load(resId).into(this@loadDrawableWithAnim)
                this@loadDrawableWithAnim.animate()
                    .alpha(1f)
                    .scaleX(1f)
                    .scaleY(1f)
                    .setDuration(150)
            }
        })
}

@BindingAdapter("setBgColor")
fun CardView.setBgColor(color: Int){
    this.setCardBackgroundColor(color)
}

@BindingAdapter(
    value = ["path", "fontStyle"],
    requireAll = false
)
fun TextView.setFontRes(path: String, fontStyle: Int? = null){
    val typeface = Typeface.createFromAsset(context.assets, path)
    when(fontStyle){
        Constants.FONT_REGULAR -> setTypeface(typeface, Typeface.NORMAL)
        Constants.FONT_ITALIC -> setTypeface(typeface, Typeface.ITALIC)
        Constants.FONT_BOLD -> setTypeface(typeface, Typeface.BOLD)
        else -> setTypeface(typeface, Typeface.NORMAL)
    }
}

@BindingAdapter("setTintColor")
fun ImageView.setTintColor(color: Int){
    setColorFilter(color)
}