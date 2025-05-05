package com.triversoft.diary.ui.dialog.text_input

import androidx.lifecycle.MutableLiveData
import com.triversoft.diary.data.models.TextAlign
import com.triversoft.diary.ui.base.BaseViewModel

class TextInputViewModel: BaseViewModel() {
    val fontCurrent = MutableLiveData(0)
    val textColorCurrent = MutableLiveData<Int>()
    val textHighlightColorCurrent = MutableLiveData<Int>()
    val alignCurrent = MutableLiveData<TextAlign>()
    val isBold = MutableLiveData<Boolean>()
    val isItalic = MutableLiveData<Boolean>()
    val isUnderline = MutableLiveData<Boolean>()
    val isStrikeThrough = MutableLiveData<Boolean>()
}