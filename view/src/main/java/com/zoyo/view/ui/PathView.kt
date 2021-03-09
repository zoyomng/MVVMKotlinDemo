package com.zoyo.view

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import com.zoyo.core.utils.L

/**
 * zoyomng 2021/1/24
 * @JvmOverloads:指示Kotlin编译器为这个函数生成替代默认参数值的重载
 * 如果一个方法有N个参数，其中M个参数有默认值，则会产生M个重载
 * 对应Java中重载,自定义控件中有多个构造方法重载
 */
class PathView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) :
    View(context, attrs, defStyleAttr) {
    val paint = Paint().apply {
        L.e("paint初始化")
        isAntiAlias = true
        color = Color.RED

        /**画笔宽度
         * drawCircle()时Style=STORKE/FILL_AND_STROKE时生效
         * drawLine()时充作线宽,与Style无关
         */
        strokeWidth = 20F
    }
    val subPaint = Paint().apply {
        isAntiAlias = true
        color = Color.BLUE
        style = Paint.Style.FILL
        strokeWidth = 10F
    }
    val radius = 40F

    val rect = Rect(410, 500, 500, 600)
    val rectf = RectF(510F, 500F, 600F, 600F)
    val roundRectf = RectF(610F, 500F, 900F, 700F)
    val path = Path().apply {
        moveTo(200F, 700F)
        lineTo(400F, 900F)
        lineTo(200F, 900F)
        close()
    }
    val arcRectf = RectF(600F, 800F, 900F, 1000F)

    //填充弧度路径
    val fillArcPath = Path().apply {
        //起始角:指定弧度从哪开始(X轴正方向为0°,顺时针为正)
        //扫描角:弧的角度
        arcTo(arcRectf, 0F, 90F)
    }

    //描边弧度路径
    val strokeArcPath = Path().apply {
        arcTo(arcRectf, 90F, 90F)
    }

    //混合路径
    val hybridPath = Path().apply {
        //设置起点
        moveTo(200F, 1000F)
        //设置直线的终点
        lineTo(400F, 1200F)
        //添加弧度路径
        addArc(RectF(300F, 1100F, 500F, 1200F), 0F, 90F)
    }

    //路径方向
    val directionPath = Path().apply {
        moveTo(600F, 1000F)
        addOval(RectF(500F, 1000F, 700F, 1300F), Path.Direction.CCW)
    }

    /** 路径填充:Path的填充与Paint的填充模式不同
     * 1.WINDING-填充路径内部
     * 2.EVEN_ODD-填充路径相交区域
     * 3.INVERSE_WINDING-填充路径外部
     * 4.INVERSE_EVEN_ODD-填充路径的外部与相交区域
     */
    val pathForFillType = Path().apply {
        addRect(800F, 1100F, 1000F, 1300F, Path.Direction.CW)
        addCircle(800F, 1100F, 100F, Path.Direction.CW)
        fillType = Path.FillType.EVEN_ODD
    }

//    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
//        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
//        val mWidth: Int
//        val mHeight: Int
//
//        val widthMeasureMode = MeasureSpec.getMode(widthMeasureSpec)
//        val widthMeasureSize = MeasureSpec.getSize(widthMeasureSpec)
//        val heightMeasureMode = MeasureSpec.getMode(heightMeasureSpec)
//        val heightMeasureSize = MeasureSpec.getSize(heightMeasureSpec)
//        val min = Math.min(widthMeasureSize, heightMeasureSize)
//        if (widthMeasureMode == MeasureSpec.EXACTLY)
//            mWidth = widthMeasureSize
//        else
//            mWidth = min
//
//        if (heightMeasureMode == MeasureSpec.EXACTLY)
//            mHeight = heightMeasureSize
//        else
//            mHeight = min
//
//        setMeasuredDimension(mWidth, mHeight)
//    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        //设置画布背景(画布背景要在其他图形绘制前设置,否则背景色会覆盖已绘制图形)
        canvas.drawColor(Color.DKGRAY)
        //画圆
        canvas.drawCircle(200F, 100F, radius, paint)

        //描边
        paint.style = Paint.Style.STROKE
        canvas.drawCircle(200F, 200F, radius, paint)
        //填充
        paint.style = Paint.Style.FILL
        //再次设置画笔颜色,对当前画笔后面绘制所有图形生效
        paint.color = Color.WHITE
        canvas.drawCircle(500F, 200F, radius, paint)
        //描边且填充
        paint.style = Paint.Style.FILL_AND_STROKE
        canvas.drawCircle(800F, 200F, radius, paint)

        //绘制直线(startX startY确定起始点,stopX stopY确定终止点)
        canvas.drawLine(200F, 300F, 400F, 300F, paint)
        canvas.drawLine(200F, 300F, 400F, 450F, subPaint)

        //绘制点(点的大小取决于strokeWidth)
        canvas.drawPoint(500F, 300F, paint)
        canvas.drawPoint(600F, 300F, subPaint)

        //矩形(3种方式,rect与rectF数据类型不同)
        paint.style = Paint.Style.STROKE
        canvas.drawRect(200F, 500F, 400F, 600F, paint)
        canvas.drawRect(rect, subPaint)
        canvas.drawRect(rectf, subPaint)

        //圆角矩形
        canvas.drawRoundRect(roundRectf, radius, radius, subPaint)

        //路径绘制
        paint.style = Paint.Style.STROKE
        canvas.drawPath(path, paint)
        canvas.drawPath(path, subPaint)

        //弧线绘制
        paint.style = Paint.Style.FILL
        paint.color = Color.GREEN
        //绘制背景,用于辅助辨认
        canvas.drawOval(arcRectf, subPaint)
        //绘制填充型弧线
//        canvas.drawPath(fillArcPath, paint)
        //绘制描边型弧线
        subPaint.run {
            style = Paint.Style.STROKE
            strokeWidth = 10F
            color = Color.RED
        }
        canvas.drawPath(strokeArcPath, subPaint)

        //路径重置-路径一旦被重置,保存的所有路径都会被清空,这样就不需要重建一个Path对象了
        strokeArcPath.apply {

            reset() //不清除填充类型FillType
//            rewind()//清除FillType,保留内部数据结构,以便复用(前提是重绘相同路径,数据结构才能复用)
            arcTo(arcRectf, 270F, 90F)
        }
        canvas.drawPath(strokeArcPath, subPaint)

        //改变起点->重置起点(forceMoveTo:true-从弧形上点开始绘制,false-以起点开始绘制 )
        fillArcPath.moveTo(left.toFloat(), top.toFloat())
        fillArcPath.arcTo(arcRectf, 180F, 90F, true)
        canvas.drawPath(fillArcPath, paint)

        //混合路径
        canvas.drawPath(hybridPath, subPaint)

        //路径方向
        paint.run {
            style = Paint.Style.STROKE
            color = Color.YELLOW
            strokeWidth = 3F
            textSize = 20F
        }
        canvas.drawTextOnPath("这是逆时针", directionPath, 0F, 0F, paint)
        canvas.drawPath(directionPath, paint)

        //路径填充(前提:恢复Paint默认的Fill类型)
        paint.style = Paint.Style.FILL
        canvas.drawPath(pathForFillType, paint)

    }

}