package com.triversoft.diary.ui.home

import android.view.View
import com.daimajia.androidanimations.library.Techniques
import com.daimajia.androidanimations.library.YoYo
import com.triversoft.diary.R
import com.triversoft.diary.databinding.FragmentHomeBinding
import com.triversoft.diary.extension.setBackPressListener
import com.triversoft.diary.extension.setPreventDoubleClick
import com.triversoft.diary.ui.base.BaseFragment
import kotlin.system.exitProcess

class HomeFragment: BaseFragment<FragmentHomeBinding>(R.layout.fragment_home) {


    private fun initData() {
        binding.apply {
            view?.post {
            YoYo.with(Techniques.Tada)
                .duration(500)
                .repeat(3)
                .playOn(ivTutorial);

            YoYo.with(Techniques.SlideInLeft)
                .duration(500)
                .repeat(0)
                .playOn(lnCurrentSize);

            YoYo.with(Techniques.SlideInRight)
                .duration(500)
                .repeat(0)
                .playOn(lnCustom);

            YoYo.with(Techniques.SlideInUp)
                .duration(500)
                .repeat(0)
                .playOn(rlSize13);

            YoYo.with(Techniques.SlideInUp)
                .duration(700)
                .repeat(0)
                .playOn(rlSize16);

            YoYo.with(Techniques.SlideInUp)
                .duration(1000)
                .repeat(0)
                .playOn(rlSize20);

            }


            lnCurrentSize.setPreventDoubleClick {

            }
            rlCurrentSize.setPreventDoubleClick {

            }
            btnTutorial.setPreventDoubleClick {
            }
        }
    }


    override fun initView(view: View) {
        setBackPressListener {
        exitProcess(0)
    }
        initData()


    }

    override fun screenName(): String = "fragment_home"

}