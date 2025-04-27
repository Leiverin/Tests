package com.triversoft.diary.ui.views

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.view.View
import com.triversoft.diary.R

class LineDashGapView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : View(context, attrs) {

    private var dashWidth = context.resources.getDimension(com.intuit.sdp.R.dimen._3sdp)
    private var dashGap = context.resources.getDimension(com.intuit.sdp.R.dimen._2sdp)

    private val paint = Paint().apply {
        isAntiAlias = true
        color = Color.parseColor("#CFCFCF")
        style = Paint.Style.FILL
    }

    init {
        initAttrs(attrs)
    }

    private fun initAttrs(attrs: AttributeSet?) {
        val a = context.obtainStyledAttributes(attrs, R.styleable.LineDashGapView)
        dashWidth = a.getDimension(R.styleable.LineDashGapView_ldgv_dash_width, dashWidth)
        dashGap = a.getDimension(R.styleable.LineDashGapView_ldgv_dash_gap, dashGap)
        paint.color = a.getColor(R.styleable.LineDashGapView_color, Color.parseColor("#CFCFCF"))
        a.recycle()
        postInvalidate()
    }

    @SuppressLint("DrawAllocation")
    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        val dashWidthCount = (width / (dashWidth + dashGap)).toInt()
        for (i in 0 until dashWidthCount){
            val left = dashWidth * i + dashGap * i
            val rect = RectF(left, 0f, left + dashWidth, height.toFloat())
            canvas.drawRoundRect(rect, height / 2f, height / 2f, paint)
        }
    }

}