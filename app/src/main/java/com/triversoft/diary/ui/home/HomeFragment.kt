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