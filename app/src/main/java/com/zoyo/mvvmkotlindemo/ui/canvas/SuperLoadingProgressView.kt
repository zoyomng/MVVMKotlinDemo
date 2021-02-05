package com.zoyo.mvvmkotlindemo.ui.canvas

import android.animation.Animator
import android.animation.Animator.*
import android.animation.AnimatorListenerAdapter
import android.animation.ValueAnimator
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator
import android.view.animation.AccelerateInterpolator
import android.view.animation.LinearInterpolator
import com.zoyo.mvvmkotlindemo.core.utils.L
import kotlin.math.cos
import kotlin.math.sin

/**
 * zoyomng 2021/1/29
 */
class SuperLoadingProgressView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    //当前进度
    var progress = 0

    //最大进度
    val maxProgress = 100

    /**
     * 0:画圆
     * 1:抛出方块
     * 2:下落变粗,挤压圆形
     * 3:绘制三叉,圆形恢复
     * 4:绿色勾
     * 5:红色感叹号出现
     * 6:红色感叹号震动
     */
    var status = 0

    val startAngle = -90

    val strokeWidth = 20F

    //
    var mWidth: Int = 0
    var mHeight: Int = 0
    var unitSize: Float = 0F
    var mRectF: RectF = RectF()

    val color = Color.argb(255, 48, 63, 159)
    val pos1 = FloatArray(2)
    val tan1 = FloatArray(2)
    val pos2 = FloatArray(2)
    val tan2 = FloatArray(2)
    val pos3 = FloatArray(2)
    val tan3 = FloatArray(2)


    val circlePaint = Paint().apply {
        isAntiAlias = true
        color = this@SuperLoadingProgressView.color
        strokeWidth = this@SuperLoadingProgressView.strokeWidth
        style = Paint.Style.STROKE
    }

    /**
     * 假设方块终点居圆心为2*radius,可计算出:方块抛弧半径arcRadius = 5 * radius / 2
     *
     *方块始末端相对方块抛弧圆心的夹角:a,方块高:h
     * tan a = h/(5*radius/2) -->a
     * Math.atan()用于计算反正切，返回的角度范围在 -pi/2 到 pi/2 之间,
     * a = Math.atan(smallRectHeight / arcRadius) / Math.PI * 180
     */
    val smallRectHeight = 40F
    var arcRadius: Float = 0F

    var arcAngle: Float = 0F

    val smallRectPaint = Paint().apply {
        isAntiAlias = true
        color = this@SuperLoadingProgressView.color
        strokeWidth = this@SuperLoadingProgressView.strokeWidth / 2F
        style = Paint.Style.STROKE

    }

    var isSuccess: Boolean = false
    val endAngle = Math.atan(4.0 / 3).toFloat()
    var curSweepAngle: Float = 0.0F

    val mRotateAnim = ValueAnimator.ofFloat(0F, endAngle).apply {
        duration = 5000
        interpolator = AccelerateDecelerateInterpolator()
        addUpdateListener {
            curSweepAngle = it.animatedValue as Float
            invalidate()
        }

        addListener(object : AnimatorListenerAdapter() {

            override fun onAnimationEnd(animation: Animator?) {
                //动画结束
                curSweepAngle = 0.0F
                if (isSuccess) {
                    status = 2
                    mDownAnim.start()
                } else {
                    status = 5
                    mCommaAnim.start()
                }
            }

        })

    }

    /**小方块下落绘制
     *
     *
     */

    val downRectPaint = Paint().apply {
        isAntiAlias = true
        color = this@SuperLoadingProgressView.color
        strokeWidth = this@SuperLoadingProgressView.strokeWidth
        style = Paint.Style.FILL
    }

    //下落路径
    var downPath1: Path = Path()
    var downPath2: Path = Path()
    val downPathMeasure1: PathMeasure = PathMeasure()

    val downPathMeasure2: PathMeasure = PathMeasure()

    //下落动画
    var downPercent: Float = 0.0f
    val mDownAnim = ValueAnimator.ofFloat(0F, 1F).apply {
        duration = 5000
        interpolator = AccelerateInterpolator()
        addUpdateListener {
            downPercent = it.animatedValue as Float
            invalidate()
        }
        addListener(object : AnimatorListenerAdapter() {

            override fun onAnimationEnd(animation: Animator?) {
                //动画结束
                status = 3
                mForkAnim.start()
            }

        })
    }

    //分叉动画
    val forkPath1 = Path()
    val forkPath2 = Path()
    val forkPath3 = Path()

    val sin60 = Math.sin(Math.PI / 3)
    val cos60 = Math.cos(Math.PI / 3)

    var forkPrecent: Float = 0.0f
    val forkPathMeasure1: PathMeasure = PathMeasure()
    val forkPathMeasure2: PathMeasure = PathMeasure()
    val forkPathMeasure3: PathMeasure = PathMeasure()

    val mForkAnim = ValueAnimator.ofFloat(0f, 1f).apply {
        duration = 3000
        interpolator = LinearInterpolator()
        addUpdateListener {
            forkPrecent = it.animatedValue as Float
            invalidate()
        }

        addListener(object : AnimatorListenerAdapter() {

            override fun onAnimationEnd(animation: Animator?) {
                mTickAnim.start()
            }
        })

    }

    /** 打钩
     *
     */
    var tickPrecent = 0F
    val tickPath = Path()
    val tickPathMeasure = PathMeasure()
    val tickPaint = Paint().apply {
        isAntiAlias = true
        color = Color.argb(255, 0, 150, 136)
        strokeWidth = this@SuperLoadingProgressView.strokeWidth
        style = Paint.Style.STROKE
    }

    val mTickAnim = ValueAnimator.ofFloat(0F, 1F).apply {
        startDelay = 3000
        duration = 5000
        interpolator = AccelerateInterpolator()
        addUpdateListener {
            tickPrecent = it.animatedValue as Float
            invalidate()
        }
        addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationStart(animation: Animator?) {
                super.onAnimationStart(animation)
                status = 4
            }
        })
    }


    /** 感叹号
     *
     */
    var commaPrecent = 0F
    val commaPath1 = Path()
    val commaPath2 = Path()
    val commaPathMeasure1 = PathMeasure()
    val commaPathMeasure2 = PathMeasure()
    val commaPaint = Paint().apply {
        isAntiAlias = true
        color = Color.argb(255, 229, 57, 53)
        strokeWidth = this@SuperLoadingProgressView.strokeWidth
        style = Paint.Style.STROKE
    }
    val mCommaAnim = ValueAnimator.ofFloat(0F, 1F).apply {
        duration = 300
        interpolator = AccelerateInterpolator()
        addUpdateListener {
            commaPrecent = it.animatedValue as Float
            invalidate()
        }
        addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator?) {
                super.onAnimationEnd(animation)
                status = 6
                mShockCommaAnim.start()
            }
        })
    }

    /** 感叹号震动
     *
     */
    //震动角度
    val shockDir = 20F
    var shockPercent = 0
    val mShockCommaAnim = ValueAnimator.ofInt(-1, 0, 1, 0, -1, 0, 1, 0).apply {
        duration = 500
        interpolator = LinearInterpolator()
        addUpdateListener {
            shockPercent = it.animatedValue as Int
            invalidate()
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        val widthMeasureSize = MeasureSpec.getSize(widthMeasureSpec)
        val widthMeasureMode = MeasureSpec.getMode(widthMeasureSpec)
        val heightMeasureSize = MeasureSpec.getSize(heightMeasureSpec)
        val heightMeasureMode = MeasureSpec.getMode(heightMeasureSpec)
        val minSize = Math.min(widthMeasureSize, heightMeasureSize)
        setMeasuredDimension(minSize, minSize)

    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        mWidth = w
        mHeight = h
        //TODO 暂且把宽高最小尺寸的1/4作为半径,不考虑strokeWidth
        unitSize = Math.min(measuredWidth, measuredHeight) / 4.0F

        arcRadius = 5 * unitSize / 2
        arcAngle = Math.atan(1.0 * smallRectHeight / arcRadius).toFloat()
        mRectF.set(
            unitSize,
            unitSize,
            3 * unitSize,
            3 * unitSize
        )

        //方块下落
        downPath1.run {
            moveTo(2 * unitSize, smallRectHeight)
            lineTo(2 * unitSize, (unitSize + smallRectHeight))
        }
        downPath2.run {
            moveTo(2 * unitSize, smallRectHeight)
            lineTo(2 * unitSize, 2 * unitSize)
        }

        downPathMeasure1.setPath(downPath1, false)
        downPathMeasure2.setPath(downPath2, false)

        //分叉
        forkPath1.apply {
            moveTo(2 * unitSize, 2 * unitSize)
            lineTo(2 * unitSize, 3 * unitSize)
        }
        forkPath2.apply {
            moveTo(2 * unitSize, 2 * unitSize)
            lineTo(
                (2 * unitSize - unitSize * sin60).toFloat(),
                (2 * unitSize + unitSize * cos60).toFloat()
            )
        }
        forkPath3.apply {
            moveTo(2 * unitSize, 2 * unitSize)
            lineTo(
                (2 * unitSize + unitSize * sin60).toFloat(),
                (2 * unitSize + unitSize * cos60).toFloat()
            )
        }
        forkPathMeasure1.setPath(forkPath1, false)
        forkPathMeasure2.setPath(forkPath2, false)
        forkPathMeasure3.setPath(forkPath3, false)

        //打钩路径
        tickPath.apply {
            moveTo(1.5F * unitSize, 2 * unitSize)
            lineTo(1.8F * unitSize, 2.3F * unitSize)
            lineTo(2.5F * unitSize, 1.7F * unitSize)
        }
        tickPathMeasure.setPath(tickPath, false)

        //感叹号路径
        commaPath1.moveTo(2 * unitSize, 1.25F * unitSize)
        commaPath1.lineTo(2 * unitSize, 2.25F * unitSize)
        commaPath2.moveTo(2 * unitSize, 2.76F * unitSize)
        commaPath2.lineTo(2 * unitSize, 2.5F * unitSize)
        commaPathMeasure1.setPath(commaPath1, false)
        commaPathMeasure2.setPath(commaPath2, false)
        super.onSizeChanged(w, h, oldw, oldh)

    }

    override fun onDraw(canvas: Canvas) {
        when (status) {
            0 -> {
                val percent: Float = 1.0F * progress / maxProgress
                canvas.drawArc(
                    mRectF,
                    startAngle - 270 * percent,
                    -(60 + 300 * percent),
                    false,
                    circlePaint
                )
            }
            1 -> drawSmallRect(canvas)
            2 -> drawSmallRectDown(canvas)
            3 -> drawFork(canvas)
            4 -> drawTick(canvas)
            5 -> drawComma(canvas)
            6 -> drawShockComma(canvas)
        }
    }

    private fun drawShockComma(canvas: Canvas) {
        val path1 = Path()
        commaPathMeasure1.getSegment(0F, commaPathMeasure1.length, path1, true)
        path1.rLineTo(0F, 0F)
        val path2 = Path()
        commaPathMeasure2.getSegment(0F, commaPathMeasure2.length, path2, true)
        path2.rLineTo(0F, 0F)
        if (shockPercent != 0) {
            canvas.save()
            if (shockPercent == 1) {
                canvas.rotate(shockDir, 2 * unitSize, 2 * unitSize)
            } else if (shockPercent == -1) {
                canvas.rotate(-shockDir, 2 * unitSize, 2 * unitSize)
            }
        }

        canvas.drawPath(path1, commaPaint)
        canvas.drawPath(path2, commaPaint)
        canvas.drawArc(mRectF, 0F, 360F, false, commaPaint)
        if (shockPercent != 0) {
            canvas.restore()
        }
    }

    private fun drawComma(canvas: Canvas) {
        val path1 = Path()
        commaPathMeasure1.getSegment(0F, commaPrecent * commaPathMeasure1.length, path1, true)
        path1.rLineTo(0F, 0F)
        val path2 = Path()
        commaPathMeasure2.getSegment(0F, commaPrecent * commaPathMeasure2.length, path2, true)
        path2.rLineTo(0F, 0F)
        canvas.drawPath(path1, commaPaint)
        canvas.drawPath(path2, commaPaint)
        canvas.drawArc(mRectF, 0F, 360F, false, commaPaint)

    }

    private fun drawTick(canvas: Canvas) {
        val tempPath = Path()
        tickPathMeasure.getSegment(0F, tickPrecent * tickPathMeasure.length, tempPath, true)
        tempPath.rLineTo(0F, 0F)
        canvas.drawPath(tempPath, tickPaint)
        canvas.drawArc(mRectF, 0F, 360F, false, tickPaint)
    }

    private fun drawFork(canvas: Canvas) {
        forkPathMeasure1.getPosTan(forkPrecent * forkPathMeasure1.length, pos1, tan1)
        forkPathMeasure2.getPosTan(forkPrecent * forkPathMeasure2.length, pos2, tan2)
        forkPathMeasure3.getPosTan(forkPrecent * forkPathMeasure3.length, pos3, tan3)

        canvas.drawLine(2 * unitSize, unitSize, 2 * unitSize, 2 * unitSize, downRectPaint)
        canvas.drawLine(2 * unitSize, 2 * unitSize, pos1[0], pos1[1], downRectPaint)
        canvas.drawLine(2 * unitSize, 2 * unitSize, pos2[0], pos2[1], downRectPaint)
        canvas.drawLine(2 * unitSize, 2 * unitSize, pos3[0], pos3[1], downRectPaint)

        //椭圆区域-恢复圆形
        val fl = mRectF.height() * 0.1F * (1 - forkPrecent)
        val rectF = RectF(
            mRectF.left,
            mRectF.top + fl,
            mRectF.right,
            mRectF.bottom - fl
        )
        canvas.drawArc(rectF, 0F, 360F, false, circlePaint)
    }

    private fun drawSmallRectDown(canvas: Canvas) {

        //下落方块的起始端坐标
        downPathMeasure1.getPosTan(downPercent * downPathMeasure1.length, pos1, tan1)

        //下落方块的末端坐标
        downPathMeasure2.getPosTan(downPercent * downPathMeasure2.length, pos2, tan2)

        /**椭圆形区域:圆/椭圆都是根据Rect绘制出来的
         * 圆环压缩成椭圆,椭圆的高是圆的0.8倍
         */
        val ovalRect = Rect(
            Math.round(mRectF.left),
            Math.round(mRectF.top + mRectF.height() * 0.1F * downPercent),
            Math.round(mRectF.right),
            Math.round(mRectF.bottom - mRectF.height() * 0.1F * downPercent)
        )
        //非交集-左右加粗smallRectHeight / 4
        val region1 = Region(
            Math.round(pos1[0] - smallRectHeight / 4),
            Math.round(pos1[1]),
            Math.round(pos2[0] + smallRectHeight / 4),
            Math.round(pos2[1])
        )
        region1.op(ovalRect, Region.Op.DIFFERENCE)
        drawRegion(canvas, region1, downRectPaint)

        //交集-左右加粗smallRectHeight / 2
        val region2 = Region(
            Math.round(pos1[0] - smallRectHeight / 2),
            Math.round(pos1[1]),
            Math.round(pos2[0] + smallRectHeight / 2),
            Math.round(pos2[1])
        )
        val isIntersect = region2.op(ovalRect, Region.Op.INTERSECT)
        drawRegion(canvas, region2, downRectPaint)

        //椭圆区域
        if (isIntersect) {
            //如果有交集
            val extrusionPrecent = (pos2[1] - unitSize) / unitSize
            //值动画过程中对应的椭圆挤压量
            val fl = mRectF.height() * 0.1F * extrusionPrecent
            val rectF = RectF(
                mRectF.left,
                mRectF.top + fl,
                mRectF.right,
                mRectF.bottom - fl
            )
            canvas.drawArc(rectF, 0F, 360F, false, circlePaint)
        } else {
            canvas.drawArc(mRectF, 0F, 360F, false, circlePaint)
        }

    }

    private fun drawRegion(canvas: Canvas, region: Region, paint: Paint) {
        val iter = RegionIterator(region)
        val rect = Rect()
        while (iter.next(rect)) {
            canvas.drawRect(rect, paint)
        }
    }

    private fun drawSmallRect(canvas: Canvas) {
        canvas.save()
        //将坐标移至小方块抛弧对应圆心,便于计算轨迹
        canvas.translate(unitSize / 2, 2 * unitSize)
        //方块起始端坐标
        val x1 = arcRadius * cos(curSweepAngle)
        val y1 = -arcRadius * sin(curSweepAngle)

        //方块末端坐标
        val x2 = arcRadius * cos(curSweepAngle + arcAngle)
        val y2 = -arcRadius * sin(curSweepAngle + arcAngle)

        canvas.drawLine(x1, y1, x2, y2, smallRectPaint)
        canvas.restore()
        canvas.drawArc(mRectF, 0F, 360F, false, circlePaint)
    }


    fun setProgressValue(progress: Int) {
        this.progress = Math.min(progress, maxProgress)
        postInvalidate()
        if (progress <= maxProgress) status = 0
    }

    fun finishLoading(isSuccess: Boolean) {
        setProgressValue(maxProgress)
        this.isSuccess = isSuccess
        status = 1
        startAnim()
    }


    private fun startAnim() {
        post {
            mRotateAnim.start()
        }
    }


}