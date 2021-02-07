package com.zoyo.mvvmkotlindemo.ui.view.spider

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import com.zoyo.mvvmkotlindemo.R
import kotlin.random.Random

/**
 * zoyomng 2021/2/6
 */

const val DEFAULT_POINT_RADIUS = 1
const val DEFAULT_LINE_WIDTH = 2F
const val DEFAULT_LINE_ALPHA = 150
const val DEFAULT_POINT_NUMBER = 50
const val DEFAULT_POINT_ACCELERATION = 10
const val DEFAULT_MAX_DISTANCE = 280
const val DEFAULT_TOUCH_POINT_RADIUS = 1F
const val DEFAULT_GRAVITATION_STRENGTH = 50

class SpiderView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    var mWidth: Int = 0
    var mHeight: Int = 0
    var pointRadius: Int = DEFAULT_POINT_RADIUS
    var pointNum: Int = DEFAULT_POINT_NUMBER
    val pointAcceleration = DEFAULT_POINT_ACCELERATION
    val maxDistance = DEFAULT_MAX_DISTANCE
    var lineWidth: Float = DEFAULT_LINE_WIDTH
    var lineAlpha: Int = DEFAULT_LINE_ALPHA
    var touchPointRadius: Float = DEFAULT_TOUCH_POINT_RADIUS
    var gravitationStrength = DEFAULT_GRAVITATION_STRENGTH

    val mSpiderPointList = ArrayList<SpiderPointData>()

    val pointPaint = Paint().apply {
        strokeWidth = pointRadius.toFloat()
        strokeCap = Paint.Cap.ROUND  //笔刷形状
        color = Color.parseColor("#EBFF4081")

    }
    val linePaint = Paint().apply {
        strokeWidth = lineWidth
        strokeCap = Paint.Cap.ROUND
        color = Color.parseColor("#EBFF94B9")
    }

    //手势触摸操作,用于处理滑动与拖拽
    val mGestureDetector: GestureDetector =
        GestureDetector(context, object : GestureDetector.SimpleOnGestureListener() {
            override fun onScroll(
                e1: MotionEvent,
                e2: MotionEvent,
                distanceX: Float,
                distanceY: Float
            ): Boolean {
                //单根手指操作
                if (e1.pointerCount == e2.pointerCount && e1.pointerCount == 1) {
                    mTouchX = e2.x
                    mTouchY = e2.y
                    return true
                }
                return super.onScroll(e1, e2, distanceX, distanceY)
            }

            override fun onFling(
                e1: MotionEvent,
                e2: MotionEvent,
                velocityX: Float,
                velocityY: Float
            ): Boolean {
                if (e1.pointerCount == e2.pointerCount && e1.pointerCount == 1) {
                    mTouchX = e2.x
                    mTouchY = e2.y
                    return true
                }

                return super.onFling(e1, e2, velocityX, velocityY)
            }

            override fun onDown(e: MotionEvent): Boolean {
                mTouchX = e.x
                mTouchY = e.y
                return true
            }

            override fun onSingleTapUp(e: MotionEvent?): Boolean {
                return false
            }
        })

    //触摸点坐标
    var mTouchX = -1F
    var mTouchY = -1F
    val touchPaint = Paint().apply {
        strokeWidth = touchPointRadius
        strokeCap = Paint.Cap.ROUND
        color = Color.parseColor("#D8FF7875")
    }

    init {
        val a = context.obtainStyledAttributes(attrs, R.styleable.SpiderView)
        pointRadius = a.getDimension(
            R.styleable.SpiderView_pointRadius,
            DEFAULT_POINT_RADIUS.toFloat()
        ).toInt()
        pointNum = a.getInt(R.styleable.SpiderView_pointNum, DEFAULT_POINT_NUMBER)
        lineWidth = a.getDimension(R.styleable.SpiderView_lineWidth, DEFAULT_LINE_WIDTH)
        lineAlpha = a.getInt(R.styleable.SpiderView_lineAlpha, DEFAULT_LINE_ALPHA)

        a.recycle()
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        mWidth = w
        mHeight = h
        restart()
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        canvas.save()

        //绘制触摸点
        if (mTouchX != -1F && mTouchY != -1F) {
            canvas.drawPoint(mTouchX, mTouchY, touchPaint)
        }

        //画点
        for (i in 0 until mSpiderPointList.size) {
            val point = mSpiderPointList[i]
            point.x += point.aX
            point.y += point.aY

            //越界反弹
            if (point.x <= pointRadius) {
                //左边界
                point.x = pointRadius
                point.aX = -point.aX
            } else if (point.x >= mWidth - pointRadius) {
                //右边界
                point.x = mWidth - pointRadius
                point.aX = -point.aX
            }

            if (point.y <= pointRadius) {
                point.y = pointRadius
                point.aY = -point.aY
            } else if (point.y >= mHeight - pointRadius) {
                point.y = mHeight - pointRadius
                point.aY = -point.aY
            }
            pointPaint.color = point.color
            canvas.drawCircle(
                point.x.toFloat(),
                point.y.toFloat(),
                point.r.toFloat(),
                pointPaint
            )

            //绘制触摸点与其他点连线
            if (mTouchX != -1F && mTouchY != -1F) {
                val dX = mTouchX - point.x
                val dY = mTouchY - point.y
                val distance = Math.sqrt((dX * dX + dY * dY).toDouble())
                if (distance <= maxDistance + gravitationStrength) {
                    //T(触摸点) > P(当前点) -> dX>0 ----P向T靠近----> P.x+dX
                    //T(触摸点) < P(当前点) -> dX<0 ----P向T靠近----> P.x+dX
                    if (distance > maxDistance - gravitationStrength) {
                        point.x += (0.03 * dX).toInt()
                        point.y += (0.03 * dY).toInt()
                    }

                    val alpha = (1.0 - distance / maxDistance) * lineAlpha
                    linePaint.color = point.color
                    linePaint.alpha = alpha.toInt()

                    canvas.drawLine(
                        point.x.toFloat(),
                        point.y.toFloat(),
                        mTouchX,
                        mTouchY,
                        linePaint
                    )
                }
            }

            //绘制连线
            for (j in i + 1 until mSpiderPointList.size) {
                val aroundPoint = mSpiderPointList[j]
                if (point != aroundPoint) {
                    val distance =
                        distanceBetweenPoints(point.x, point.y, aroundPoint.x, aroundPoint.y)
                    if (distance < maxDistance) {
                        val alpha = (1 - distance / maxDistance) * lineAlpha
                        linePaint.color = point.color
                        linePaint.alpha = alpha.toInt()
                        canvas.drawLine(
                            point.x.toFloat(),
                            point.y.toFloat(),
                            aroundPoint.x.toFloat(),
                            aroundPoint.y.toFloat(),
                            linePaint
                        )
                    }
                }
            }
        }
        canvas.restore()

        invalidate()
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        if (event.action == MotionEvent.ACTION_UP || event.action == MotionEvent.ACTION_CANCEL)
            resetTouchPoint()
        return mGestureDetector.onTouchEvent(event)
    }

    private fun distanceBetweenPoints(x: Int, y: Int, x1: Int, y1: Int): Double {
        return Math.sqrt(((x - x1) * (x - x1) + (y - y1) * (y - y1)).toDouble())
    }

    private fun restart() {
        resetTouchPoint()
        clearPointList()
        initPoint()
    }

    /**
     * 初始化小点
     */
    private fun initPoint() {
        for (i in 1..pointNum) {
            val width = Random.nextFloat() * mWidth
            val height = Random.nextFloat() * mHeight

            var aX = 0
            var aY = 0
            while (aX == 0) {
                aX = ((Random.nextFloat() - 0.5F) * pointAcceleration).toInt()
            }
            while (aY == 0) {
                aY = ((Random.nextFloat() - 0.5F) * pointAcceleration).toInt()
            }
            mSpiderPointList.add(
                SpiderPointData(
                    width.toInt(),
                    height.toInt(),
                    randomRGB(),
                    pointRadius,
                    aX,
                    aY,
                    0F,
                    0F
                )
            )
        }
    }

    private fun randomRGB(): Int {
        return Color.rgb(Random.nextInt(255), Random.nextInt(255), Random.nextInt(255))
    }

    /**
     * 清空数据源
     */
    private fun clearPointList() {
        mSpiderPointList.clear()
    }

    /**
     * 重置触摸点
     */
    private fun resetTouchPoint() {
        mTouchY = -1F
        mTouchX = -1F
    }


}