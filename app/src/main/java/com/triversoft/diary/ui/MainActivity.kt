package com.triversoft.diary.ui

import android.content.Context
import androidx.navigation.fragment.NavHostFragment
import com.hjq.language.MultiLanguages
import com.triversoft.diary.R
import com.triversoft.diary.databinding.ActivityMainBinding
import com.triversoft.diary.ui.base.BaseActivity

class MainActivity: BaseActivity<ActivityMainBinding>() {

    override fun layoutRes(): Int = R.layout.activity_main

    override fun initView() {
        setUpNav()
    }

    private fun setUpNav() {
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.fcvMain) as NavHostFragment
        val inflater = navHostFragment.navController.navInflater
        val graph = inflater.inflate(R.navigation.main_nav)
        val navController = navHostFragment.navController
            graph.setStartDestination(R.id.chooseThemeFragment)
        navController.setGraph(graph, null)
    }

    override fun observeData() {

    }

    override fun attachBaseContext(newBase: Context?) {
        kotlin.runCatching {
            super.attachBaseContext(MultiLanguages.attach(newBase))
        }
    }

}