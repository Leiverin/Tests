package com.triversoft.diary.util

import android.content.Context
import android.view.ViewGroup
import com.admob.ads.interstitial.AdmobInter
import com.admob.ads.nativead.AdmobNative
import com.triversoft.diary.extension.beGone
import com.triversoft.diary.extension.beVisible
import com.triversoft.diary.extension.haveInternet

object AdsUtils {
    fun loadNative(context: Context?, space: String, layoutAds: ViewGroup, layoutRes: Int){
        if (context?.haveInternet() == true){
            layoutAds.beVisible()
            AdmobNative.show(layoutAds, space, layoutRes, false)
        }else{
            layoutAds.beGone()
        }
    }


    fun showInter(context: Context?, space: String = "interChung", action: () -> Unit){
        if (context?.haveInternet() == true){
            AdmobInter.show(
                space,
                showLoadingInter = true,
                nextActionBeforeDismiss = true,
                nextActionBeforeDismissDelayTime = 200,
                nextAction = {
                    action.invoke()
                })
        }else{
            action.invoke()
        }
    }

}