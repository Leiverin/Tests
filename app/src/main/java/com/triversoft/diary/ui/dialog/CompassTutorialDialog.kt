package com.triversoft.diary.ui.dialog

import android.content.Context
import com.triversoft.diary.databinding.DialogTutorialCompassBinding
import com.triversoft.diary.ui.base.BaseDialog
import com.triversoft.diary.R
import com.triversoft.diary.extension.setPreventDoubleClick

class CompassTutorialDialog(
    private val context: Context
): BaseDialog<DialogTutorialCompassBinding>(context) {

    override fun onViewReady() {
        initEvents()
    }

    private fun initEvents() {
        binding.btnGotIt.setPreventDoubleClick {
            dismissDialog()
        }
    }

    override fun layoutRes(): Int = R.layout.dialog_tutorial_compass

}