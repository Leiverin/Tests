package com.triversoft.diary.extension

import android.view.View
import androidx.activity.addCallback
import androidx.fragment.app.Fragment

fun Fragment.setBackPressListener(viewBack: View? = null, onClickBack: () -> Unit) {
    viewBack?.setPreventDoubleClick {
        onClickBack()
    }
    activity?.onBackPressedDispatcher?.addCallback(viewLifecycleOwner, true) {
        onClickBack()
    }
}

