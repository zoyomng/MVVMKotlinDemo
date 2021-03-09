package com.zoyo.view.pie

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.view.animation.Animation
import android.view.animation.Transformation
import com.zoyo.core.extensions.px
import com.zoyo.core.utils.L
import java.util.*
import kotlin.math.cos
import kotlin.math.pow
import kotlin.math.sin

/**
 * zoyomng 2021/2/16
 */
class PieChartView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private var valueList = ArrayList<PieData>()
    private var percent: Float = 0F
    private var radius: Float = 0F
    private var centerX = 0F
    private var centerY = 0F

    private val extendedLineLength = 20F.px
    private val paddingTextToLine = 30F.px

    private val paint = Paint().apply {
        style = Paint.Style.FILL
        isAntiAlias = true
    }
    private val framePaint = Paint().apply {
        isAntiAlias = true
        style = Paint.Style.STROKE
        strokeWidth = 3F
        color = Color.WHITE
    }
    private val infoPaint = Paint().apply {
        isAntiAlias = true
        textSize = 40F.px
    }

    private val rectF = RectF()
    private val textRect = Rect()

    private val pieAnimation = object : Animation() {
        override fun applyTransformation(interpolatedTime: Float, t: Transformation?) {
            super.applyTransformation(interpolatedTime, t)
            percent = interpolatedTime
            invalidate()
        }
    }.apply {
        duration = 5000
    }

    init {
        animation = pieAnimation
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        val mWidth: Int
        val mheight: Int

        val widthMeasureSize = MeasureSpec.getSize(widthMeasureSpec)
        val widthMeasureMode = MeasureSpec.getMode(widthMeasureSpec)
        val heightMeasureSize = MeasureSpec.getSize(heightMeasureSpec)
        val heightMeasureMode = MeasureSpec.getMode(heightMeasureSpec)

        val min = Math.min(widthMeasureSize, heightMeasureSize)
        if (widthMeasureMode == MeasureSpec.EXACTLY) {
            mWidth = widthMeasureSize
        } else {
            mWidth = min
        }

        if (heightMeasureMode == MeasureSpec.EXACTLY) {
            mheight = heightMeasureSize
        } else {
            mheight = min
        }
        setMeasuredDimension(mWidth, mheight)

    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        val min = Math.min(w, h)
        radius = min * 0.3F
        centerX = min / 2F
        centerY = min / 2F
        rectF.set(centerX - radius, centerY - radius, centerX + radius, centerY + radius)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        var startAngle = 0F

        for (pieData in valueList) {
            paint.color = pieData.color
            val swapAngle = 360F * percent * pieData.value / 100
            //画扇形
            canvas.drawArc(rectF, startAngle, swapAngle, true, paint)
            //画扇形白边框
            canvas.drawArc(rectF, startAngle, swapAngle, true, framePaint)
            //画线和标题
            infoPaint.color = pieData.color
            val a = (startAngle + swapAngle / 2F) * Math.PI / 180
            val cosA = cos(a)
            val sinA = sin(a)
            val turnX: Float = (centerX + (radius + extendedLineLength) * cosA).toFloat()
            val turnY: Float = (centerY + (radius + extendedLineLength) * sinA).toFloat()
            val startX: Float = (centerX + radius * cosA).toFloat()
            val startY: Float = (centerY + radius * sinA).toFloat()

            canvas.drawLine(startX, startY, turnX, turnY, infoPaint)
            val title = pieData.title
            infoPaint.getTextBounds(title, 0, title.length, textRect)
            val h = textRect.height()
            val w = textRect.width()

            if (cosA > 0) {
                canvas.drawLine(turnX, turnY, turnX + extendedLineLength, turnY, infoPaint)
                canvas.drawText(title, turnX + paddingTextToLine, turnY + h / 2, infoPaint)
            } else {
                canvas.drawLine(turnX, turnY, turnX - extendedLineLength, turnY, infoPaint)
                canvas.drawText(title, turnX - w - paddingTextToLine, turnY + h / 2, infoPaint)
            }

            startAngle += swapAngle
        }
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        if (event.action == MotionEvent.ACTION_DOWN) {
            if ((event.x - centerX).pow(2) + (event.y - centerY).pow(2) <= radius.pow(2)) {
                //圆上
                val a = Math.atan2((event.y - centerY).toDouble(), (event.x - centerX).toDouble())
                L.e("$a")
                return true
            }
        }
        return super.onTouchEvent(event)
    }


    fun setData(list: ArrayList<PieData>) {
        if (list.size == 0) return
        valueList = list
    }
}