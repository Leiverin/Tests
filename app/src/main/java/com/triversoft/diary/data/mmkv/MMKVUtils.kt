package com.triversoft.diary.data.mmkv

import com.tencent.mmkv.MMKV

object MMKVUtils {

    var isFirstOpen: Boolean
        get() = MMKV.defaultMMKV().decodeBool(MMKVKey.IS_FIRST_OPEN, true)
        set(value) { MMKV.defaultMMKV().encode(MMKVKey.IS_FIRST_OPEN, value) }
    var isAskedFirst: Boolean
        get() = MMKV.defaultMMKV().decodeBool(MMKVKey.IS_ASKED_FIRST, false)
        set(value) { MMKV.defaultMMKV().encode(MMKVKey.IS_ASKED_FIRST, value) }
    var isAskedTwo: Boolean
        get() = MMKV.defaultMMKV().decodeBool(MMKVKey.IS_ASKED_TWO, false)
        set(value) { MMKV.defaultMMKV().encode(MMKVKey.IS_ASKED_TWO, value) }
    var langCode: String
        get() = MMKV.defaultMMKV().decodeString(MMKVKey.LANG_CODE, "") ?: ""
        set(value) { MMKV.defaultMMKV().encode(MMKVKey.LANG_CODE, value) }


}