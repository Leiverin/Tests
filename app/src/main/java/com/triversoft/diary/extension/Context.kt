package com.triversoft.diary.extension

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.hardware.Sensor
import android.hardware.SensorManager
import android.hardware.camera2.CameraManager
import android.media.MediaScannerConnection
import android.net.ConnectivityManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.Parcelable
import android.os.StrictMode
import android.os.Vibrator
import android.os.VibratorManager
import android.view.Display
import android.widget.Toast
import com.triversoft.diary.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

fun Context.toast(int: Int, length: Int = Toast.LENGTH_SHORT){
    toast(getString(int), length)
}

fun Context.toast(msg: String, length: Int = Toast.LENGTH_SHORT){
    doToast(this@toast, msg, length)
}

private fun doToast(context: Context, msg: String, length: Int){
    CoroutineScope(Dispatchers.Main).launch {
        if (context is Activity){
            if (!context.isDestroyed && !context.isFinishing){
                Toast.makeText(context, msg, length).show()
            }
        }else{
            Toast.makeText(context, msg, length).show()
        }
    }
}

fun Context.shareText(text: String){
    val sendIntent: Intent = Intent().apply {
        action = Intent.ACTION_SEND
        putExtra(Intent.EXTRA_TEXT, text)
        type = "text/plain"
    }
    startActivity(Intent.createChooser(sendIntent, null))
}

val storagePermissions: Array<String>
    get() = if (isTiramisu()) arrayOf(
        Manifest.permission.READ_MEDIA_VIDEO,
    ) else arrayOf(
        Manifest.permission.WRITE_EXTERNAL_STORAGE
    )

fun Context.haveInternet(): Boolean {
    var haveConnectedWifi = false
    var haveConnectedMobile = false
    return try {
        val cm = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val netInfo = cm.allNetworkInfo
        for (ni in netInfo) {
            if (ni.typeName
                    .equals("WIFI", ignoreCase = true)
            ) if (ni.isConnected) haveConnectedWifi = true
            if (ni.typeName
                    .equals("MOBILE", ignoreCase = true)
            ) if (ni.isConnected) haveConnectedMobile = true
        }
        haveConnectedWifi || haveConnectedMobile
    } catch (e: Exception) {
        System.err.println(e.toString())
        false
    }
}

fun Context.hasPermission(array: Array<String>): Boolean{
    return array.all { checkSelfPermission(it) == PackageManager.PERMISSION_GRANTED }
}

fun Context.openBrowser(url: String) {
    var url = url
    if (!url.startsWith("http://") && !url.startsWith("https://")) {
        url = "http://$url"
    }
    val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
    try {
        startActivity(browserIntent)
    } catch (ex: java.lang.Exception) {
        ex.printStackTrace()
    }
}

fun Context.sendEmailMore(
    addresses: Array<String>,
    subject: String,
    body: String
) {

    disableExposure()
    val emailSelectorIntent = Intent(Intent.ACTION_SENDTO)
    emailSelectorIntent.data = Uri.parse("mailto:")

    val intent = Intent(Intent.ACTION_SEND_MULTIPLE)
    // intent.type = "message/rfc822"
    // intent.data = Uri.parse("mailto:") // only email apps should handle this
    intent.putExtra(Intent.EXTRA_EMAIL, addresses)
    intent.putExtra(Intent.EXTRA_SUBJECT, subject)
//            if (uris.size > 0)
//                intent.putParcelableArrayListExtra(Intent.EXTRA_STREAM, uris)

    intent.putExtra(
        Intent.EXTRA_TEXT, body + "\n\n\n"
    )
    intent.selector = emailSelectorIntent
    try {
        startActivity(intent)
    } catch (e: Exception) {
        try {
            startActivity(Intent.createChooser(intent, "Send email..."));
        } catch (e: Exception) {
            Toast.makeText(this, "you need install gmail", Toast.LENGTH_SHORT).show()
        }

    }
}

inline fun <reified T : Parcelable> Bundle.getObjectParcelable(key: String): T? {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU){
        this.getParcelable(key, T::class.java)
    }else{
        this.getParcelable(key)
    }
}

private fun disableExposure() {
    if (Build.VERSION.SDK_INT >= 24) {
        try {
            val m = StrictMode::class.java.getMethod("disableDeathOnFileUriExposure")
            m.invoke(null)
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
        }
    }
}

fun Context.openPolicy(link: String) {
    var url = link
    if (!url.startsWith("http://") && !url.startsWith("https://")) {
        url = "http://$url"
    }
    val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
    try {
        startActivity(browserIntent)
    } catch (ex: java.lang.Exception) {
        ex.printStackTrace()
    }
}

fun Context.sendEmail(
    addresses: Array<String>,
    subject: String,
) {
    val emailSelectorIntent = Intent(Intent.ACTION_SENDTO)
    emailSelectorIntent.data = Uri.parse("mailto:")
    val intent = Intent(Intent.ACTION_SEND_MULTIPLE)
    intent.putExtra(Intent.EXTRA_EMAIL, addresses)
    intent.putExtra(Intent.EXTRA_SUBJECT, subject)

    intent.putExtra(
        Intent.EXTRA_TEXT, "input feedback hear"
    )
    intent.selector = emailSelectorIntent
    try {
        startActivity(intent)
    } catch (e: Exception) {
        try {
            startActivity(Intent.createChooser(intent, "Send email..."));
        } catch (e: Exception) {
            Toast.makeText(this, "you need install gmail", Toast.LENGTH_SHORT).show()
        }

    }
}

fun Context.shareApp(applicationId: String) {
    try {
        val shareIntent = Intent(Intent.ACTION_SEND)
        shareIntent.type = "text/plain"
        shareIntent.putExtra(Intent.EXTRA_SUBJECT, "${getString(R.string.app_name)}")
        var shareMessage = "Download app " + "${getString(R.string.app_name)}"
        shareMessage = "$shareMessage https://play.google.com/store/apps/details?id=$applicationId".trimIndent()
        shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage)
        startActivity(Intent.createChooser(shareIntent, "Choose one"))
    } catch (e: java.lang.Exception) {
        e.printStackTrace()
    }
}

fun Context.exportFile(path: String){
    MediaScannerConnection.scanFile(this, arrayOf(path), null, null)
}

val Context?.sensorManager: SensorManager?
    get() = this?.getSystemService(Context.SENSOR_SERVICE) as? SensorManager?

val Context?.cameraManager: CameraManager?
    get() = this?.getSystemService(Context.CAMERA_SERVICE) as? CameraManager?

val Context?.vibrator: Vibrator?
    get() = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
        (this?.getSystemService(Context.VIBRATOR_MANAGER_SERVICE) as? VibratorManager?)?.defaultVibrator
    } else {
        this?.getSystemService(Context.VIBRATOR_SERVICE) as? Vibrator?
    }

val Context?.magnetometer: Sensor?
    get() = sensorManager?.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD)

val Context?.rotationVectorSensor: Sensor?
    get() = sensorManager?.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR)

fun Activity?.getDisplayCompat(): Display?{
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
        this?.display
    } else {
        this?.windowManager?.defaultDisplay
    }
}

fun makeDelay(delay: Long = 100, action: () -> Unit){
    Handler(Looper.getMainLooper()).postDelayed({
        action.invoke()
    },delay)
}