package com.triversoft.diary.ui.home

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.core.content.ContextCompat
import com.daimajia.androidanimations.library.Techniques
import com.daimajia.androidanimations.library.YoYo
import com.triversoft.diary.R
import com.triversoft.diary.extension.beVisible
import com.triversoft.diary.extension.setPreventDoubleClick
import com.triversoft.diary.itemTbHome
import com.triversoft.diary.util.loadImage

fun HomeFragment.initData() {
    binding.apply {
        view?.post {
            btnWrite.setPreventDoubleClick {
                if (a == 9) {
                    a = 1
                } else {
                    a++
                }
                when (a) {
                    1 -> {
                        theme = "light"
                    }

                    2 -> {
                        theme = "dark"
                    }

                    3 -> {
                        theme = "heart_bloom"
                    }

                    4 -> {
                        theme = "cloud_field"
                    }

                    5 -> {
                        theme = "starry_dream"
                    }

                    6 -> {
                        theme = "jelly_sea"
                    }

                    7 -> {
                        theme = "sunny_joy"
                    }

                    8 -> {
                        theme = "cosmic_night"
                    }

                    9 -> {
                        theme = "evening_stop"
                    }
                }
                themeApp()
            }
            themeApp()
        }
    }
}

fun HomeFragment.initRvHome() {
    binding.rcvHome.withModels {
        itemTbHome {
            id("tbHome")
            isWhite(isWhiteTheme)
            onClick { _ -> }
        }

//        languages.forEachIndexed { index, language ->
//            itemLanguage {
//                id(index)
//                resId(language.flag)
//                name(language.name)
//                isSelected(langCode == language.code)
//                onClick { _ ->
//                    handleClickLanguage(language)
//                }
//            }
//        }
    }
    showEmpty()

}

fun HomeFragment.hideEmpty() {

}
fun HomeFragment.showEmpty() {
    binding.apply {
        ivEmpty.post{
        ivEmpty.beVisible()
        ivJellyChatTop.beVisible()
        tvChatTop.beVisible()
        tvChatTop.animateText(getString(R.string.home_empty_chat_1))
        ivEmpty.postDelayed({
            ivJellyChatBot.beVisible()
            tvChatBot.beVisible()
            tvChatBot.animateText(getString(R.string.home_empty_chat_2))
            btnGo.beVisible()

        }, 2000)
        YoYo.with(Techniques.Pulse).duration(1500).repeat(-1).playOn(ivEmpty)
    }
    }
}

fun HomeFragment.themeApp() {
    binding.apply {
        btnTemplate.colorFilter = null
        btnFavorite.colorFilter = null
        btnDashBoard.colorFilter = null
        btnSetting.colorFilter = null
        isWhiteTheme = true
        bgBottomHome.loadImage(drawableRes = R.drawable.bg_bottom_home)

        when (theme) {
            "light" -> {
                btnWrite.loadImage(drawableRes = R.drawable.ic_h_write_light)

                btnTemplate.loadImage(drawableRes = R.drawable.ic_h_template_light)
                btnFavorite.loadImage(drawableRes = R.drawable.ic_h_favorite_light)
                btnDashBoard.loadImage(drawableRes = R.drawable.ic_h_dash_light)
                btnSetting.loadImage(drawableRes = R.drawable.ic_h_setting_light)



                btnTemplate.setColorFilter(Color.parseColor("#171717"))
                btnFavorite.setColorFilter(Color.parseColor("#171717"))
                btnDashBoard.setColorFilter(Color.parseColor("#171717"))
                btnSetting.setColorFilter(Color.parseColor("#171717"))
                bgEffectHome.alpha = 0f
                bgHome.loadImage(
                    drawable = ColorDrawable(
                        ContextCompat.getColor(
                            requireContext(),
                            R.color.bg_light
                        )
                    )
                )
            }

            "dark" -> {
                bgHome.loadImage(
                    drawable = ColorDrawable(
                        ContextCompat.getColor(
                            requireContext(),
                            R.color.black
                        )
                    )
                )


                btnWrite.loadImage(drawableRes = R.drawable.ic_h_write_dark)

                btnTemplate.loadImage(drawableRes = R.drawable.ic_h_template_light)
                btnFavorite.loadImage(drawableRes = R.drawable.ic_h_favorite_light)
                btnDashBoard.loadImage(drawableRes = R.drawable.ic_h_dash_light)
                btnSetting.loadImage(drawableRes = R.drawable.ic_h_setting_light)

                isWhiteTheme = false

                bgBottomHome.loadImage(drawableRes = R.drawable.bg_bottom_home_dark)
                btnTemplate.setColorFilter(Color.parseColor("#FFFFFF"))
                btnFavorite.setColorFilter(Color.parseColor("#FFFFFF"))
                btnDashBoard.setColorFilter(Color.parseColor("#FFFFFF"))
                btnSetting.setColorFilter(Color.parseColor("#FFFFFF"))

                bgEffectHome.alpha = 0f
            }

            "heart_bloom" -> {

                bgHome.loadImage(drawableRes = R.drawable.bg_heart_bloom)
                btnWrite.loadImage(drawableRes = R.drawable.ic_h_write_heart_bloom)

                btnTemplate.loadImage(drawableRes = R.drawable.ic_h_template_heart_bloom)
                btnFavorite.loadImage(drawableRes = R.drawable.ic_h_favorite_heart_bloom)
                btnDashBoard.loadImage(drawableRes = R.drawable.ic_h_dash_heart_bloom)
                btnSetting.loadImage(drawableRes = R.drawable.ic_h_setting_heart_bloom)

                bgEffectHome.loadImage(
                    drawable = ColorDrawable(
                        ContextCompat.getColor(
                            requireContext(),
                            R.color.white
                        )
                    )
                )
                bgEffectHome.alpha = 0.2f
            }

            "cloud_field" -> {
                bgHome.loadImage(drawableRes = R.drawable.bg_cloud_field)
                btnWrite.loadImage(drawableRes = R.drawable.ic_h_write_light)
                btnTemplate.loadImage(drawableRes = R.drawable.ic_h_template_cloud_field)
                btnFavorite.loadImage(drawableRes = R.drawable.ic_h_favorite_cloud_field)
                btnDashBoard.loadImage(drawableRes = R.drawable.ic_h_dash_cloud_field)
                btnSetting.loadImage(drawableRes = R.drawable.ic_h_setting_cloud_field)


                bgEffectHome.loadImage(
                    drawable = ColorDrawable(
                        ContextCompat.getColor(
                            requireContext(),
                            R.color.white
                        )
                    )
                )
                bgEffectHome.alpha = 0.2f
            }

            "starry_dream" -> {
                bgHome.loadImage(drawableRes = R.drawable.bg_starry_dream)
                btnWrite.loadImage(drawableRes = R.drawable.ic_h_write_starry_dream)
                btnTemplate.loadImage(drawableRes = R.drawable.ic_h_template_starry_dream)
                btnFavorite.loadImage(drawableRes = R.drawable.ic_h_favorite_starry_dream)
                btnDashBoard.loadImage(drawableRes = R.drawable.ic_h_dash_starry_dream)
                btnSetting.loadImage(drawableRes = R.drawable.ic_h_setting_starry_dream)

                isWhiteTheme = false
                bgEffectHome.loadImage(
                    drawable = ColorDrawable(
                        ContextCompat.getColor(
                            requireContext(),
                            R.color.white
                        )
                    )
                )
                bgEffectHome.alpha = 0.5f
            }

            "jelly_sea" -> {
                bgHome.loadImage(drawableRes = R.drawable.bg_jelly_sea)
                btnWrite.loadImage(drawableRes = R.drawable.ic_h_write_light)
                btnTemplate.loadImage(drawableRes = R.drawable.ic_h_template_jelly_sea)
                btnFavorite.loadImage(drawableRes = R.drawable.ic_h_favorite_jelly_sea)
                btnDashBoard.loadImage(drawableRes = R.drawable.ic_h_dash_jelly_sea)
                btnSetting.loadImage(drawableRes = R.drawable.ic_h_setting_jelly_sea)

                bgEffectHome.loadImage(
                    drawable = ColorDrawable(
                        ContextCompat.getColor(
                            requireContext(),
                            R.color.white
                        )
                    )
                )
                bgEffectHome.alpha = 0.2f
            }

            "sunny_joy" -> {
                bgHome.loadImage(drawableRes = R.drawable.bg_sunny_joy)
                btnWrite.loadImage(drawableRes = R.drawable.ic_h_write_sunny_joy)
                btnTemplate.loadImage(drawableRes = R.drawable.ic_h_template_sunny_joy)
                btnFavorite.loadImage(drawableRes = R.drawable.ic_h_favorite_sunny_joy)
                btnDashBoard.loadImage(drawableRes = R.drawable.ic_h_dash_sunny_joy)
                btnSetting.loadImage(drawableRes = R.drawable.ic_h_setting_sunny_joy)

                bgEffectHome.loadImage(
                    drawable = ColorDrawable(
                        ContextCompat.getColor(
                            requireContext(),
                            R.color.white
                        )
                    )
                )
                bgEffectHome.alpha = 0.5f
            }

            "cosmic_night" -> {
                bgHome.loadImage(drawableRes = R.drawable.bg_cosmic_night)

                btnWrite.loadImage(drawableRes = R.drawable.ic_h_write_dark)
                btnTemplate.loadImage(drawableRes = R.drawable.ic_h_template_cosmic_night)
                btnFavorite.loadImage(drawableRes = R.drawable.ic_h_favorite_cosmic_night)
                btnDashBoard.loadImage(drawableRes = R.drawable.ic_h_dash_cosmic_night)
                btnSetting.loadImage(drawableRes = R.drawable.ic_h_setting_cosmic_night)
                bgBottomHome.loadImage(drawableRes = R.drawable.bg_bottom_home_dark)
                isWhiteTheme = false
                bgEffectHome.loadImage(
                    drawable = ColorDrawable(
                        ContextCompat.getColor(
                            requireContext(),
                            R.color.black
                        )
                    )
                )
                bgEffectHome.alpha = 0.5f
            }

            "evening_stop" -> {
                bgHome.loadImage(drawableRes = R.drawable.bg_evening_stop)
                btnWrite.loadImage(drawableRes = R.drawable.ic_h_write_dark)
                btnTemplate.loadImage(drawableRes = R.drawable.ic_h_template_evening_stop)
                btnFavorite.loadImage(drawableRes = R.drawable.ic_h_favorite_evening_stop)
                btnDashBoard.loadImage(drawableRes = R.drawable.ic_h_dash_evening_stop)
                btnSetting.loadImage(drawableRes = R.drawable.ic_h_setting_evening_stop)
                bgBottomHome.loadImage(drawableRes = R.drawable.bg_bottom_home_dark)
                isWhiteTheme = false
                bgEffectHome.loadImage(
                    drawable = ColorDrawable(
                        ContextCompat.getColor(
                            requireContext(),
                            R.color.black
                        )
                    )
                )
                bgEffectHome.alpha = 0.2f
            }
        }
        rcvHome.requestModelBuild()
    }
}