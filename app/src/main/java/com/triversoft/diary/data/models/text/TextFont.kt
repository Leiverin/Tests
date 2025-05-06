package com.triversoft.diary.data.models.text

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class TextFont(
    val id: Int,
    val text: String = "",
    val parentPath: String = "",
    val paths: ArrayList<String> = arrayListOf()
): Parcelable{

    fun getPathRegular(): String {
        return paths.firstOrNull { it.contains("regular") } ?: paths.lastOrNull() ?: ""
    }

    fun getPathBold(): String{
        return paths.firstOrNull { it.contains("bold") } ?: paths.firstOrNull() ?: ""
    }

    fun getPathItalic(): String{
        return paths.firstOrNull { it.contains("italic") } ?: paths.lastOrNull() ?: ""
    }

}