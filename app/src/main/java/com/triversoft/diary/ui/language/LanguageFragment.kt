package com.triversoft.diary.ui.language

import android.view.View
import com.hjq.language.MultiLanguages
import com.triversoft.diary.R
import com.triversoft.diary.data.mmkv.MMKVUtils
import com.triversoft.diary.data.models.Language
import com.triversoft.diary.ui.base.BaseFragment
import com.triversoft.diary.databinding.FragmentLanguageBinding
import com.triversoft.diary.extension.setBackPressListener
import com.triversoft.diary.extension.setPreventDoubleClick
import com.triversoft.diary.extension.toast
import com.triversoft.diary.itemLanguage
import com.triversoft.diary.util.AdsUtils
import com.triversoft.diary.util.CommonData
import com.triversoft.diary.util.visible
import java.util.Locale
import kotlin.system.exitProcess

class LanguageFragment : BaseFragment<FragmentLanguageBinding>(R.layout.fragment_language) {

    private var langCode = ""
    private val languages = mutableListOf<Language>()


    override fun initView(view: View) {
        initData()
        initRv()
    }

    private fun initData() {
        binding.btnNext.setPreventDoubleClick {
            if (langCode.isEmpty()){
                context?.toast(getString(R.string.please_select_your_language))
            }else{
                handleSetLanguage()
            }
        }
        setBackPressListener(binding.btnBack) {
            if (MMKVUtils.isFirstOpen) exitProcess(0) else popBackStack()
        }
        langCode = MMKVUtils.langCode
        languages.clear()
        languages.addAll(CommonData.languages())

        context?.let {
        AdsUtils.loadNative(it,"nativeLanguage", binding.layoutAds, R.layout.layout_native_large)
        }
        binding.btnBack.visible(!MMKVUtils.isFirstOpen)
        binding.btnNext.alpha = if (langCode.isEmpty()) 0.3f else 1f
    }

    private fun initRv() {
        binding.rvLanguages.withModels {
            languages.forEachIndexed { index, language ->
                itemLanguage {
                    id(index)
                    resId(language.flag)
                    name(language.name)
                    isSelected(langCode == language.code)
                    onClick { _ ->
                        handleClickLanguage(language)
                    }
                }
            }
        }
    }

    private fun handleClickLanguage(language: Language) {
        langCode = language.code
        binding.btnNext.alpha = 1f
        binding.rvLanguages.requestModelBuild()
    }


    private fun handleSetLanguage() {
        MMKVUtils.langCode = langCode
        runCatching {
            MultiLanguages.setAppLanguage(context, Locale(langCode))
        }
        if (MMKVUtils.isFirstOpen){
           navToOnboarding()
        }else{
             popBackStack()
        }
    }

    private fun popBackStack() {

    }

    private fun navToOnboarding() {
        safeNav(R.id.languageFragment,R.id.action_languageFragment_to_onboardingFragment)
    }

    override fun screenName(): String = "fragment_language"

}