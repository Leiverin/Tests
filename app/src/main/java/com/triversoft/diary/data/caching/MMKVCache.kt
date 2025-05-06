package com.triversoft.diary.data.caching

import com.google.common.reflect.TypeToken
import com.google.gson.Gson
import com.tencent.mmkv.MMKV
import com.triversoft.diary.data.mmkv.MMKVKey
import com.triversoft.diary.data.models.text.TextFont
import com.triversoft.diary.extension.decodeListParcelable
import com.triversoft.diary.extension.encodeListParcelable
import com.triversoft.diary.util.CommonData

object MMKVCache {

    var colors: ArrayList<Int>
        get() {
            val jsonString = MMKV.defaultMMKV().decodeString(MMKVKey.LIST_COLOR)
            return if (jsonString == null) CommonData.colors()
            else Gson().fromJson(jsonString, object : TypeToken<List<Int>>() {}.type) ?: CommonData.colors()
        }
        set(value){
            val json = Gson().toJson(value)
            MMKV.defaultMMKV().encode(MMKVKey.LIST_COLOR, json)
        }

    var fonts: List<TextFont>
        get() = MMKV.defaultMMKV().decodeListParcelable(MMKVKey.LIST_FONT, listOf()) ?: listOf()
        set(value){
            MMKV.defaultMMKV().encodeListParcelable(MMKVKey.LIST_FONT, value)
        }

}