package com.triversoft.diary.ui.splash

import android.os.CountDownTimer
import android.view.View
import androidx.core.view.postDelayed
import com.daimajia.androidanimations.library.Techniques
import com.daimajia.androidanimations.library.YoYo
import com.triversoft.diary.R
import com.triversoft.diary.data.mmkv.MMKVUtils
import com.triversoft.diary.ui.base.BaseFragment
import com.triversoft.diary.databinding.FragmentSplashBinding
import com.triversoft.diary.extension.beVisible
import com.triversoft.diary.util.loadImage
import kotlin.math.abs

class SplashFragment : BaseFragment<FragmentSplashBinding>( R.layout.fragment_splash) {

    companion object{
        const val TIME_PROGRESS_SPLASH = 3000
    }

    private var isNetworkAvailable: Boolean = false


    private var currentIndex = 0
    override fun initView(view: View) {
        initData()
        isNetworkAvailable = isNetworkConnected()
        val progressDuration = if (isNetworkAvailable) 10000L else TIME_PROGRESS_SPLASH.toLong()
        binding.progressBar.max = progressDuration.toInt()

        binding.apply{
        view.post {

//            ivBlack.postDelayed({
//                startFadeOutCountdown(ivBlack,1200)
//            },700)
            YoYo.with(Techniques.SlideInUp)
                .duration(2000)
                .repeat(0)
                .playOn(imgLogo);

            YoYo.with(Techniques.SlideInUp)
                .duration(2000)
                .repeat(0)
                .onEnd {
                            imgLogo3.beVisible()
                            YoYo.with(Techniques.FadeIn)
                                .duration(1000)
                                .repeat(0)
                                .onEnd {
                                    rlJellyChat.beVisible()
                                    tvChat  .animateText(getString(R.string.sp_chat));

                                    rlJellyChat.postDelayed({
                                        YoYo.with(Techniques.Pulse)
                                            .duration(1500)
                                            .repeat(-1)
                                            .playOn(rlJellyChat);
                                    },1500)
                                    YoYo.with(Techniques.Pulse)
                                        .duration(1500)
                                        .repeat(1)
                                        .playOn(imgLogo2);
                                    YoYo.with(Techniques.Pulse)
                                        .duration(1500)
                                        .repeat(1)
                                        .playOn(imgLogo3);
                                }
                                .playOn(imgLogo3);
                        }
                .playOn(imgLogo2);

        }
        }

//        if (isNetworkAvailable){
//            GDPRUtils.showCMP(activity as AppCompatActivity, BuildConfig.DEBUG) {
//                makeCountdown(progressDuration)
//                moveWithAds()
//            }
//        }else{
            makeCountdown(progressDuration)
//        }

    }
    fun startFadeOutCountdown(view: View, duration: Long = 3000L) {
        val interval = 10L // thời gian cập nhật mỗi bước (ms)
        val steps = duration / interval

        object : CountDownTimer(duration, interval) {
            override fun onTick(millisUntilFinished: Long) {
                val progress = millisUntilFinished.toFloat() / duration
                view.alpha = progress // cập nhật độ mờ
            }

            override fun onFinish() {
                view.alpha = 0f // đảm bảo mờ hẳn khi kết thúc
                view.visibility = View.GONE // ẩn view nếu cần
            }
        }.start()
    }
    private fun moveWithAds() {
//        AdmobInter.load("interChung")
        if (MMKVUtils.isFirstOpen){
//            AdmobNative.loadOnly("nativeLanguage")
//            AdmobNative.loadOnly("nativeIntro")
//            AdmobNative.loadOnly("nativePermission")
        }
//        if (isNetworkAvailable) {
//            AdmobOpenSplash.show("openSplash", 10_000L) {
//                moveFragment()
//            }
//        } else {
            moveFragment()
//        }
    }

    private fun moveFragment() {
//        if (isNetworkAvailable){
//            AdmobOpenResume.load("openResume", object : TAdCallback {
//                override fun onAdLoaded(adUnit: String, adType: AdType) {
//                    super.onAdLoaded(adUnit, adType)
//                }
//            })
//        }
//        view?.postDelayed({
        safeNav(R.id.splashFragment,R.id.action_splashFragment_to_languageFragment)
//                          },3000)
    }


    private fun makeCountdown(progressDuration: Long) {
        object : CountDownTimer(progressDuration, 1){
            override fun onTick(millisUntilFinished: Long) {
                binding.progressBar.progress = (progressDuration - abs(1 - millisUntilFinished)).toInt()
            }

            override fun onFinish() {
                if (!isNetworkAvailable){
                    moveFragment()
                }
            }
        }.start()
    }

    private fun initData() {
        binding.imgBg.loadImage(drawableRes = R.drawable.bg_sp_bottom)
        binding.imgLogo.loadImage(drawableRes = R.drawable.ic_sp_logo)
        binding.imgLogo2.loadImage(drawableRes = R.drawable.ic_sp_jellyfish)
        binding.imgLogo3.loadImage(drawableRes = R.drawable.ic_sp_security)
        binding.ivJellyChat.loadImage(drawableRes = R.drawable.bg_chat_sp)
    }



    override fun screenName(): String = "fragment_splash"

}