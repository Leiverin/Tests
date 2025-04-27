package com.triversoft.diary.ui.create_diary

import android.view.View
import androidx.fragment.app.viewModels
import com.triversoft.diary.R
import com.triversoft.diary.databinding.FragmentCreateDairyBinding
import com.triversoft.diary.extension.setBackPressListener
import com.triversoft.diary.extension.setPreventDoubleClick
import com.triversoft.diary.ui.base.BaseFragment
import com.triversoft.diary.ui.views.StatusPopup

class CreateDiaryFragment: BaseFragment<FragmentCreateDairyBinding>(R.layout.fragment_create_dairy) {

    private val viewModel: CreateDiaryViewModel by viewModels()

    private var statusPopup: StatusPopup? = null

    override fun initView(view: View) {
        initData()
        initEvents()
    }

    private fun initData() {

    }

    private fun initEvents() {
        setBackPressListener(binding.btnBack) {
            popUpBackStack()
        }
        binding.viewMood.setPreventDoubleClick {
            showPopupStatus(StatusPopup.StatusType.MOOD, it)
        }
        binding.viewWeather.setPreventDoubleClick {
            showPopupStatus(StatusPopup.StatusType.WEATHER, it)
        }
    }

    private fun showPopupStatus(type: StatusPopup.StatusType, view: View){
        statusPopup?.dismiss()
        context?.let { context ->
            statusPopup = StatusPopup(context).apply {
                setType(type)
                onSelectMood = { mood ->

                }
                onSelectWeather = { weather ->

                }
            }
            if (statusPopup?.isShowing != true){
                statusPopup?.showAsDropDown(view)
            }
        }
    }

    override fun screenName(): String = ""

}