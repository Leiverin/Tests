package com.triversoft.diary.ui.popup

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.PopupWindow
import androidx.constraintlayout.widget.ConstraintLayout
import com.triversoft.diary.R
import com.triversoft.diary.databinding.LayoutOptionDiaryBinding
import com.triversoft.diary.extension.onGlobalLayout
import com.triversoft.diary.extension.screenWidth
import com.triversoft.diary.extension.setPreventDoubleClick

class AddContentPopup @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : PopupWindow(context, attrs) {

    private val binding = LayoutOptionDiaryBinding.inflate(context.applicationContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater)
    var onEventAddContentListener: IOnEventAddContentListener? = null

    init {
        setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        contentView = binding.root
        width = ConstraintLayout.LayoutParams.MATCH_PARENT
        height = context.resources.getDimension(com.intuit.sdp.R.dimen._95sdp).toInt()
        animationStyle = R.style.PopupAnimation
        isFocusable = true
        binding.btnTextbox.setPreventDoubleClick {
            onEventAddContentListener?.addTextbox()
        }
        binding.btnAddImage.setPreventDoubleClick {
            onEventAddContentListener?.addImage()
        }
        binding.btnAddCbx.setPreventDoubleClick {
            onEventAddContentListener?.addCheckbox()
        }
    }

    interface IOnEventAddContentListener{
        fun addTextbox()
        fun addImage()
        fun addCheckbox()
    }
}