package com.triversoft.diary.ui.popup

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.PopupWindow
import com.triversoft.diary.R
import com.triversoft.diary.databinding.LayoutStatusBinding
import com.triversoft.diary.extension.screenWidth
import com.triversoft.diary.itemMood
import com.triversoft.diary.itemWeather
import com.triversoft.diary.util.CommonData

class StatusPopup @JvmOverloads constructor(
    private val context: Context,
    attrs: AttributeSet? = null
) : PopupWindow(context, attrs) {

    private val binding = LayoutStatusBinding.inflate(LayoutInflater.from(context))
    private var type = StatusType.MOOD

    var onSelectMood: (Pair<Int, Int>) -> Unit = {}
    var onSelectWeather: (Triple<Int, String, Int>) -> Unit = {}

    fun setType(type: StatusType){
        runCatching {
            this.type = type
            when(type){
                StatusType.MOOD -> initRvMood()
                StatusType.WEATHER -> initRvWeather()
            }
        }
    }

    init {
        setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        contentView = binding.root
        width = screenWidth
        animationStyle = R.style.PopupAnimation
        isFocusable = true
        when(type){
            StatusType.MOOD -> initRvMood()
            StatusType.WEATHER -> initRvWeather()
        }
    }

    private fun initRvWeather() {
        val list = CommonData.weatherData(context)
        binding.rvStatus.withModels {
            list.forEachIndexed { index, weather ->
                itemWeather {
                    id(index)
                    name(weather.second)
                    resId(weather.third)
                    onClick { _ ->
                        selectWeather(weather)
                    }
                }
            }
        }
    }

    private fun selectWeather(weather: Triple<Int, String, Int>) {
        onSelectWeather.invoke(weather)
    }

    private fun initRvMood() {
        binding.rvStatus.withModels {
            CommonData.moods().forEachIndexed { index, mood ->
                itemMood {
                    id(index)
                    resId(mood.second)
                    onClick { _ ->
                        selectMood(mood)
                    }
                }
            }
        }
    }

    private fun selectMood(mood: Pair<Int, Int>) {
        onSelectMood.invoke(mood)
    }

    enum class StatusType{
        MOOD, WEATHER
    }

}