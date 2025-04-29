package com.triversoft.diary.ui.dialog

import android.content.Context
import com.triversoft.diary.R
import com.triversoft.diary.databinding.DialogTextInputBinding
import com.triversoft.diary.ui.base.BaseDialog

class TextInputDialog(
    private val context: Context
): BaseDialog<DialogTextInputBinding>(context) {

    override fun onViewReady() {
        initEvents()
    }

    private fun initEvents() {

    }

    override fun layoutRes(): Int = R.layout.dialog_text_input

}