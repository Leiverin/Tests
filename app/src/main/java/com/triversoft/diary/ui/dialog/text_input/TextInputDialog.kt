package com.triversoft.diary.ui.dialog.text_input

import android.content.Context
import androidx.fragment.app.viewModels
import com.triversoft.diary.R
import com.triversoft.diary.databinding.DialogTextInputBinding
import com.triversoft.diary.ui.base.BaseDialog
import com.triversoft.diary.ui.base.BaseDialogFragment

class TextInputDialog: BaseDialogFragment<DialogTextInputBinding>() {

    private val viewModel: TextInputViewModel by viewModels()

    override fun onViewReady() {
        initData()
        initEvents()
    }

    private fun initData() {

    }

    private fun initEvents() {

    }

    override fun layoutRes(): Int = R.layout.dialog_text_input

}