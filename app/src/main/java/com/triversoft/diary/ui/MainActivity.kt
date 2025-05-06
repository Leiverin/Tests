package com.triversoft.diary.ui

import android.content.Context
import android.graphics.Rect
import android.view.MotionEvent
import android.widget.EditText
import androidx.navigation.fragment.NavHostFragment
import com.hjq.language.MultiLanguages
import com.triversoft.diary.R
import com.triversoft.diary.data.caching.MMKVCache
import com.triversoft.diary.databinding.ActivityMainBinding
import com.triversoft.diary.extension.hideKeyboard
import com.triversoft.diary.ui.base.BaseActivity
import com.triversoft.diary.util.CommonData

class MainActivity: BaseActivity<ActivityMainBinding>() {

    override fun layoutRes(): Int = R.layout.activity_main

    override fun initView() {
        initData()
        setUpNav()
    }

    private fun initData() {
        if (MMKVCache.fonts.isEmpty()){
            MMKVCache.fonts = CommonData.fonts(context = this)
        }
    }

    private fun setUpNav() {
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.fcvMain) as NavHostFragment
        val inflater = navHostFragment.navController.navInflater
        val graph = inflater.inflate(R.navigation.main_nav)
        val navController = navHostFragment.navController
        graph.setStartDestination(R.id.createDiaryFragment)
        navController.setGraph(graph, null)
    }

    override fun observeData() {

    }

    override fun attachBaseContext(newBase: Context?) {
        kotlin.runCatching {
            super.attachBaseContext(MultiLanguages.attach(newBase))
        }
    }

    override fun dispatchTouchEvent(ev: MotionEvent): Boolean {
        if (ev.action == MotionEvent.ACTION_DOWN) {
            val v = currentFocus
            if (v is EditText) {
                val outRect = Rect()
                v.getGlobalVisibleRect(outRect)
                if (!outRect.contains(ev.rawX.toInt(), ev.rawY.toInt())) {
                    v.clearFocus()
                    v.hideKeyboard()
                }
            }
        }
        return super.dispatchTouchEvent(ev)
    }


}