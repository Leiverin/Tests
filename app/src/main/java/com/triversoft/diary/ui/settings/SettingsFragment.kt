package com.triversoft.diary.ui.settings

import android.view.View
import com.triversoft.diary.BuildConfig
import com.triversoft.diary.R
import com.triversoft.diary.ui.base.BaseFragment
import com.triversoft.diary.databinding.FragmentSettingsBinding
import com.triversoft.diary.extension.openPolicy
import com.triversoft.diary.extension.sendEmail
import com.triversoft.diary.extension.setBackPressListener
import com.triversoft.diary.extension.shareApp
import com.triversoft.diary.util.CommonData
import com.triversoft.diary.itemSettings

class SettingsFragment : BaseFragment<FragmentSettingsBinding>( R.layout.fragment_settings) {
    private val listSettings = arrayListOf<Pair<Int, String>>()


    override fun initView(view: View) {
        setBackPressListener(binding.btnBack) {
    }
        initData()
        initRv()
    }

    private fun initData() {
        listSettings.clear()
        listSettings.addAll(CommonData.settings(context))
    }

    private fun initRv() {
        binding.rvSettings.withModels {
            listSettings.forEachIndexed { index, pair ->
                itemSettings{
                    id(index)
                    icon(pair.first)
                    name(pair.second)
                    onClick{ _ ->
                        handleClickSettings(index)
                    }
                }
            }
        }
    }

    private fun handleClickSettings(index: Int) {
        when(index){
            0 -> clickPolicy()
            1 -> clickLanguage()
            2 -> clickShare()
            3 -> clickFeedback()
        }
    }

    private fun clickFeedback() {
        context?.sendEmail(
            arrayOf("triversoft99@gmail.com"),
            "Feedback to ${getString(R.string.app_name)}- ${BuildConfig.VERSION_NAME}",
        )
    }

    private fun clickShare() {
        context?.shareApp(BuildConfig.APPLICATION_ID)
    }

    private fun clickLanguage() {
    }

    private fun clickPolicy() {
        context?.openPolicy("https://sites.google.com/view/policy-gold-finder?usp=sharing")
    }


    override fun screenName(): String = "fragment_settings"

}