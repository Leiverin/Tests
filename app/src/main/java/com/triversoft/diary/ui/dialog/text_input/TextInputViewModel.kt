package com.triversoft.diary.ui.dialog.text_input

import android.graphics.Color
import androidx.lifecycle.MutableLiveData
import com.triversoft.diary.data.caching.MMKVCache
import com.triversoft.diary.data.models.text.FeatureExpandType
import com.triversoft.diary.data.models.text.FontStyle
import com.triversoft.diary.data.models.text.TextAlign
import com.triversoft.diary.data.models.text.TextColorMode
import com.triversoft.diary.data.models.text.TextFont
import com.triversoft.diary.data.models.text.TextType
import com.triversoft.diary.ui.base.BaseViewModel
import com.triversoft.diary.util.CommonData

class TextInputViewModel: BaseViewModel() {
    val colors = MutableLiveData(MMKVCache.colors)
    val fonts = MutableLiveData(MMKVCache.fonts)

    val fontCurrent = MutableLiveData(MMKVCache.fonts.first())

    val textColorCurrent = MutableLiveData(Color.parseColor("#171717"))
    val textHighlightColorCurrent = MutableLiveData(Color.parseColor("#171717"))
    val textColorMode = MutableLiveData(TextColorMode.NORMAL)

    val alignCurrent = MutableLiveData(TextAlign.START)
    val featureExpandType = MutableLiveData(FeatureExpandType.NONE)
    val textType = MutableLiveData(TextType.CONTENT)

    val fontStyles = MutableLiveData<ArrayList<FontStyle>>()
    private val _fontStyles = arrayListOf<FontStyle>()

    fun toggleStyle(style: FontStyle){
        if (!_fontStyles.contains(style)){
            _fontStyles.add(style)
        }else{
            _fontStyles.remove(style)
        }
        fontStyles.value = _fontStyles
    }

    fun toggleFontOpt() {
        if (featureExpandType.value != FeatureExpandType.FONT){
            featureExpandType.value = FeatureExpandType.FONT
            textColorMode.value = TextColorMode.NORMAL
        }else{
            featureExpandType.value = FeatureExpandType.NONE
            textColorMode.value = TextColorMode.NORMAL
        }
    }

    fun toggleTextColorOpt() {
        if (textColorMode.value != TextColorMode.TEXT_COLOR){
            textColorMode.value = TextColorMode.TEXT_COLOR
            featureExpandType.value = FeatureExpandType.PICK_COLOR
        }else{
            featureExpandType.value = FeatureExpandType.NONE
            textColorMode.value = TextColorMode.NORMAL
        }
    }

    fun toggleTextHighlightOpt() {
        if (textColorMode.value != TextColorMode.TEXT_HIGHLIGHT_COLOR){
            textColorMode.value = TextColorMode.TEXT_HIGHLIGHT_COLOR
            featureExpandType.value = FeatureExpandType.PICK_COLOR
        }else{
            featureExpandType.value = FeatureExpandType.NONE
            textColorMode.value = TextColorMode.NORMAL
        }
    }

    fun colorSelected(colorCode: Int): Boolean{
        return when(textColorMode.value){
            TextColorMode.TEXT_COLOR -> textColorCurrent.value == colorCode
            TextColorMode.TEXT_HIGHLIGHT_COLOR -> textHighlightColorCurrent.value == colorCode
            else -> false
        }
    }


}