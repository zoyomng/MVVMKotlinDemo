package com.zoyo.mvvmkotlindemo.ui.view

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View

/**
 * zoyomng 2021/1/27
 */
class RegionView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {
    val paint = Paint().apply {
        color = Color.RED
    }

    val rect = Rect(100, 100, 300, 300)
    val region = Region(rect)

    val iterator = RegionIterator(region)
    val r = Rect()

    /**
     * 合并区域
     */
    //目标矩形
    val targetRect = Rect(600, 100, 650, 300)

    //原始区域
    val origionRegion = Region(600, 100, 700, 150).apply {
        union(targetRect)
    }
    val unionIterator = RegionIterator(origionRegion)


    /**
     *     剪裁区域
     */
    val ovalRect = RectF(400F, 50F, 550F, 500F)
    val ovalPath = Path().apply {
        addOval(ovalRect, Path.Direction.CCW)
    }

    val clip = Region(400, 50, 550, 200)
    val ovalRegion = Region().apply {
        setPath(ovalPath, clip)
    }
    val clipIterator = RegionIterator(ovalRegion)

    /**
     * 集合运算
     */
    val yellowRect = Rect(400, 100, 500, 400)
    val greenRect = Rect(300, 200, 600, 300)

    val greenRegion = Region(greenRect)
    val yellowRegion = Region(yellowRect).apply {
        op(greenRegion, Region.Op.XOR)
    }

    val opIterator = RegionIterator(yellowRegion)


    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {

        val widthMeasureMode = MeasureSpec.getMode(widthMeasureSpec)
        val widthMeasureSize = MeasureSpec.getSize(widthMeasureSpec)
        val heightMeasureMode = MeasureSpec.getMode(heightMeasureSpec)
        val heightMeasureSize = MeasureSpec.getSize(heightMeasureSpec)

        setMeasuredDimension(widthMeasureSize, heightMeasureSize)
    }

    override fun onDraw(canvas: Canvas) {
        //创建区域
//        while (iterator.next(r)) {
//            canvas.drawRect(r, paint)
//        }
        //合并区域
//        while (unionIterator.next(r)) {
//            canvas.drawRect(r, paint)
//        }
        //裁剪区域
//        while (clipIterator.next(r)) {
//            canvas.drawRect(r, paint)
//        }

        //集合运算
        paint.style = Paint.Style.STROKE

        paint.color = Color.YELLOW
        canvas.drawRect(yellowRect, paint)
        paint.color = Color.GREEN
        canvas.drawRect(greenRect, paint)

        paint.color = Color.RED
        paint.style = Paint.Style.FILL
        while (opIterator.next(r)) {
            canvas.drawRect(r, paint)
        }
    }
}