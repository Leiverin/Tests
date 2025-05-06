package com.triversoft.diary

import android.app.Application
import android.content.Context
import com.admob.ads.AdsSDK
import com.google.android.gms.ads.MobileAds
import com.google.android.gms.ads.RequestConfiguration
import com.google.firebase.Firebase
import com.google.firebase.initialize
import com.hjq.language.MultiLanguages
import com.triversoft.diary.ui.splash.SplashFragment
import com.tencent.mmkv.MMKV
import com.triversoft.diary.util.CommonData

class AppApplication: Application() {

    override fun onCreate() {
        super.onCreate()

        MMKV.initialize(this)
        MultiLanguages.init(this)
        Firebase.initialize(this)
        initData()
        configAds()
    }

    private fun initData() {
    }

    private fun configAds() {
        AdsSDK.init(this,"admod_id.json", "ads_config", BuildConfig.DEBUG)
            .setAdCallback()
            .setIgnoreAdResume(SplashFragment::class.java)
        val testDeviceIds = listOf("EECA22EB8E49F117EBC06CDC12AB86E9")
        val configuration = RequestConfiguration.Builder().setTestDeviceIds(testDeviceIds).build()
        MobileAds.setRequestConfiguration(configuration)
    }
    override fun attachBaseContext(base: Context) {
        kotlin.runCatching {
            super.attachBaseContext(MultiLanguages.attach(base))
        }
    }

}