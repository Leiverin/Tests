package com.triversoft.diary.extension

import android.content.res.Resources
import java.util.Locale

val Float.dp: Float get() = this / Resources.getSystem().displayMetrics.density

val Float.px: Float get() = this * Resources.getSystem().displayMetrics.density

val screenWidth = Resources.getSystem().displayMetrics.widthPixels

val screenHeight = Resources.getSystem().displayMetrics.heightPixels

fun Long?.getFormattedDuration(forceShowHours: Boolean = false): String {
    if (this == null) return "null"
    val sb = StringBuilder(8)
    val hours = (this / 1000) / 3600
    val minutes = (this / 1000) % 3600 / 60
    val seconds = (this / 1000) % 60

    if (this >= 3600) {
        sb.append(String.format(Locale.getDefault(), "%02d", hours)).append(":")
    } else if (forceShowHours) {
        sb.append("00:")
    }

    sb.append(String.format(Locale.getDefault(), "%02d", minutes))
    sb.append(":").append(String.format(Locale.getDefault(), "%02d", seconds))
    return sb.toString()
}