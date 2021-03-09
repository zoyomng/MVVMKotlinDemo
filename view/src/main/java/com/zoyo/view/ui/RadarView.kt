package com.zoyo.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.util.AttributeSet
import android.view.View

/**
 * zoyomng 2021/1/25
 * 雷达控件
 */
class RadarView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    //数据个数
    val count = 6
    val maxValue = 6
    val value = doubleArrayOf(2.0, 5.0, 1.0, 6.0, 4.0, 3.0)

    /** π是弧度制 180°是角度制 一弧度代表半径为一的圆中，长度为一的圆弧所对应的角度
     * 角度和弧度关系是：2π弧度=360°。从而1°≈0.0174533弧度，1弧度≈57.29578°。
     * 1) 角度转换为弧度公式：弧度=角度÷180×π
     * 2)弧度转换为角度公式： 角度=弧度×180÷π
     */
    val arc: Double = 2 * Math.PI / count
    val angle: Float = 360F / count


    val radarPaint = Paint().apply {
        isAntiAlias = true
        style = Paint.Style.STROKE
        color = Color.GREEN
        strokeWidth = 2F
        style = Paint.Style.STROKE
    }
    val raderPath = Path()
    val linePath = Path()

    val valuePaint = Paint().apply {
        isAntiAlias = true
        color = Color.RED
        style = Paint.Style.FILL
    }
    val valuePath = Path()

    var radius: Float = 0F
    var centerX: Float = 0F
    var centerY: Float = 0F

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        //设定网状半径大小占当前控件的90%
        radius = Math.min(w, h) / 2 * 0.9F
        centerX = w / 2F
        centerY = h / 2F

        //size发生改变,重新绘制
        postInvalidate()
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val mWidth: Int
        val mHeight: Int

        val widthMeasureMode = MeasureSpec.getMode(widthMeasureSpec)
        val widthMeasureSize = MeasureSpec.getSize(widthMeasureSpec)
        val heightMeasureMode = MeasureSpec.getMode(heightMeasureSpec)
        val heightMeasureSize = MeasureSpec.getSize(heightMeasureSpec)
        val min = Math.min(widthMeasureSize, heightMeasureSize)
        if (widthMeasureMode == MeasureSpec.EXACTLY)
            mWidth = widthMeasureSize
        else
            mWidth = min

        if (heightMeasureMode == MeasureSpec.EXACTLY)
            mHeight = heightMeasureSize
        else
            mHeight = min

        setMeasuredDimension(mWidth, mHeight)
    }


    override fun onDraw(canvas: Canvas) {

//        drawNoByCanvasTranslateOrRotate(canvas)

        drawByCanvasTranslateAndRotate(canvas)

    }

    /**
     * 未对画布进行特殊操作
     */
    private fun drawNoByCanvasTranslateOrRotate(canvas: Canvas) {
        //1.绘制蛛网
        drawPolygon(canvas)
        //        drawPolygonByOnce(canvas)
        //2.画线
        drawLines(canvas)
        //3.画值
        drawValue(canvas)
    }

    /**
     * 通过将画布原点平移至屏幕中心,然后画布旋转绘制蛛网
     */
    private fun drawByCanvasTranslateAndRotate(canvas: Canvas) {
        //将画布原点平移至屏幕中心
        canvas.translate(centerX, centerY)
        //通过Canvas旋转
        drawPolygonByCanvasRotate(canvas)
        drawValueAfterCanvasTranslate(canvas)
    }


    private fun drawValue(canvas: Canvas) {
        for (i in 0 until count) {
            val percent = value[i] / maxValue
            val x: Float = (centerX + radius * percent * Math.cos(arc * i)).toFloat()
            val y: Float = (centerY + radius * percent * Math.sin(arc * i)).toFloat()
            if (i == 0) {
                valuePath.moveTo(x, centerY)
            } else {
                valuePath.lineTo(x, y)
            }
            //绘制点
            valuePaint.alpha = 255
            canvas.drawCircle(x, y, 10F, valuePaint)
        }

        //绘制填充区域
        valuePaint.alpha = 127
        canvas.drawPath(valuePath, valuePaint)
    }

    private fun drawLines(canvas: Canvas) {
        for (i in 1..count) {
            linePath.reset()
            linePath.moveTo(centerX, centerY)
            val x = centerX + radius * Math.cos(arc * i)
            val y = centerY + radius * Math.sin(arc * i)
            linePath.lineTo(x.toFloat(), y.toFloat())
            canvas.drawPath(linePath, radarPaint)
        }
    }

    private fun drawPolygon(canvas: Canvas) {
        val gap = radius / count
        for (i in 1..count) {
            //reset()路径重置,旧数据需要擦除,否则会重复绘制
            raderPath.reset()
            val currentR = gap * i
            for (j in 0 until count) {
                if (j == 0) {
                    raderPath.moveTo(centerX + currentR, centerY)
                } else {
                    val x: Double = centerX + currentR * Math.cos(arc * j)
                    val y: Double = centerY + currentR * Math.sin(arc * j)
                    raderPath.lineTo(x.toFloat(), y.toFloat())
                }
            }
            raderPath.close()
            canvas.drawPath(raderPath, radarPaint)
        }
    }


    /**
     * 画布平移至屏幕中心后画值
     */
    private fun drawValueAfterCanvasTranslate(canvas: Canvas) {
        for (i in 0 until count) {
            val percent = value[i] / maxValue
            val x: Float = (radius * percent * Math.cos(arc * i)).toFloat()
            val y: Float = (radius * percent * Math.sin(arc * i)).toFloat()
            if (i == 0) {
                valuePath.moveTo(x, 0F)
            } else {
                valuePath.lineTo(x, y)
            }
            //绘制点
            valuePaint.alpha = 255
            canvas.drawCircle(x, y, 10F, valuePaint)
        }

        //绘制填充区域
        valuePaint.alpha = 127
        canvas.drawPath(valuePath, valuePaint)
    }

    /**
     * 画布平移至屏幕中心后,通过旋转画布画网
     */
    private fun drawPolygonByCanvasRotate(canvas: Canvas) {
        val gap = radius / count

        //直线
        linePath.moveTo(0F, 0F)
        linePath.lineTo(radius, 0F)

        for (i in 1..count) {
            val currentR = gap * i
            //网
            raderPath.moveTo(currentR, 0F)

            val x: Double = currentR * Math.cos(arc)
            val y: Double = currentR * Math.sin(arc)
            raderPath.lineTo(x.toFloat(), y.toFloat())
        }

        for (j in 1..count) {
//            if (j != 1) canvas.rotate(angle)
            canvas.rotate(angle)
            //raderPath.reset() 路径不需要重置,因为是画布旋转,路径不变,所以不用重建路径
            canvas.drawPath(raderPath, radarPaint)
            //raderPath.close() 总共画了count次,正好闭合
            canvas.drawPath(linePath, radarPaint)
        }
    }


    /**
     * 制定路径,一次绘制
     */
    private fun drawPolygonByOnce(canvas: Canvas) {
        val gap = radius / count
        for (i in 1..count) {
            //reset()路径重置,旧数据需要擦除,否则会重复绘制
            //raderPath.reset()
            val currentR = gap * i
            for (j in 0 until count) {
                if (j == 0) {
                    raderPath.moveTo(centerX + currentR, centerY)
                } else {
                    val x: Double = centerX + currentR * Math.cos(arc * j)
                    val y: Double = centerY + currentR * Math.sin(arc * j)
                    raderPath.lineTo(x.toFloat(), y.toFloat())
                }
            }
            raderPath.close()
        }
        canvas.drawPath(raderPath, radarPaint)
    }
}