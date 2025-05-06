package com.triversoft.diary.util

import com.airbnb.epoxy.EpoxyDataBindingLayouts
import com.triversoft.diary.R

@EpoxyDataBindingLayouts(
    value = [
        R.layout.item_language,
        R.layout.item_onboarding,
        R.layout.item_settings,
        R.layout.item_mood,
        R.layout.item_weather,
        R.layout.item_checkbox,
        R.layout.item_textbox,
        R.layout.item_list_photo_selected,
        R.layout.item_photo_selected,
        R.layout.item_button_add_content,
        R.layout.item_color_text_input,
        R.layout.item_font_text_input,
    ]
)
interface EpoxyConfig
