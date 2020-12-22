package com.zoyo.mvvmkotlindemo.ui.view

import android.content.Context
import android.content.res.Resources
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Typeface
import android.os.Build
import android.text.TextPaint
import android.util.AttributeSet
import android.view.View
import androidx.annotation.RequiresApi
import com.zoyo.mvvmkotlindemo.R
import java.text.NumberFormat
import java.util.*

/**
 * Copyright (c) dtelec, Inc All Rights Reserved.
 */
@RequiresApi(Build.VERSION_CODES.N)
internal class MonthView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
) :
    View(context, attrs, defStyleAttr) {

    private val mLocale: Locale
    private val mDayFormatter: NumberFormat
    private val dayPaint = TextPaint()
    private val weekTitlePaint = TextPaint()
    private val mDesiredDayHeight: Int
    private val mDesiredCellWidth: Int
    private var mPaddedWidth = 0
    private var mPaddedHeight = 0
    private var mDayHeight = 0
    private var mWeekLabelHeight = 0
    private var cellWidth = 0

    //    当月最大天数
    private var mDaysInMonth = 0

    //    指当月第一天是星期中的哪一天
    private var mDayOfWeekStart = 0

    val weekLabels = listOf<String>("日", "一", "二", "三", "四", "五", "六")

    //    一周的第一天以1为索引
    private var mWeekStart: Int = DEFAULT_WEEK_START

    private val mCalendar: Calendar
    private var mMonth: Int = 0
    private var mYear: Int = 0

    companion object {
        private const val MAX_WEEKS_IN_MONTH = 6
        private const val DAYS_IN_WEEK = 7
        private const val DEFAULT_WEEK_START = Calendar.SUNDAY

        private fun isValidDayOfWeek(day: Int): Boolean {
            return day >= Calendar.SUNDAY && day <= Calendar.SATURDAY
        }

        private fun isValidMonth(month: Int): Boolean {
            return month >= Calendar.JANUARY && month <= Calendar.DECEMBER
        }

        private fun getDaysInMonth(month: Int, year: Int): Int {
            return when (month) {
                Calendar.JANUARY, Calendar.MARCH, Calendar.MAY, Calendar.JULY, Calendar.AUGUST, Calendar.OCTOBER, Calendar.DECEMBER -> 31
                Calendar.APRIL, Calendar.JUNE, Calendar.SEPTEMBER, Calendar.NOVEMBER -> 30
                Calendar.FEBRUARY -> if (year % 4 == 0) 29 else 28
                else -> 0
            }
        }
    }

    init {
        val res = context.resources
        mDesiredDayHeight = res.getDimensionPixelSize(R.dimen.day_cell_hight)
        mDesiredCellWidth = res.getDimensionPixelSize(R.dimen.day_cell_width)

        //获取区域设置列表中首选项
        mLocale = res.configuration.locales.get(0)
        mCalendar = Calendar.getInstance(mLocale)
        mDayFormatter = NumberFormat.getIntegerInstance(mLocale)
        initPaint(res)
    }

    private fun initPaint(res: Resources) {
        val dayTextSize = res.getDimensionPixelSize(R.dimen.day_text_size)
        val weekTitleTextSize = res.getDimensionPixelSize(R.dimen.week_Title_text_size)
        val weekTitleBg = res.getColor(R.color.weekTitleBg, context.theme)

        dayPaint.run {
            isAntiAlias = true
            textSize = dayTextSize.toFloat()
            typeface = Typeface.create("sans-serif", Typeface.NORMAL)
            textAlign = Paint.Align.CENTER
            style = Paint.Style.FILL
        }
        weekTitlePaint.run {
            isAntiAlias = true
            textSize = weekTitleTextSize.toFloat()
            typeface = Typeface.create("sans-serif", Typeface.NORMAL)
            textAlign = Paint.Align.CENTER
            style = Paint.Style.FILL
            bgColor = weekTitleBg
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        //首选高度
        val preferredHeight: Int =
            mDesiredDayHeight * MAX_WEEKS_IN_MONTH + paddingTop + paddingBottom
        //首选宽度
        val preferredWidth: Int = mDesiredCellWidth * DAYS_IN_WEEK + paddingStart + paddingEnd

        /**
         * 确定尺寸-根据测量模式
         * 1.MeasureSpec.AT_MOST:尽可能的大,但是不能超指定尺寸 int specSize = MeasureSpec.getSize(measureSpec)
         * 2.MeasureSpec.EXACTLY:只能使用Parent给定的尺寸
         * 3.MeasureSpec.UNSPECIFIED:Parent没有限制,可能超出边界
         */
        val resolvedWidth = resolveSize(preferredWidth, widthMeasureSpec)
        val resolvedHeight = resolveSize(preferredHeight, heightMeasureSpec)
        //设置尺寸
        setMeasuredDimension(resolvedWidth, resolvedHeight)
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        //控件宽高
        val w = right - left
        val h = bottom - top
        //控件内容的宽高
        val contentWidth = w - paddingRight - paddingLeft
        val contentHeight = h - paddingBottom - paddingTop
        if (contentWidth == mPaddedWidth || contentHeight == mPaddedHeight) {
            return
        }
        mPaddedWidth = contentWidth
        mPaddedHeight = contentHeight

        val measuredPaddedHeight = measuredHeight - paddingTop - paddingBottom
        val scaleH = contentHeight / measuredPaddedHeight.toFloat()


        //单个日期宽度
        val cellWidth: Int = mPaddedWidth / DAYS_IN_WEEK
        mDayHeight = (mDesiredDayHeight * scaleH).toInt()

        //weekTitle单个cell
        mWeekLabelHeight = cellWidth

        this.cellWidth = cellWidth

    }

    override fun onDraw(canvas: Canvas) {
        val paddingLeft = paddingLeft
        val paddingTop = paddingTop
        canvas.translate(paddingLeft.toFloat(), paddingTop.toFloat())
        drawWeekTitle(canvas)
        drawDays(canvas)
        canvas.translate(-paddingLeft.toFloat(), -paddingTop.toFloat())
    }

    private fun drawWeekTitle(canvas: Canvas) {
        val p = weekTitlePaint
        val rowHeight = cellWidth
        val colWidth = cellWidth
        val rowCenter = rowHeight / 2
        val halfLineHeight = (p.ascent() + p.descent()) / 2f
        var label = 0
        while (label < DAYS_IN_WEEK) {
            val colCenter = colWidth * label + colWidth / 2
            canvas.drawText(
                weekLabels[label],
                colCenter.toFloat(),
                rowCenter.toFloat(),
                p
            )
            label++
        }
    }

    private fun drawDays(canvas: Canvas) {
        val p = dayPaint
        val rowHeight = mDayHeight
        val colWidth = cellWidth
        var rowCenter = mWeekLabelHeight + rowHeight / 2

        val halfLineHeight = (p.ascent() + p.descent()) / 2f
        var day = 1
        var col = findDayOffset()
        while (day <= mDaysInMonth) {
            val colCenter = colWidth * col + colWidth / 2
            canvas.drawText(
                mDayFormatter.format(day.toLong()),
                colCenter.toFloat(),
                rowCenter - halfLineHeight,
                p
            )
            col++

            if (col == DAYS_IN_WEEK) {
                //换行
                col = 0
                rowCenter += rowHeight
            }
            day++
        }
    }

    private fun findDayOffset(): Int {
        //当月第一天是星期三(4),默认第一天是星期日(1),offset= 4-1
        val offset = mDayOfWeekStart - mWeekStart
        return if (mDayOfWeekStart < mWeekStart) {
            offset + DAYS_IN_WEEK
        } else offset
    }

    fun setMonthParams(month: Int, year: Int) {
        if (isValidMonth(month)) {
            mMonth = month
        }
        mYear = year
        mCalendar[Calendar.MONTH] = mMonth
        mCalendar[Calendar.YEAR] = mYear
        mCalendar[Calendar.DAY_OF_MONTH] = 1
        mDayOfWeekStart = mCalendar[Calendar.DAY_OF_WEEK]

        mDaysInMonth = getDaysInMonth(mMonth, year)

    }

}