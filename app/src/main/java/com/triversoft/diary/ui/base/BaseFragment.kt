package com.triversoft.diary.ui.base

import android.content.Context
import android.net.ConnectivityManager
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.firebase.analytics.FirebaseAnalytics
import com.hjq.language.MultiLanguages

abstract class BaseFragment<V : ViewDataBinding>(val layout_id:Int) : Fragment() {

    private var logger: FirebaseAnalytics? = null

    lateinit var binding: V



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        MultiLanguages.updateAppLanguage(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, layout_id, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    fun isNetworkConnected(): Boolean {
        val cm = context?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager?
        return cm?.activeNetworkInfo != null && cm.activeNetworkInfo!!.isConnected
    }

    fun safeNav(currentDestination: Int, action: Int, bundle: Bundle?=null) {
        if (findNavController().currentDestination?.id == currentDestination) {
            try {
                if (bundle!=null){
                    findNavController().navigate(action, bundle)
                }else{
                    findNavController().navigate(action)
                }
            } catch (e: Exception) {
                Log.e("TAG", "safeNav Exception: ${e.message}")
            }
        }
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView(view)
        logEventScreen(screenName())
        logEvent(screenName()+"_show")
    }

    override fun onResume() {
        super.onResume()
        hideSystemNavigation()
    }





    private fun hideSystemNavigation(){
        activity?.let { act ->
            WindowCompat.getInsetsController(act.window, binding.root).let {
                it.hide(WindowInsetsCompat.Type.navigationBars())
                it.hide(WindowInsetsCompat.Type.statusBars())
                it.systemBarsBehavior =
                    WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
            }
        }
    }

    abstract fun initView(view:View)

    abstract fun screenName(): String

    fun getLog(): FirebaseAnalytics? {
        if (logger == null) {
            logger = context?.let { FirebaseAnalytics.getInstance(it) }
        }
        return logger
    }

    fun logEventScreen(screenName: String) {
        try {
            activity?.let { getLog()?.setCurrentScreen(it, screenName, screenName) }
        } catch (e: Exception) {
        }
    }

    fun logEvent(eventName: String) {
        logger?.logEvent(eventName, Bundle.EMPTY)
    }
}
