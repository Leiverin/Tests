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
import android.view.ViewGroup
import android.view.Window
import android.view.WindowManager
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager

abstract class BaseDialogFragment<M: ViewDataBinding>: DialogFragment(){

    protected var percentWidth = 0.85f

    protected abstract fun onViewReady()

    @LayoutRes
    protected abstract fun layoutRes(): Int

    protected lateinit var binding: M

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dialog?.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        binding = DataBindingUtil.inflate(layoutInflater, layoutRes(), null, false)
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setCancelable(false)
        setLayout(widthPc = 0.85f, heightPc = 0.9f, isHeightWrap = true)
        onViewReady()
    }

    fun setLayout(widthPc: Float = 0.9f, heightPc: Float = 0.9f, isHeightWrap: Boolean = false) {
        val screenWith = Resources.getSystem().displayMetrics.widthPixels
        val screenHeight = Resources.getSystem().displayMetrics.heightPixels
        val params = dialog?.window?.attributes
        params?.width = (screenWith * widthPc).toInt()
        params?.height = if (isHeightWrap){
            WindowManager.LayoutParams.WRAP_CONTENT
        }else{
            (screenHeight * heightPc).toInt()
        }
        dialog?.window?.attributes = params
    }

    fun dismissImmediately(){
        if (isAdded && this.dialog?.isShowing != false){
            dismiss()
        }
    }

}