package com.triversoft.diary.ui.onboarding

import android.view.View
import androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback
import com.airbnb.epoxy.EpoxyController
import com.triversoft.diary.R
import com.triversoft.diary.itemOnboarding
import com.triversoft.diary.ui.base.BaseFragment
import com.triversoft.diary.databinding.FragmentOnboardingBinding
import com.triversoft.diary.extension.setBackPressListener
import com.triversoft.diary.util.CommonData
import kotlin.system.exitProcess

class OnboardingFragment : BaseFragment<FragmentOnboardingBinding>(R.layout.fragment_onboarding) {

    private val onboardings = mutableListOf<Pair<Int, Int>>()
    override fun initView(view: View) {

        setBackPressListener {
            exitProcess(0)
        }

        context?.let { context ->
            onboardings.clear()
            onboardings.addAll(CommonData.onboardings(context))
        }
         binding.pagerOnboarding.adapter = controller.adapter
        binding.pagerOnboarding.registerOnPageChangeCallback(onPageChangeCallback)
        controller.requestModelBuild()
    }

    private fun navToHome() {
    safeNav(R.id.onboardingFragment,R.id.action_onboardingFragment_to_homeFragment)

    }


    private val onPageChangeCallback = object : OnPageChangeCallback() {
        override fun onPageSelected(position: Int) {
            super.onPageSelected(position)

        }
    }

    private val controller = object: EpoxyController(){
        override fun buildModels() {
            onboardings.forEachIndexed { index, pair ->
                itemOnboarding {
                    id(index)
                    resId(pair.first)
                    title(pair.second)
                    onClick { _ ->
                        if (binding.pagerOnboarding.currentItem == 2){
                            navToHome()
                        }else{
                            binding.pagerOnboarding.currentItem += 1
                        }
                    }

                }
            }
        }
    }

    override fun screenName(): String = "fragment_onboarding"

}