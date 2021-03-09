package com.zoyo.view

import android.content.Context
import android.graphics.*
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatImageView
import com.zoyo.core.extensions.dp

/**
 * zoyomng 2021/2/5
 * 图片裁剪
 */
class RoundImageView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : AppCompatImageView(context, attrs, defStyleAttr) {

    var viewMinSize: Int = 0
    var mRadius: Float = 0F

    /** 边框类型
     *  0-圆形
     *  1-圆角
     */
    var type = 0
    val TYPE_CIRCLE = 0
    val TYPE_ROUND = 1

    var mBorderRadius = 0F
    val BORDER_RADIUS_DEFAULT = 10F

    val mMatrix = Matrix()
    lateinit var mBitmapShader: BitmapShader
    var mRoundRect = RectF()

    val paint = Paint().apply {
        isAntiAlias = true
    }

    init {
        if (attrs != null) {
            val typedArray =
                context.obtainStyledAttributes(attrs, R.styleable.RoundImageView)
            mBorderRadius = typedArray.getDimensionPixelSize(
                R.styleable.RoundImageView_borderRadius,
                BORDER_RADIUS_DEFAULT.dp.toInt()
            ).toFloat()

            type = typedArray.getInt(R.styleable.RoundImageView_type, TYPE_CIRCLE)
            typedArray.recycle()
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)

        if (type == TYPE_CIRCLE) {
            viewMinSize = Math.min(measuredWidth, measuredHeight)
            mRadius = viewMinSize / 2F
            setMeasuredDimension(viewMinSize, viewMinSize)
        }
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        if (type == TYPE_ROUND) {
            mRoundRect = RectF(0F, 0F, width.toFloat(), height.toFloat())
        }
    }

    override fun onDraw(canvas: Canvas) {
        if (drawable == null) return

        setShader()
        if (type == TYPE_ROUND) {
            canvas.drawRoundRect(
                mRoundRect,
                mBorderRadius,
                mBorderRadius,
                paint
            )
        } else {
            canvas.drawCircle(
                mRadius, mRadius,
                mRadius, paint
            )
        }
    }

    private fun setShader() {
        val bmp = drawableToBitmap(drawable)
        mBitmapShader = BitmapShader(bmp, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP)
        var scale = 1.0F
        if (type == TYPE_CIRCLE) {
            val min = Math.min(bmp.width, bmp.height)
            scale = viewMinSize * 1.0F / min
        } else if (type == TYPE_ROUND) {
            //图片的宽高与View的宽高不匹配时,应计算出需要缩放的比例,缩放后的图片应大于View的宽高
            scale = Math.max(width * 1.0F / bmp.width, height * 1.0F / bmp.height)
        }
        //shader的变换矩阵
        mMatrix.setScale(scale, scale)
        mBitmapShader.setLocalMatrix(mMatrix)
        paint.shader = mBitmapShader
    }

    private fun drawableToBitmap(drawable: Drawable): Bitmap {
        if (drawable is BitmapDrawable) {
            return drawable.bitmap
        }
        val intrinsicWidth = drawable.intrinsicWidth
        val intrinsicHeight = drawable.intrinsicHeight
        val bitmap =
            Bitmap.createBitmap(intrinsicWidth, intrinsicHeight, Bitmap.Config.ARGB_8888)
        drawable.setBounds(0, 0, intrinsicWidth, intrinsicHeight)
        drawable.draw(Canvas())
        return bitmap
    }
}