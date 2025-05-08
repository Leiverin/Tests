package com.triversoft.diary.ui.views

import android.content.Context
import android.util.AttributeSet

class TextStyleEditText @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : androidx.appcompat.widget.AppCompatEditText(context, attrs) {

    var onSelectionChanged: ((Int, Int) -> Unit)? = { _, _ -> }

    override fun onSelectionChanged(selStart: Int, selEnd: Int) {
        super.onSelectionChanged(selStart, selEnd)
        // Bôi đen start & end
        onSelectionChanged?.invoke(selStart, selEnd)
    }

}