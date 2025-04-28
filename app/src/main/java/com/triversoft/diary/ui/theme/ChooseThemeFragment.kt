package com.triversoft.diary.ui.theme

import android.content.res.ColorStateList
import android.graphics.Color
import android.view.View
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback
import com.airbnb.epoxy.EpoxyController
import com.triversoft.diary.R
import com.triversoft.diary.data.models.ThemeObj
import com.triversoft.diary.databinding.FragmentChooseThemeBinding
import com.triversoft.diary.itemOnboarding
import com.triversoft.diary.ui.base.BaseFragment
import com.triversoft.diary.databinding.FragmentOnboardingBinding
import com.triversoft.diary.extension.beVisible
import com.triversoft.diary.extension.setBackPressListener
import com.triversoft.diary.extension.setPreventDoubleClick
import com.triversoft.diary.itemTheme
import com.triversoft.diary.util.CommonData
import com.triversoft.diary.util.invisible
import com.triversoft.diary.util.visible
import kotlin.system.exitProcess

class ChooseThemeFragment :
    BaseFragment<FragmentChooseThemeBinding>(R.layout.fragment_choose_theme) {
    private val onPageChangeCallback = object : OnPageChangeCallback() {
        override fun onPageSelected(position: Int) {
            super.onPageSelected(position)
            val theme = themes[position]
            binding.apply {
              viewColorTheme.backgroundTintList = ColorStateList.valueOf(Color.parseColor(theme.colorTheme))
           if (theme.isPro && !theme.isBuy) {
               btnApply.invisible(true)
               tvDesThemePro.beVisible()
               btnWatchAds.beVisible()
               btnGoPro.beVisible()
           }else{
               btnApply.beVisible()
               tvDesThemePro.invisible(true)
               btnWatchAds.invisible(true)
               btnGoPro.invisible(true)
           }
           }
        }
    }
    private val themes = mutableListOf<ThemeObj>()
    override fun initView(view: View) {

        setBackPressListener {
            exitProcess(0)
        }
        binding.btnSkip.setPreventDoubleClick {
            safeNav(R.id.onboardingFragment, R.id.action_onboardingFragment_to_homeFragment)
        }

        context?.let { context ->
            themes.clear()
            themes.addAll(CommonData.themes(context))
        }
        binding.pagerTheme.adapter = controller.adapter
        binding.pagerTheme.registerOnPageChangeCallback(onPageChangeCallback)


        val pageOffsetPx = resources.getDimensionPixelOffset(com.intuit.sdp.R.dimen._24sdp)
        val recyclerView = binding.pagerTheme.getChildAt(0) as RecyclerView
        recyclerView.clipChildren = false
        recyclerView.clipToPadding = false
        recyclerView.setPadding(pageOffsetPx, 0, pageOffsetPx, 0) // padding giống trong XML
        recyclerView.itemAnimator = null // tránh animation gây lỗi

        controller.requestModelBuild()
    }

    private fun navToHome() {
        safeNav(R.id.onboardingFragment, R.id.action_onboardingFragment_to_homeFragment)

    }


    private val controller = object : EpoxyController() {
        override fun buildModels() {
            themes.forEachIndexed { index, pair ->
                itemTheme {
                    id(index)
                    resId(pair.avatar)
                    isPro(pair.isPro)
                    onClick { _ ->
                    }

                }
            }
        }
    }

    override fun screenName(): String = "fragment_onboarding"

}