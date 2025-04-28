/**
 * @author Boom Team
 * Created 4/28/2025 at 9:47 AM
 */

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
import com.triversoft.diary.extension.screenWidth
import com.triversoft.diary.extension.setPreventDoubleClick

class AddContentPopup @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : PopupWindow(context, attrs) {

    private val binding = LayoutOptionDiaryBinding.inflate(LayoutInflater.from(context))
    var onEventAddContentListener: IOnEventAddContentListener? = null

    init {
        setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        contentView = binding.root
        width = screenWidth
        height = ConstraintLayout.LayoutParams.WRAP_CONTENT
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