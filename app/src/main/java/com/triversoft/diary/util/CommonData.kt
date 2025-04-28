package com.triversoft.diary.util

import android.content.Context
import com.triversoft.diary.R
import com.triversoft.diary.data.models.Language

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
        Pair(R.drawable.on_broading_1, R.string.string_content_onboarding_first),
        Pair(R.drawable.on_broading_2, R.string.string_content_onboarding_second),
        Pair(R.drawable.on_broading_3, R.string.string_content_onboarding_third),
    )

    fun suggestion(context: Context) = arrayListOf(
        Triple(R.drawable.sg_1, R.string.title_suggestion_1, context.getString(R.string.content_suggestion_1)),
        Triple(R.drawable.sg_2, R.string.title_suggestion_2, context.getString(R.string.content_suggestion_2)),
        Triple(R.drawable.sg_3, R.string.title_suggestion_3, context.getString(R.string.content_suggestion_3)),
    )

    fun settings(context: Context?) = arrayListOf(
        Pair(R.drawable.ic_policy, context?.resources?.getString(R.string.privacy_policy) ?: "Privacy Policy"),
        Pair(R.drawable.ic_language, context?.resources?.getString(R.string.language) ?: "Language"),
        Pair(R.drawable.ic_share, context?.resources?.getString(
                R.string.share,
                context.resources.getString(R.string.app_name)
            ) ?: "Share GOLD DETECTOR"),
        Pair(R.drawable.ic_feedback, context?.resources?.getString(R.string.feedback) ?: "Feedback"),
    )

    fun moods() = arrayListOf(
        Pair(0, R.drawable.ic_happy_mood),
        Pair(1, R.drawable.ic_normal_mood),
        Pair(2, R.drawable.ic_very_happy_mood),
        Pair(3, R.drawable.ic_angry_mood),
        Pair(4, R.drawable.ic_sorrow_mood),
        Pair(5, R.drawable.ic_cry_mood),
    )

    fun weatherData(context: Context? = null) = arrayListOf(
        Triple(0, context?.getString(R.string.sunny) ?: "Sunny", R.drawable.ic_sunny),
        Triple(1, context?.getString(R.string.rainy) ?: "Rainy", R.drawable.ic_ranny),
        Triple(2, context?.getString(R.string.cloudy) ?: "Cloudy", R.drawable.ic_cloudy),
        Triple(3, context?.getString(R.string.foggy) ?: "Foggy", R.drawable.ic_foggy),
        Triple(4, context?.getString(R.string.cold) ?: "Cold", R.drawable.ic_cold),
        Triple(5, context?.getString(R.string.windy) ?: "Windy", R.drawable.ic_windy),
    )

}