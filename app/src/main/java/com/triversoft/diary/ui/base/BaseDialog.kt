package com.triversoft.diary.ui.base

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Context
import android.content.res.Resources
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.Window
import android.view.WindowManager
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding

abstract class BaseDialog<M: ViewDataBinding>(
    private val context: Context
): Dialog(context){

    protected var percentWidth = 0.85f

    protected abstract fun onViewReady()

    @LayoutRes
    protected abstract fun layoutRes(): Int

    protected lateinit var binding: M

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        binding = DataBindingUtil.inflate(LayoutInflater.from(context), layoutRes(), null, false)
        setContentView(binding.root)
        setCancelable(false)
        setLayout(widthPc = 0.85f, heightPc = 0.9f, isHeightWrap = true)
        onViewReady()
    }


    fun showDialog(){
        if (!this.isShowing){
            show()
        }
    }

    fun dismissDialog(){
        if (this.isShowing){
            dismiss()
        }
    }

    fun setLayout(widthPc: Float = 0.9f, heightPc: Float = 0.9f, isHeightWrap: Boolean = false) {
        val screenWith = Resources.getSystem().displayMetrics.widthPixels
        val screenHeight = Resources.getSystem().displayMetrics.heightPixels
        val params = window?.attributes
        params?.width = (screenWith * widthPc).toInt()
        params?.height = if (isHeightWrap){
            WindowManager.LayoutParams.WRAP_CONTENT
        }else{
            (screenHeight * heightPc).toInt()
        }
        window?.attributes = params
    }

    @SuppressLint("Recycle")
    fun setAnimationScale(root: View? = null){
        root?.let {
//            window?.setWindowAnimations(R.style.DialogZoomAnimation)
            // Scale
//            val anim = ObjectAnimator.ofFloat(0.0f, 1.15f, 1f)
//            anim.addUpdateListener { animator ->
//                val animate = animator.animatedValue as Float
//                root.scaleX = animate
//                root.scaleY = animate
//            }
//            // Fade
//            val fade = ObjectAnimator.ofFloat(0.7f, 1f)
//            fade.addUpdateListener { animator ->
//                val animate = animator.animatedValue as Float
//                root.alpha = animate
//            }
//            val set = AnimatorSet()
//            set.playTogether(anim, fade)
//            set.duration = 1200
//            set.interpolator = LinearInterpolator()
//            set.start()
        }
    }

}