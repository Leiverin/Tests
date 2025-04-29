package com.triversoft.diary.util

import android.content.Context
import com.triversoft.diary.R
import com.triversoft.diary.data.models.Language
import com.triversoft.diary.data.models.ThemeObj

object CommonData {

    fun languages() = arrayListOf(
        Language("عربي", "ar", R.drawable.ic_arabic_flag),
        Language("हिंदी", "hi", R.drawable.ic_hindi_flag),
        Language("čeština", "cs", R.drawable.ic_czech_flag),
        Language("Deutsch", "de", R.drawable.ic_german_flag),
        Language("English", "en", R.drawable.ic_english_flag),
        Language("한국인", "ko", R.drawable.ic_korean_flag),
        Language("日本語", "ja", R.drawable.ic_japan_flag),
        Language("中國人", "zh", R.drawable.ic_china_flag),
//        Language("Dansk", "da", R.drawable.danish),
        Language("Français", "fr", R.drawable.ic_france_flag),
//        Language("Indonesia", "id", R.drawable.indonesia),
//        Language("فارسی", "fa", R.drawable.persian),
        Language("ไทย", "th", R.drawable.ic_thailand_flag),
        Language("Tiếng Việt", "vi", R.drawable.ic_vietnam_flag),
    )

    fun onboardings(context: Context) = arrayListOf(
        Triple(
            R.drawable.iv_ob_1,
            R.string.title_ob_1,
            R.string.content_ob_1
        ),
        Triple(
            R.drawable.iv_ob_2,
          R.string.title_ob_2,
            R.string.content_ob_2
        ),
        Triple(
            R.drawable.iv_ob_3,
           R.string.title_ob_3,
            R.string.content_ob_3
        ),
        Triple(
            R.drawable.iv_ob_4,
           R.string.title_ob_4,
            R.string.content_ob_4
        ),
    )

    fun themes( ) = arrayListOf(
        ThemeObj(
            "light",R.drawable.iv_theme_1,"Light","#FFFFFF",false,false
        ),
        ThemeObj(
            "dark",R.drawable.iv_theme_2,"Dark","#6B6B6B",false,false
        ),
        ThemeObj(
            "heart_bloom",R.drawable.iv_theme_3,"Heart Bloom","#E899A4",false,false
        ),
        ThemeObj(
            "cloud_field",R.drawable.iv_theme_4,"Cloud Field","#59B5C9",true,false
        ),
        ThemeObj(
            "starry_dream",R.drawable.iv_theme_5,"Starry Dream","#A18CE0",true,false
        ),
        ThemeObj(
            "jelly_sea",R.drawable.iv_theme_6,"Jelly Sea","#6699CC",true,false
        ),
        ThemeObj(
            "sunny_joy",R.drawable.iv_theme_7,"Sunny Joy","#ECB64D",true,false
        ),
        ThemeObj(
            "cosmic_night",R.drawable.iv_theme_8,"Cosmic Night","#6B6B6B",true,false
        ),
        ThemeObj(
            "evening_stop",R.drawable.iv_theme_9,"Evening Stop","#6B6B6B",true,false
        )
    )
    fun suggestion(context: Context) = arrayListOf(
        Triple(
            R.drawable.sg_1,
            R.string.title_suggestion_1,
            context.getString(R.string.content_suggestion_1)
        ),
        Triple(
            R.drawable.sg_2,
          R.string.title_suggestion_2,
                  context.getString(R.string.content_suggestion_2)
        ),
        Triple(
            R.drawable.sg_3,
            R.string.title_suggestion_3,
            context.getString(R.string.content_suggestion_3)
        ),
    )

    fun settings(context: Context?) = arrayListOf(
        Pair(
            R.drawable.ic_policy,
            context?.resources?.getString(R.string.privacy_policy) ?: "Privacy Policy"
        ),
        Pair(
            R.drawable.ic_language,
            context?.resources?.getString(R.string.language) ?: "Language"
        ),
        Pair(
            R.drawable.ic_share,
            context?.resources?.getString(
                R.string.share,
                context.resources.getString(R.string.app_name)
            ) ?: "Share GOLD DETECTOR"
        ),
        Pair(
            R.drawable.ic_feedback,
            context?.resources?.getString(R.string.feedback) ?: "Feedback"
        ),
    )


}