package com.zoyo.mvvmkotlindemo.ui.view

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import com.zoyo.mvvmkotlindemo.R
import com.zoyo.mvvmkotlindemo.core.extensions.px
import com.zoyo.mvvmkotlindemo.core.utils.L

/**
 * zoyomng 2021/1/28
 * 使用 Canvas 的绘制方法有下面三个需要注意的点。
 *1.生成新图层
 *每次调用绘制方法 drawXXX 时，都会产生一个新的 Canvas 透明图层。
 *2.操作不可逆
 *调用了绘制方法前，平移和旋转等函数对 Canvas 进行了操作，那么这个操作是不可逆的，每次产生的画布的最新位置都是这些操作后的位置。
 *3.超出不显示
 *在 Canvas 图层与屏幕合成时，超出屏幕范围的图像是不会显示出来的
 */
class CanvasBasicView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    val paint = Paint().apply {
        color = Color.RED
        strokeWidth = 2F
        textSize = 30F.px
    }


    val subPaint = Paint().apply {
        color = Color.GREEN
        strokeWidth = 2F
        textSize = 30F.px
    }

    val rect = Rect(0, 0, 100, 100)
    val path = Path().apply {
        //x轴
        moveTo(0F, 0F)
        lineTo(300F, 0F)

        moveTo(300F, 0F)
        lineTo(280F, 5F)
        lineTo(280F, -5F)
        close()

        //y轴
        moveTo(0F, 0F)
        lineTo(0F, 300F)

        moveTo(0F, 300F)
        lineTo(5F, 280F)
        lineTo(-5F, 280F)
        close()
    }


    //圆形头像
    val pathLeftOffset = 60F.px
    val pathTopOffset = 60F.px


    val rectPaint = Paint().apply {
        isAntiAlias = true
        color = Color.DKGRAY
    }
    val bitmapPaint = Paint().apply {
        isAntiAlias = true
    }

    /**
     * 成员变量(get()形式的)被调用几次,get()方法会调用几次
     * 主构造方法->init()->次构造方法
     */
    val clipPath: Path
        get() {
            L.e("成员变量")
            val width = cropAndScaleBitmap.width
            val height = cropAndScaleBitmap.height
            return Path().apply {
                addCircle(
                    width / 2 + pathLeftOffset,
                    height / 2 + pathTopOffset,
                    width / 2F,
                    Path.Direction.CCW
                )
            }
        }

    val rect1: RectF
        get() {
            val left = 220F.px
            val top = 50F.px
//        val width = 80F.px
            return RectF(left, top, left + width, top + width)
        }

    val cropAndScaleBitmap: Bitmap
        get() {
            L.e("cropAndScaleBitmap")
            return cropAndScaleBitmap()
        }

    init {
        L.e("init方法")
        setLayerType(LAYER_TYPE_SOFTWARE, null)
    }

    private fun cropAndScaleBitmap(): Bitmap {
        //获取原图
        val bitmap = BitmapFactory.decodeResource(resources, R.drawable.avatar)
        //裁剪以短边尺寸的正方形
        val minSize = Math.min(bitmap.width, bitmap.height)
        val cropBitmap = Bitmap.createBitmap(bitmap, 0, 0, minSize, minSize, null, false)
        val matrix = Matrix()
        val scale = 400F.px / cropBitmap.width
        matrix.postScale(scale, scale)
        val resultBitmap = Bitmap.createBitmap(
            cropBitmap,
            0,
            0,
            cropBitmap.width,
            cropBitmap.height,
            matrix,
            false
        )
        if (!cropBitmap.isRecycled)
            cropBitmap.recycle()

        return resultBitmap
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {

        val widthMeasureMode = MeasureSpec.getMode(widthMeasureSpec)
        val widthMeasureSize = MeasureSpec.getSize(widthMeasureSpec)
        val heightMeasureMode = MeasureSpec.getMode(heightMeasureSpec)
        val heightMeasureSize = MeasureSpec.getSize(heightMeasureSpec)

        setMeasuredDimension(widthMeasureSize, heightMeasureSize)
    }


    override fun onDraw(canvas: Canvas) {
        //原坐标轴
//        drawAxis(canvas, paint)
//
//        canvas.translate(200F, 200F)
//        drawAxis(canvas, subPaint)
//
//        canvas.translate(0F, 0F)

        //圆形头像
        canvas.drawColor(Color.RED)
        canvas.save()

        canvas.clipPath(clipPath)
        canvas.drawColor(Color.GREEN)
        canvas.save()

        canvas.drawBitmap(cropAndScaleBitmap, pathLeftOffset, pathTopOffset, bitmapPaint)

        canvas.restore()
        canvas.drawRect(rect1, rectPaint)
    }


    private fun drawAxis(canvas: Canvas, paint: Paint) {
        paint.style = Paint.Style.STROKE
        canvas.drawPath(path, paint)
        paint.style = Paint.Style.FILL
        canvas.drawRect(rect, paint)
        paint.color = Color.BLACK
        canvas.drawText("(0,0)", 10F, 40F, paint)
    }


}