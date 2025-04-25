package com.triversoft.diary.util.help

import android.content.Context
import android.media.MediaPlayer
import android.os.VibrationEffect
import com.triversoft.diary.extension.cameraManager
import com.triversoft.diary.extension.vibrator
import com.triversoft.diary.R

class AudioVibrationManager(
    private val context: Context
) {
    private val vibrator = context.vibrator
    private val cameraManager = context.cameraManager
    private var camId: String? = null
    private var mPlayer: MediaPlayer = MediaPlayer.create(context, R.raw.beep)

    init {
        cameraManager?.cameraIdList?.get(0)?.firstOrNull()?.let {
            camId = it.toString()
        }
    }

    fun turnOnFlash(){
        runCatching {
            camId?.let {
                cameraManager?.setTorchMode(camId!!, true)
            }
        }
    }

    fun turnOffFlash(){
        runCatching {
            camId?.let {
                cameraManager?.setTorchMode(camId!!, false)
            }
        }
    }

    fun toggleVibrate(isEnable: Boolean){
        if (isEnable) startVibration() else stopVibration()
    }

    private fun startVibration(){
        if (vibrator?.hasVibrator() == true){
            val vibrationEffect = VibrationEffect.createOneShot(100, VibrationEffect.DEFAULT_AMPLITUDE)
            vibrator.vibrate(vibrationEffect)
        }
    }

    private fun stopVibration(){
        vibrator?.cancel()
    }

    fun toggleSound(isEnable: Boolean){
        if (isEnable) startSound() else stopSound()
    }

    private fun stopSound() {
        if (mPlayer.isPlaying){
            mPlayer.stop()
            mPlayer.release()
            mPlayer = MediaPlayer.create(context, R.raw.beep)
        }
    }

    private fun startSound(){
        if (!mPlayer.isPlaying){
            mPlayer.isLooping = true
            mPlayer.start()
        }
    }



}