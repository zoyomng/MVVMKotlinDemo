package com.zoyo.mvvmkotlindemo.ui.view.pie

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.view.animation.Animation
import android.view.animation.Transformation
import androidx.core.graphics.not
import com.zoyo.mvvmkotlindemo.core.extensions.px
import com.zoyo.mvvmkotlindemo.core.utils.L
import java.util.*
import kotlin.math.cos
import kotlin.math.pow
import kotlin.math.sin

/**
 * zoyomng 2021/2/17
 */
class RingChartView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {


    private var valueList = ArrayList<PieData>()
    private var percent: Float = 0F

    private val extendedLineLength = 30F.px
    private val paddingTextToLine = 30F.px

    private var outerRadius: Float = 0F
    private var innerRadius: Float = 0F
    private var gap: Float = 0F

    private val outerArcRectF = RectF()
    private val innerArcRectF = RectF()
    private val outerArcRectFTouched = RectF()

    private var path = Path()
    private val innerPath = Path()
    private val outerPath = Path()

    private var centerX = 0F
    private var centerY = 0F

    //用于计算环形路径上点的边界1
    private val tempRectF = RectF()

    //用于剪切边界1的区域2
    private var clipRegion = Region()

    //边界1与区域2的共同区域即每个数据对应的环形区域
    private val sectionRegion: Region
        get() {
            return Region().apply {
                setPath(path, clipRegion)
            }
        }

    private val textRect = Rect()


    private val paint = Paint().apply {
        style = Paint.Style.FILL
        strokeWidth = 1F
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

    private val pieAnimation = object : Animation() {
        override fun applyTransformation(interpolatedTime: Float, t: Transformation?) {
            super.applyTransformation(interpolatedTime, t)
            percent = interpolatedTime
            L.e("$percent")
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
        innerRadius = min * 0.2F
        outerRadius = min * 0.3F
        gap = min * 0.01F

        centerX = min / 2F
        centerY = min / 2F
        innerArcRectF.set(
            centerX - innerRadius,
            centerY - innerRadius,
            centerX + innerRadius,
            centerY + innerRadius
        )
        outerArcRectF.set(
            centerX - outerRadius,
            centerY - outerRadius,
            centerX + outerRadius,
            centerY + outerRadius
        )

        outerArcRectFTouched.set(
            centerX - outerRadius - gap,
            centerY - outerRadius - gap,
            centerX + outerRadius + gap,
            centerY + outerRadius + gap
        )
    }


    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        var startAngle = 0F

        for (pieData in valueList) {
            paint.color = pieData.color
            val sweepAngle = 360F * percent * pieData.value / 100

            //通过路径运算绘制环形
            innerPath.run {
                reset()
                moveTo(centerX, centerY)
                arcTo(
                    innerArcRectF,
                    startAngle,
                    sweepAngle
                )
            }
            outerPath.run {
                reset()
                moveTo(centerX, centerY)
                arcTo(
                    if (pieData.isTouched) outerArcRectFTouched else outerArcRectF,
                    startAngle,
                    sweepAngle
                )
            }

            path.run {
                reset()
                op(outerPath, innerPath, Path.Op.DIFFERENCE)
            }

            canvas.drawPath(path, paint)


            //将绘制完的path赋值,进行点击判断
            if (percent == 1F) {
                tempRectF.setEmpty()
                path.computeBounds(tempRectF, true)

                clipRegion.setEmpty()
                clipRegion.set(
                    tempRectF.left.toInt(),
                    tempRectF.top.toInt(),
                    tempRectF.right.toInt(),
                    tempRectF.bottom.toInt()
                )

//                val sectionRegion = Region()
//                pieData.region = sectionRegion.apply {
//                    setPath(path, clipRegion)
//                }

                pieData.region = sectionRegion
            }


            //画扇形白边框
            canvas.drawPath(path, framePaint)
            //画线和标题
            infoPaint.color = pieData.color
            val a = (startAngle + sweepAngle / 2F) * Math.PI / 180
            val cosA = cos(a)
            val sinA = sin(a)
            val turnX: Float = (centerX + (outerRadius + extendedLineLength) * cosA).toFloat()
            val turnY: Float = (centerY + (outerRadius + extendedLineLength) * sinA).toFloat()
            val startX: Float = (centerX + outerRadius * cosA).toFloat()
            val startY: Float = (centerY + outerRadius * sinA).toFloat()

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

            startAngle += sweepAngle
        }
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        if (event.action == MotionEvent.ACTION_DOWN) {
            val x = event.x
            val y = event.y
            val distance = (x - centerX).pow(2) + (y - centerY).pow(2)
            if (distance >= innerRadius.pow(2) && distance <= outerRadius.pow(2)) {
                //扇形上
                valueList.map {
                    val region = it.region
                    it.isTouched = region != null && region.contains(x.toInt(), y.toInt())
                }
                invalidate()
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