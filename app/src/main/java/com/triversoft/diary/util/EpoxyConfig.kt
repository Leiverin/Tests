package com.triversoft.diary.util

import com.airbnb.epoxy.EpoxyDataBindingLayouts
import com.triversoft.diary.R

@EpoxyDataBindingLayouts(
    value = [
        R.layout.item_language,
        R.layout.item_onboarding,
        R.layout.item_settings,
        R.layout.item_theme,
        R.layout.item_tb_home,
    ]
)
interface EpoxyConfig
