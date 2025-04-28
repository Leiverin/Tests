/**
 * @author Boom Team
 * Created 4/27/2025 at 3:09 PM
 */

package com.triversoft.diary.data.models

import com.triversoft.diary.R
import com.triversoft.diary.util.CommonData

data class DiaryModel(
    val id: Long,
    val title: String? = null,
    val mood: Int? = null,
    val weather: Int? = null,
    val timeCreated: Long? = null,
    val timeUpdated: List<Long> = arrayListOf(),
    val contents: List<Content> = arrayListOf()
){
    data class Content(
        val id: Long,
        val type: ContentType? = null,
        val text: String? = null,
        val isChecked: Boolean = false,
        val images: MutableList<String> = arrayListOf(),
        val priority: Int = -1,
    )

    fun getIconWeather(): Int{
        return CommonData.weatherData().find { it.first == weather }?.third ?: R.drawable.ic_sunny
    }
    fun getIconMood(): Int{
        return CommonData.moods().find { it.first == mood }?.second ?: R.drawable.ic_normal_mood
    }

}