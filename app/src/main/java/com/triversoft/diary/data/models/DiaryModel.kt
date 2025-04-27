/**
 * @author Boom Team
 * Created 4/27/2025 at 3:09 PM
 */

package com.triversoft.diary.data.models

data class DiaryModel(
    val id: Int,
    val title: String? = null,
    val mood: Int? = null,
    val weather: Int? = null,
    val timeCreated: Long? = null,
    val timeUpdated: List<Long> = arrayListOf(),
    val contents: List<Content>
){
    data class Content(
        val id: Int,
        val type: ContentType? = null,
        val text: String? = null,
        val image: List<String> = arrayListOf(),
        val priority: Int = -1,
    )
}