package com.triversoft.diary.data.models

import android.graphics.Color
import android.os.Parcelable
import com.triversoft.diary.R
import com.triversoft.diary.extension.px
import com.triversoft.diary.util.CommonData
import kotlinx.parcelize.Parcelize

@Parcelize
data class DiaryModel(
    val id: Long,
    val title: String? = null,
    val mood: Int? = null,
    val weather: Int? = null,
    val timeCreated: Long? = null,
    val timeUpdated: List<Long> = arrayListOf(),
    val contents: List<Content> = arrayListOf()
): Parcelable{

    @Parcelize
    data class Content(
        val id: Long,
        val type: ContentType? = null,
        val text: String? = null,
        val isChecked: Boolean = false,
        val images: MutableList<String> = arrayListOf(),
        val priority: Int = -1,
        val textStyle: TextStyle? = null,
    ): Parcelable

    @Parcelize
    data class TextStyle(
        val fontRes: String? = null,
        val textColor: Int = Color.parseColor("#171717"),
        val fontStyle: FontStyle = FontStyle.NORMAL,
        val textAlign: TextAlign = TextAlign.START,
        val textSize: Float = 12f.px,
        val highlightColor: Int = Color.parseColor("#00FFFFFF"),
        val textHighLight: String = "",
    ): Parcelable

    fun getIconWeather(): Int{
        return CommonData.weatherData().find { it.first == weather }?.third ?: R.drawable.ic_sunny
    }

    fun getIconMood(): Int{
        return CommonData.moods().find { it.first == mood }?.second ?: R.drawable.ic_normal_mood
    }

}