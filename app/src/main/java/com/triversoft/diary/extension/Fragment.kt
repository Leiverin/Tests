package com.triversoft.diary.extension

import android.view.View
import androidx.activity.addCallback
import androidx.fragment.app.Fragment

fun Fragment.setBackPressListener(vararg viewBack: View?, onClickBack: () -> Unit) {
    viewBack.forEach {
        it?.setPreventDoubleClick {
            onClickBack()
        }
    }
    activity?.onBackPressedDispatcher?.addCallback(viewLifecycleOwner, true) {
        onClickBack()
    }
}

