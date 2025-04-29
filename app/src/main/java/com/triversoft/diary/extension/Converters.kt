package com.triversoft.diary.extension

import android.content.Context
import android.net.Uri
import java.io.File
import java.io.FileOutputStream

fun Uri.getId(): String{
    return this.toString().substringAfterLast("/")
}

fun Uri.toPath(context: Context): String? {
    val inputStream = context.contentResolver.openInputStream(this) ?: return null
    val file = File(context.cacheDir, "photo_${this.getId()}.png")
    if (file.exists()) return file.absolutePath
    val outputStream = FileOutputStream(file)
    inputStream.use { input ->
        outputStream.use { output ->
            input.copyTo(output)
        }
    }
    return file.absolutePath
}