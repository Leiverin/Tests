package com.admob.ads

import android.app.Activity
import android.app.Application
import android.os.Bundle
import android.os.SystemClock
import android.util.Log
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ProcessLifecycleOwner
import com.admob.ActivityActivityLifecycleCallbacks
import com.admob.AdType
import com.admob.Ads
import com.admob.AdsChild
import com.admob.TAdCallback
import com.admob.adLogger
import com.admob.ads.banner.AdmobBanner
import com.admob.ads.interstitial.AdmobInterResume
import com.admob.ads.nativead.AdmobNative
import com.admob.ads.open.AdmobOpenResume
import com.admob.delay
import com.admob.getStringAssetFile
import com.google.android.gms.ads.AdActivity
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.MobileAds
import com.google.firebase.ktx.Firebase
import com.google.firebase.remoteconfig.ktx.remoteConfig
import com.google.firebase.remoteconfig.ktx.remoteConfigSettings
import com.google.gson.Gson
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

object AdsSDK {

    internal lateinit var app: Application
    internal  var isDebugging : Boolean = true

    var isEnableBanner = true
        private set

    var isEnableNative = true
        private set

    var isEnableInter = true
        private set

    var isEnableOpenAds = true
        private set

    var isEnableRewarded = true
        private set

    var isPremium = false
        private set



    private var outsideAdCallback: TAdCallback? = null


    private var preventShowResumeAd = false


    internal var isEnableAppsflyer = false

    private var gson = Gson()


    /**
     * Mark loading time
     *  [String] => AdUnitId
     *  [Long] => Start time loading
     *  [Long] => End time loading
     */
    private var adUnitLoadingTime = mutableMapOf<String, Pair<Long, Long>?>()

    val adCallback: TAdCallback = object : TAdCallback {

        override fun onAdStartLoading(adUnit: String, adType: AdType) {
            super.onAdStartLoading(adUnit, adType)
            adLogger(adType, adUnit, "onAdStartLoading")

            // Đánh dấu thời gian bắt đầu load quảng cáo
            markAdStartLoading(adUnit)
        }

        override fun onAdClicked(adUnit: String, adType: AdType) {
            super.onAdClicked(adUnit, adType)
            outsideAdCallback?.onAdClicked(adUnit, adType)
            adLogger(adType, adUnit, "onAdClicked")
        }

        override fun onAdClosed(adUnit: String, adType: AdType) {
            super.onAdClosed(adUnit, adType)
            outsideAdCallback?.onAdClosed(adUnit, adType)
            adLogger(adType, adUnit, "onAdClosed")

            // Reset đánh dấu quảng cáo
            clearMarkLoadingTime(adUnit)
        }

        override fun onAdDismissedFullScreenContent(adUnit: String, adType: AdType) {
            super.onAdDismissedFullScreenContent(adUnit, adType)
            outsideAdCallback?.onAdDismissedFullScreenContent(adUnit, adType)
            adLogger(adType, adUnit, "onAdDismissedFullScreenContent")

            // Reset đánh dấu quảng cáo
            clearMarkLoadingTime(adUnit)
        }

        override fun onAdShowedFullScreenContent(adUnit: String, adType: AdType) {
            super.onAdShowedFullScreenContent(adUnit, adType)
            outsideAdCallback?.onAdShowedFullScreenContent(adUnit, adType)
            adLogger(adType, adUnit, "onAdShowedFullScreenContent")
        }

        override fun onAdFailedToShowFullScreenContent(
            error: String,
            adUnit: String,
            adType: AdType
        ) {
            super.onAdFailedToShowFullScreenContent(error, adUnit, adType)
            outsideAdCallback?.onAdFailedToShowFullScreenContent(error, adUnit, adType)
            adLogger(adType, adUnit, "onAdFailedToShowFullScreenContent")

            markAdEndLoading(adUnit)
            clearMarkLoadingTime(adUnit)
        }

        override fun onAdFailedToLoad(adUnit: String, adType: AdType, error: LoadAdError) {
            super.onAdFailedToLoad(adUnit, adType, error)
            outsideAdCallback?.onAdFailedToLoad(adUnit, adType, error)
            adLogger(adType, adUnit, "onAdFailedToLoad(${error.code} - ${error.message})")


            // Đánh dấu thời gian bắt đầu load xong quảng cáo (load bị lỗi)
            markAdEndLoading(adUnit)
            clearMarkLoadingTime(adUnit)
        }

        override fun onAdImpression(adUnit: String, adType: AdType) {
            super.onAdImpression(adUnit, adType)
            outsideAdCallback?.onAdImpression(adUnit, adType)
            adLogger(adType, adUnit, "onAdImpression")
        }

        override fun onAdLoaded(adUnit: String, adType: AdType) {
            super.onAdLoaded(adUnit, adType)
            outsideAdCallback?.onAdLoaded(adUnit, adType)
            adLogger(adType, adUnit, "onAdLoaded")


            // Đánh dấu thời gian bắt đầu load xong quảng cáo (load bị lỗi)
            markAdEndLoading(adUnit)
        }

        override fun onAdOpened(adUnit: String, adType: AdType) {
            super.onAdOpened(adUnit, adType)
            outsideAdCallback?.onAdOpened(adUnit, adType)
            adLogger(adType, adUnit, "onAdOpened")
        }

        override fun onAdSwipeGestureClicked(adUnit: String, adType: AdType) {
            super.onAdSwipeGestureClicked(adUnit, adType)
            outsideAdCallback?.onAdSwipeGestureClicked(adUnit, adType)
            adLogger(adType, adUnit, "onAdSwipeGestureClicked")
        }

        override fun onPaidValueListener(bundle: Bundle?) {
            super.onPaidValueListener(bundle)
            outsideAdCallback?.onPaidValueListener(bundle)
        }

        override fun onSetInterFloorId() {
            super.onSetInterFloorId()
            outsideAdCallback?.onSetInterFloorId()
        }
    }

    val activities = mutableSetOf<Activity>()

    val clazzIgnoreAdResume = mutableListOf<Class<*>>()

    private val applicationStateObserver = object : DefaultLifecycleObserver {


        override fun onStart(owner: LifecycleOwner) {
            super.onStart(owner)
            if (preventShowResumeAd) {
                preventShowResumeAd = false
                return
            }
            AdmobInterResume.onInterAppResume()
            AdmobOpenResume.onOpenAdAppResume()
        }

        override fun onResume(owner: LifecycleOwner) {
            super.onResume(owner)

        }

        override fun onStop(owner: LifecycleOwner) {
            super.onStop(owner)
        }
    }

    private val activityLifecycleCallbacks = object : ActivityActivityLifecycleCallbacks() {
        override fun onActivityCreated(activity: Activity, bundle: Bundle?) {
            super.onActivityCreated(activity, bundle)
            activities.add(activity)
        }

        override fun onActivityResumed(activity: Activity) {
            super.onActivityResumed(activity)
            activities.add(activity)
        }

        override fun onActivityDestroyed(activity: Activity) {
            super.onActivityDestroyed(activity)
            activities.remove(activity)
        }
    }

    fun init(application: Application, path: String, keyConfigAds: String, isDebug : Boolean =true): AdsSDK {
        app = application
        this.isDebugging = isDebug
        ProcessLifecycleOwner.get().lifecycle.addObserver(applicationStateObserver)
        application.registerActivityLifecycleCallbacks(activityLifecycleCallbacks)

        delay(1000) {
            CoroutineScope(Dispatchers.IO).launch {
                MobileAds.initialize(application)
            }
        }

        getData(path,keyConfigAds)
        return this
    }


    private val listAds = mutableListOf<AdsChild>()
    private fun getData(path: String, keyConfigAds: String) {
        try {
            val data = getStringAssetFile(path, app)
            val ads = gson.fromJson<Ads>(data, Ads::class.java)
            listAds.clear()
            listAds.addAll(ads.listAdsChild)
            listAds.forEach {
                Log.e("DucLH-----", it.toString())
            }
        } catch (e: Exception) {
            Log.e("DucLH-", "no load data json ads file")
        }
        getRemoteConfig(keyConfigAds)
    }

    private fun getRemoteConfig(keyConfigAds: String) {
        Firebase.remoteConfig.apply {
            setConfigSettingsAsync(remoteConfigSettings { minimumFetchIntervalInSeconds = 0 })
            fetchAndActivate().addOnCompleteListener {
                try {
                    val data = getString(keyConfigAds)

                    if (data.isNullOrBlank()) return@addOnCompleteListener

                    val ads = gson.fromJson<Ads>(data, Ads::class.java)
                    listAds.clear()
                    listAds.addAll(ads.listAdsChild)
                    listAds.forEach {
                        Log.e("DucLH-----", it.toString())
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }
    }

    fun getAdChild(space: String): AdsChild? {

        return listAds.find {
            it.spaceName == space
        }

    }



    fun setAdCallback(callback: TAdCallback?=null): AdsSDK {
        outsideAdCallback = callback
        return this
    }

    fun setIgnoreAdResume(vararg clazz: Class<*>): AdsSDK {
        clazzIgnoreAdResume.clear()
        clazzIgnoreAdResume.add(AdActivity::class.java)
        clazzIgnoreAdResume.addAll(clazz)
        return this
    }

    fun preventShowResumeAdNextTime() {
        preventShowResumeAd = true
    }


    fun setEnableBanner(isEnable: Boolean) {
        isEnableBanner = isEnable
        AdmobBanner.setEnableBanner(isEnable)
    }

    fun setEnableNative(isEnable: Boolean) {
        isEnableNative = isEnable
        AdmobNative.setEnableNative(isEnable)
    }

    fun setEnableInter(isEnable: Boolean) {
        isEnableInter = isEnable
    }

    fun setEnableOpenAds(isEnable: Boolean) {
        isEnableOpenAds = isEnable
    }

    fun setEnableRewarded(isEnable: Boolean) {
        isEnableRewarded = isEnable
    }

    fun setPremium() {
        isPremium = true
        setEnableNative(false)
        setEnableInter(false)
        setEnableOpenAds(false)
        setEnableRewarded(false)
    }

    internal fun defaultAdRequest(): AdRequest {
        return AdRequest.Builder().build()
    }

    private fun markAdStartLoading(adUnitId: String) {
        adUnitLoadingTime[adUnitId] = Pair(SystemClock.elapsedRealtime(), 0)
    }

    private fun markAdEndLoading(adUnitId: String) {
        val pairStartEndTime = adUnitLoadingTime[adUnitId]
        pairStartEndTime ?: return
        val startAdLoadingTime = pairStartEndTime.first
        adUnitLoadingTime[adUnitId] = Pair(startAdLoadingTime, SystemClock.elapsedRealtime())
    }

    internal fun getAdLoadingTime(adUnitId: String): Long {
        val pairStartEndTime = adUnitLoadingTime[adUnitId]
        pairStartEndTime ?: return -1

        val startAdLoadingTime = pairStartEndTime.first
        val endAdLoadingTime = pairStartEndTime.second

        if (startAdLoadingTime <= 0 || endAdLoadingTime <= 0) {
            return -1
        }

        if (endAdLoadingTime - startAdLoadingTime <= 0) {
            return -1
        }

        return endAdLoadingTime - startAdLoadingTime
    }

    internal fun clearMarkLoadingTime(adUnitId: String) {
        adUnitLoadingTime.remove(adUnitId)
    }

}



