package com.triversoft.diary.util

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.LinearGradient
import android.graphics.Paint
import android.graphics.RectF
import android.graphics.Shader
import android.util.AttributeSet
import android.util.Log
import android.util.TypedValue
import android.view.MotionEvent
import android.view.View
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.content.ContextCompat
import com.triversoft.diary.R

class CustomSizeSeekbar @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private var lineBg = Paint(Paint.ANTI_ALIAS_FLAG)
    private var line25 = Paint(Paint.ANTI_ALIAS_FLAG)
    private var progressPaint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val rectBg = RectF(0f, 0f, 0f, 0f)
    private val rect25 = RectF(0f, 0f, 0f, 0f)
    private val rectProgress = RectF(0f, 0f, 0f, 0f)
    private var positionThumb = -1f
    private var check = false
    private var MAX = 100
    private var positonText = 0f
    var sbProgress = 0
    private var sizeThumb = 0f
    private var sizeProgress = 0f

    val bitmap = AppCompatResources.getDrawable(context, R.drawable.thumb)

    init {
        sizeThumb = context.convertDpToPx(18).toFloat()
        sizeProgress = context.convertDpToPx(8).toFloat()

        lineBg.color = ContextCompat.getColor(context, R.color.bg_pg)
        lineBg.strokeWidth = 0f
        lineBg.style = Paint.Style.FILL_AND_STROKE
        lineBg.strokeJoin = Paint.Join.ROUND

        line25.color = ContextCompat.getColor(context, R.color.color_FFD901)
        line25.strokeWidth =0f
        line25.style = Paint.Style.FILL_AND_STROKE
        line25.strokeJoin = Paint.Join.ROUND

//        progressPaint.color = ContextCompat.getColor(context, R.color.color_FF9900)
        progressPaint.strokeWidth =0f

        progressPaint.style = Paint.Style.FILL_AND_STROKE
        progressPaint.strokeJoin = Paint.Join.ROUND



    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        progressPaint.shader = LinearGradient(
            0f, 0f,w.toFloat(),  0f,  // từ góc trên trái đến góc dưới phải
            Color.parseColor("#92ADFF"),
            Color.parseColor("#4169E1"),
            Shader.TileMode.CLAMP
        )
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        setMeasuredDimension(widthMeasureSpec, context.convertDpToPx(32))

    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)
//        setProgress(sbProgress)
    }

    fun dpFromPx(context: Context, px: Float): Float {
        return px / context.resources.displayMetrics.density
    }

    fun setProgress(progress: Int) {
        this.sbProgress = progress
        positionThumb = (progress * (width - sizeProgress * 2f)) / 100
        Log.e("TAG", "setProgress: " + progress)
        Log.e("TAG", "setProgress: " + positionThumb)
        Log.e("TAG", "setProgress: " + (width - sizeProgress * 2))
        Log.e("TAG", "setProgress: " + sizeProgress)
        if (positionThumb < sizeThumb / 2) {
            positionThumb = sizeThumb / 2
        } else if (positionThumb >= width - sizeThumb) {
            positionThumb = width - sizeThumb / 2
        }

//        positonText = when {
//            progress < 10 -> {
//                positionThumb - 10f
//            }
//
//            progress >= 100 -> {
//                positionThumb - sizeProgress
//            }
//
//            else -> {
//                positionThumb - sizeProgress
//            }
//        }
        invalidate()
    }

    private var listener: Listener? = null


    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

//        if (!check) {
////            positionThumb = 25f
////            positonText = positionThumb - 25f
//            setProgress(progress)
//            check = true
//        }


            rectBg.top = height.toFloat() / 2 - sizeProgress / 2
            rectBg.bottom = height.toFloat() / 2 + sizeProgress / 2
            rectBg.left = sizeThumb/2
            rectBg.right = width.toFloat() - sizeThumb/2

            rect25.top = height.toFloat() / 2 -  sizeThumb/2
            rect25.bottom = height.toFloat() / 2 +  sizeThumb/2
            rect25.left =  (width - sizeProgress * 2f) * 0.2F   - sizeThumb/6
            rect25.right = (width - sizeProgress * 2f) * 0.2F   + sizeThumb/6


            rectProgress.top = height.toFloat() / 2    - sizeProgress / 2
            rectProgress.bottom = height.toFloat() / 2 + sizeProgress / 2
            rectProgress.left = sizeThumb/2
            rectProgress.right = positionThumb

//        canvas.drawRoundRect(rect, height.toFloat(),height.toFloat(), linePaint)
            canvas.drawRoundRect(rectBg, height.toFloat(), height.toFloat(), lineBg)
            canvas.drawRoundRect(rectProgress, dpFromPx(context, height.toFloat()), dpFromPx(context, height.toFloat()), progressPaint)
            canvas.drawRoundRect(rect25, dpFromPx(context, height.toFloat()),dpFromPx(context, height.toFloat()),line25)

            Log.e("TAG", "onDraw positionThumb: $positionThumb")
            bitmap?.setBounds(
                (positionThumb - sizeThumb / 2).toInt(),
                (((height / 2).toFloat()) - sizeThumb / 2).toInt(),
                (positionThumb + sizeThumb / 2).toInt(),
                (((height / 2).toFloat()) + sizeThumb / 2).toInt()
            )
            bitmap?.draw(canvas)
        }

    fun callBackSeekBar(listener: Listener) {
        this.listener = listener
    }

    private fun moveThumb(x: Float) {
        positionThumb = if (x < sizeThumb / 2f) {
            sizeThumb / 2
        } else if (x > width - sizeThumb / 2f) {
            width - sizeThumb / 2f
        } else {
            x
        }
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {

        when (event.action) {

            MotionEvent.ACTION_DOWN, MotionEvent.ACTION_MOVE -> {
//                sizeThumb = context.convertDpToPx(46).toFloat()
                moveThumb(event.x)
                if (event.x >= width) {
                    sbProgress = (width * MAX / width).toInt()
                } else {
//                    sbProgress = ((positionThumb - 20f) * MAX / (width - 40f)).toInt()
                    sbProgress = ((positionThumb - sizeThumb/2) * MAX / (width - sizeThumb)).toInt()
                }


//                if (sbProgress < 10) {
//                    positonText = positionThumb - 10f
//                } else if (sbProgress >= 100) {
//                    positonText = positionThumb - sizeProgress
//                } else {
//                    positonText = positionThumb - 15f
//                }
                if (sbProgress in 18 until 22){
                    setProgress(20)
                }
                listener?.onProgress(sbProgress)
            }

            MotionEvent.ACTION_UP -> {
//                sizeThumb = context.convertDpToPx(36).toFloat()
//                moveThumb(event.x)
                listener?.onEnd(sbProgress)
            }

        }

        invalidate()
        return true
    }

    interface Listener {
        fun onProgress(progress: Int)
        fun onEnd(progress: Int)
    }
}
// DeviceDimensionsHelper.convertSpToPixel(25f, context) => (25sp converted to pixels)
fun Context.convertSpToPixel(sp: Float): Float {
    val r = resources
    return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, sp, r.displayMetrics)
}
fun Context.convertDpToPx(dp: Int): Int {
    val dip = dp.toFloat()
    return TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP,
        dip,
        resources.displayMetrics
    ).toInt()
}
