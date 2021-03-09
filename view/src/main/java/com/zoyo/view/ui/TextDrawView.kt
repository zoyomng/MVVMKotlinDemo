package com.zoyo.view

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import com.zoyo.core.extensions.px

/**
 * zoyomng 2021/1/26
 */
class TextDrawView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {
    val paint = Paint().apply {
        color = Color.DKGRAY
        strokeWidth = 3F
        isAntiAlias = true
        textSize = 40F.px
    }

    val subPaint = Paint().apply {
        color = Color.DKGRAY
        strokeWidth = 3F
        isAntiAlias = true
        textSize = 30F.px
    }

    val path = Path()
    val radius = 200F
    val bounds = Rect()

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {

        val widthMeasureMode = MeasureSpec.getMode(widthMeasureSpec)
        val widthMeasureSize = MeasureSpec.getSize(widthMeasureSpec)
        val heightMeasureMode = MeasureSpec.getMode(heightMeasureSpec)
        val heightMeasureSize = MeasureSpec.getSize(heightMeasureSpec)

        setMeasuredDimension(widthMeasureSize, heightMeasureSize)
    }

    override fun onDraw(canvas: Canvas) {
        //文本填充方式:默认-FILL
        canvas.drawText("文本", 100F, 100F, paint)

        paint.style = Paint.Style.STROKE
        canvas.drawText("文本", 300F, 100F, paint)

        paint.style = Paint.Style.FILL_AND_STROKE
        canvas.drawText("文本", 500F, 100F, paint)

        //对齐方式:默认-左对齐
        canvas.drawText("左对齐", 500F, 300F, paint)

        paint.textAlign = Paint.Align.CENTER
        canvas.drawText("中间对齐", 500F, 400F, paint)

        paint.textAlign = Paint.Align.RIGHT
        canvas.drawText("右对齐", 500F, 500F, paint)

        canvas.drawLine(300F, 200F, 300F, 600F, paint)

        //文本变形
        paint.textAlign = Paint.Align.LEFT
        canvas.drawText("正常字体", 300F, 700F, paint)
        paint.textSkewX = -0.25F
        canvas.drawText("倾斜", 300F, 800F, paint)
        paint.textScaleX = 2F
        canvas.drawText("横向拉升", 300F, 900F, paint)
        paint.isFakeBoldText = true
        canvas.drawText("加粗", 300F, 1000F, paint)
        paint.isUnderlineText = true
        canvas.drawText("下划线", 300F, 1100F, paint)
        paint.isStrikeThruText = true
        canvas.drawText("删除线", 300F, 1200F, paint)

        //路径绘制文字
        subPaint.style = Paint.Style.STROKE
        path.addCircle(600F, 1500F, radius, Path.Direction.CW)

        canvas.drawPath(path, subPaint)
        subPaint.style = Paint.Style.FILL
        canvas.drawTextOnPath("路径绘制", path, 0F, 0F, subPaint)
        subPaint.color = Color.RED
        canvas.drawTextOnPath("路径绘制", path, 30F, 0F, subPaint)
        canvas.drawTextOnPath("路径绘制", path, 0F, 30F, subPaint)
        canvas.drawTextOnPath("路径绘制", path, 0F, -30F, subPaint)

        //设置字体样式
        paint.typeface = Typeface.MONOSPACE
        canvas.drawText("这是", 100F, 1800F, paint)
        paint.typeface = Typeface.SANS_SERIF
        canvas.drawText("系统", 300F, 1800F, paint)
        paint.typeface = Typeface.SERIF
        canvas.drawText("字体", 500F, 1800F, paint)
        paint.typeface = Typeface.defaultFromStyle(Typeface.BOLD)
        canvas.drawText("粗体", 100F, 1900F, paint)
        paint.typeface = Typeface.defaultFromStyle(Typeface.ITALIC)
        canvas.drawText("斜体", 300F, 1900F, paint)
        paint.typeface = Typeface.defaultFromStyle(Typeface.BOLD_ITALIC)
        canvas.drawText("粗斜体", 500F, 1900F, paint)

        //根据字体名加载字体
        paint.typeface = Typeface.create("宋体", Typeface.NORMAL)
        canvas.drawText("宋体", 100F, 2000F, paint)
        //根据字体资源名加载
//        paint.typeface = Typeface.createFromAsset(resources.assets, fontName)
        //根据文件名加载
//        paint.typeface = Typeface.createFromFile(filePath)

        //获取字体宽高
        paint.typeface = Typeface.defaultFromStyle(Typeface.NORMAL)
        canvas.drawText("宽${paint.measureText("宋体")}", 300F, 2000F, paint)
        paint.getTextBounds("宋体", 0, 1, bounds)
        canvas.drawRect(bounds, paint)
    }
}