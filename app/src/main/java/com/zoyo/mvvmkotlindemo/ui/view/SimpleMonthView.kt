//package app.icmes.dtelec.com.icmes_app.view
//
//import android.content.Context
//import android.content.res.ColorStateList
//import android.content.res.Resources
//import android.content.res.TypedArray
//import android.graphics.Canvas
//import android.graphics.Paint
//import android.graphics.Rect
//import android.graphics.Typeface
//import android.icu.text.DisplayContext
//import android.icu.text.SimpleDateFormat
//import android.icu.util.Calendar
//import android.os.Bundle
//import android.text.TextPaint
//import android.text.format.DateFormat
//import android.util.AttributeSet
//import android.util.IntArray
//import android.util.MathUtils
//import android.util.StateSet
//import android.view.KeyEvent
//import android.view.MotionEvent
//import android.view.PointerIcon
//import android.view.View
//import android.view.accessibility.AccessibilityEvent
//import android.view.accessibility.AccessibilityNodeInfo
//import com.android.internal.R
//import com.android.internal.widget.ExploreByTouchHelper
//import libcore.icu.LocaleData
//import java.text.NumberFormat
//import java.util.*
//
///**
// * Copyright (c) dtelec, Inc All Rights Reserved.
// */
///*
// * Copyright (C) 2014 The Android Open Source Project
// *
// * Licensed under the Apache License, Version 2.0 (the "License");
// * you may not use this file except in compliance with the License.
// * You may obtain a copy of the License at
// *
// *      http://www.apache.org/licenses/LICENSE-2.0
// *
// * Unless required by applicable law or agreed to in writing, software
// * distributed under the License is distributed on an "AS IS" BASIS,
// * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// * See the License for the specific language governing permissions and
// * limitations under the License.
// */
//package android.widget
//import android.util.IntArray
//import android.util.MathUtils
//import com.android.internal.R
//import com.android.internal.widget.ExploreByTouchHelper
//import libcore.icu.LocaleData
//
///**
// * A calendar-like view displaying a specified month and the appropriate selectable day numbers
// * within the specified month.
// */
//internal class SimpleMonthView @JvmOverloads constructor(
//    context: Context,
//    attrs: AttributeSet? = null,
//    defStyleAttr: Int = R.attr.datePickerStyle,
//    defStyleRes: Int = 0
//) :
//    View(context, attrs, defStyleAttr, defStyleRes) {
//    private val mMonthPaint = TextPaint()
//    private val mDayOfWeekPaint = TextPaint()
//    private val mDayPaint = TextPaint()
//    private val mDaySelectorPaint = Paint()
//    private val mDayHighlightPaint = Paint()
//    private val mDayHighlightSelectorPaint = Paint()
//
//    /** Array of single-character weekday labels ordered by column index.  */
//    private val mDayOfWeekLabels = arrayOfNulls<String>(7)
//    private val mCalendar: Calendar
//    private val mLocale: Locale
//    private val mTouchHelper: MonthViewTouchHelper
//    private val mDayFormatter: NumberFormat
//
//    // Desired dimensions.
//    private val mDesiredMonthHeight: Int
//    private val mDesiredDayOfWeekHeight: Int
//    private val mDesiredDayHeight: Int
//    private val mDesiredCellWidth: Int
//    private val mDesiredDaySelectorRadius: Int
//    var monthYearLabel: String? = null
//        private set
//    private var mMonth = 0
//    private var mYear = 0
//
//    // Dimensions as laid out.
//    var monthHeight = 0
//        private set
//    private var mDayOfWeekHeight = 0
//    private var mDayHeight = 0
//    var cellWidth = 0
//        private set
//    private var mDaySelectorRadius = 0
//    private var mPaddedWidth = 0
//    private var mPaddedHeight = 0
//
//    /** The day of month for the selected day, or -1 if no day is selected.  */
//    private var mActivatedDay = -1
//
//    /**
//     * The day of month for today, or -1 if the today is not in the current
//     * month.
//     */
//    private var mToday: Int = android.widget.SimpleMonthView.Companion.DEFAULT_SELECTED_DAY
//
//    /** The first day of the week (ex. Calendar.SUNDAY) indexed from one.  */
//    private var mWeekStart: Int = android.widget.SimpleMonthView.Companion.DEFAULT_WEEK_START
//
//    /** The number of days (ex. 28) in the current month.  */
//    private var mDaysInMonth = 0
//
//    /**
//     * The day of week (ex. Calendar.SUNDAY) for the first day of the current month
//     * month.
//     */
//    private var mDayOfWeekStart = 0
//
//    /** The day of month for the first (inclusive) enabled day.  */
//    private var mEnabledDayStart = 1
//
//    /** The day of month for the last (inclusive) enabled day.  */
//    private var mEnabledDayEnd = 31
//
//    /** Optional listener for handling day click actions.  */
//    private var mOnDayClickListener: OnDayClickListener? = null
//    private var mDayTextColor: ColorStateList? = null
//    private var mHighlightedDay = -1
//    private var mPreviouslyHighlightedDay = -1
//    private var mIsTouchHighlighted = false
//    private fun updateMonthYearLabel() {
//        val format = DateFormat.getBestDateTimePattern(
//            mLocale,
//            android.widget.SimpleMonthView.Companion.MONTH_YEAR_FORMAT
//        )
//        val formatter = SimpleDateFormat(format, mLocale)
//        formatter.setContext(DisplayContext.CAPITALIZATION_FOR_STANDALONE)
//        monthYearLabel = formatter.format(mCalendar.time)
//    }
//
//    private fun updateDayOfWeekLabels() {
//        // Use tiny (e.g. single-character) weekday names from ICU. The indices
//        // for this list correspond to Calendar days, e.g. SUNDAY is index 1.
//        val tinyWeekdayNames: Array<String> = LocaleData.get(mLocale).tinyWeekdayNames
//        for (i in 0 until android.widget.SimpleMonthView.Companion.DAYS_IN_WEEK) {
//            mDayOfWeekLabels[i] =
//                tinyWeekdayNames[(mWeekStart + i - 1) % android.widget.SimpleMonthView.Companion.DAYS_IN_WEEK + 1]
//        }
//    }
//
//    /**
//     * Applies the specified text appearance resource to a paint, returning the
//     * text color if one is set in the text appearance.
//     *
//     * @param p the paint to modify
//     * @param resId the resource ID of the text appearance
//     * @return the text color, if available
//     */
//    private fun applyTextAppearance(p: Paint, resId: Int): ColorStateList? {
//        val ta: TypedArray = mContext.obtainStyledAttributes(
//            null,
//            R.styleable.TextAppearance, 0, resId
//        )
//        val fontFamily = ta.getString(R.styleable.TextAppearance_fontFamily)
//        if (fontFamily != null) {
//            p.typeface = Typeface.create(fontFamily, 0)
//        }
//        p.textSize = ta.getDimensionPixelSize(
//            R.styleable.TextAppearance_textSize, p.textSize.toInt()
//        ).toFloat()
//        val textColor = ta.getColorStateList(R.styleable.TextAppearance_textColor)
//        if (textColor != null) {
//            val enabledColor = textColor.getColorForState(ENABLED_STATE_SET, 0)
//            p.color = enabledColor
//        }
//        ta.recycle()
//        return textColor
//    }
//
//    fun setMonthTextAppearance(resId: Int) {
//        applyTextAppearance(mMonthPaint, resId)
//        invalidate()
//    }
//
//    fun setDayOfWeekTextAppearance(resId: Int) {
//        applyTextAppearance(mDayOfWeekPaint, resId)
//        invalidate()
//    }
//
//    fun setDayTextAppearance(resId: Int) {
//        val textColor = applyTextAppearance(mDayPaint, resId)
//        if (textColor != null) {
//            mDayTextColor = textColor
//        }
//        invalidate()
//    }
//
//    /**
//     * Sets up the text and style properties for painting.
//     */
//    private fun initPaints(res: Resources) {
//        val monthTypeface = res.getString(R.string.date_picker_month_typeface)
//        val dayOfWeekTypeface = res.getString(R.string.date_picker_day_of_week_typeface)
//        val dayTypeface = res.getString(R.string.date_picker_day_typeface)
//        val monthTextSize = res.getDimensionPixelSize(
//            R.dimen.date_picker_month_text_size
//        )
//        val dayOfWeekTextSize = res.getDimensionPixelSize(
//            R.dimen.date_picker_day_of_week_text_size
//        )
//        val dayTextSize = res.getDimensionPixelSize(
//            R.dimen.date_picker_day_text_size
//        )
//        mMonthPaint.isAntiAlias = true
//        mMonthPaint.textSize = monthTextSize.toFloat()
//        mMonthPaint.typeface = Typeface.create(monthTypeface, 0)
//        mMonthPaint.textAlign = Paint.Align.CENTER
//        mMonthPaint.style = Paint.Style.FILL
//        mDayOfWeekPaint.isAntiAlias = true
//        mDayOfWeekPaint.textSize = dayOfWeekTextSize.toFloat()
//        mDayOfWeekPaint.typeface = Typeface.create(dayOfWeekTypeface, 0)
//        mDayOfWeekPaint.textAlign = Paint.Align.CENTER
//        mDayOfWeekPaint.style = Paint.Style.FILL
//        mDaySelectorPaint.isAntiAlias = true
//        mDaySelectorPaint.style = Paint.Style.FILL
//        mDayHighlightPaint.isAntiAlias = true
//        mDayHighlightPaint.style = Paint.Style.FILL
//        mDayHighlightSelectorPaint.isAntiAlias = true
//        mDayHighlightSelectorPaint.style = Paint.Style.FILL
//
//        mDayPaint.isAntiAlias = true
//        mDayPaint.textSize = dayTextSize.toFloat()
//        mDayPaint.typeface = Typeface.create(dayTypeface, 0)
//        mDayPaint.textAlign = Paint.Align.CENTER
//        mDayPaint.style = Paint.Style.FILL
//    }
//
//    fun setMonthTextColor(monthTextColor: ColorStateList) {
//        val enabledColor = monthTextColor.getColorForState(ENABLED_STATE_SET, 0)
//        mMonthPaint.color = enabledColor
//        invalidate()
//    }
//
//    fun setDayOfWeekTextColor(dayOfWeekTextColor: ColorStateList) {
//        val enabledColor = dayOfWeekTextColor.getColorForState(ENABLED_STATE_SET, 0)
//        mDayOfWeekPaint.color = enabledColor
//        invalidate()
//    }
//
//    fun setDayTextColor(dayTextColor: ColorStateList?) {
//        mDayTextColor = dayTextColor
//        invalidate()
//    }
//
//    fun setDaySelectorColor(dayBackgroundColor: ColorStateList) {
//        val activatedColor = dayBackgroundColor.getColorForState(
//            StateSet.get(StateSet.VIEW_STATE_ENABLED or StateSet.VIEW_STATE_ACTIVATED), 0
//        )
//        mDaySelectorPaint.color = activatedColor
//        mDayHighlightSelectorPaint.color = activatedColor
//        mDayHighlightSelectorPaint.alpha =
//            android.widget.SimpleMonthView.Companion.SELECTED_HIGHLIGHT_ALPHA
//        invalidate()
//    }
//
//    fun setDayHighlightColor(dayHighlightColor: ColorStateList) {
//        val pressedColor = dayHighlightColor.getColorForState(
//            StateSet.get(StateSet.VIEW_STATE_ENABLED or StateSet.VIEW_STATE_PRESSED), 0
//        )
//        mDayHighlightPaint.color = pressedColor
//        invalidate()
//    }
//
//    fun setOnDayClickListener(listener: OnDayClickListener?) {
//        mOnDayClickListener = listener
//    }
//
//    public override fun dispatchHoverEvent(event: MotionEvent): Boolean {
//        // First right-of-refusal goes the touch exploration helper.
//        return mTouchHelper.dispatchHoverEvent(event) || super.dispatchHoverEvent(event)
//    }
//
//    override fun onTouchEvent(event: MotionEvent): Boolean {
//        val x = (event.x + 0.5f).toInt()
//        val y = (event.y + 0.5f).toInt()
//        val action = event.action
//        when (action) {
//            MotionEvent.ACTION_DOWN, MotionEvent.ACTION_MOVE -> {
//                val touchedItem = getDayAtLocation(x, y)
//                mIsTouchHighlighted = true
//                if (mHighlightedDay != touchedItem) {
//                    mHighlightedDay = touchedItem
//                    mPreviouslyHighlightedDay = touchedItem
//                    invalidate()
//                }
//                if (action == MotionEvent.ACTION_DOWN && touchedItem < 0) {
//                    // Touch something that's not an item, reject event.
//                    return false
//                }
//            }
//            MotionEvent.ACTION_UP -> {
//                val clickedDay = getDayAtLocation(x, y)
//                onDayClicked(clickedDay)
//                // Reset touched day on stream end.
//                mHighlightedDay = -1
//                mIsTouchHighlighted = false
//                invalidate()
//            }
//            MotionEvent.ACTION_CANCEL -> {
//                mHighlightedDay = -1
//                mIsTouchHighlighted = false
//                invalidate()
//            }
//        }
//        return true
//    }
//
//    override fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {
//        // We need to handle focus change within the SimpleMonthView because we are simulating
//        // multiple Views. The arrow keys will move between days until there is no space (no
//        // day to the left, top, right, or bottom). Focus forward and back jumps out of the
//        // SimpleMonthView, skipping over other SimpleMonthViews in the parent ViewPager
//        // to the next focusable View in the hierarchy.
//        var focusChanged = false
//        when (event.keyCode) {
//            KeyEvent.KEYCODE_DPAD_LEFT -> if (event.hasNoModifiers()) {
//                focusChanged = moveOneDay(isLayoutRtl())
//            }
//            KeyEvent.KEYCODE_DPAD_RIGHT -> if (event.hasNoModifiers()) {
//                focusChanged = moveOneDay(!isLayoutRtl())
//            }
//            KeyEvent.KEYCODE_DPAD_UP -> if (event.hasNoModifiers()) {
//                ensureFocusedDay()
//                if (mHighlightedDay > 7) {
//                    mHighlightedDay -= 7
//                    focusChanged = true
//                }
//            }
//            KeyEvent.KEYCODE_DPAD_DOWN -> if (event.hasNoModifiers()) {
//                ensureFocusedDay()
//                if (mHighlightedDay <= mDaysInMonth - 7) {
//                    mHighlightedDay += 7
//                    focusChanged = true
//                }
//            }
//            KeyEvent.KEYCODE_DPAD_CENTER, KeyEvent.KEYCODE_ENTER -> if (mHighlightedDay != -1) {
//                onDayClicked(mHighlightedDay)
//                return true
//            }
//            KeyEvent.KEYCODE_TAB -> {
//                var focusChangeDirection = 0
//                if (event.hasNoModifiers()) {
//                    focusChangeDirection = FOCUS_FORWARD
//                } else if (event.hasModifiers(KeyEvent.META_SHIFT_ON)) {
//                    focusChangeDirection = FOCUS_BACKWARD
//                }
//                if (focusChangeDirection != 0) {
//                    val parent = parent
//                    // move out of the ViewPager next/previous
//                    var nextFocus: View? = this
//                    do {
//                        nextFocus = nextFocus!!.focusSearch(focusChangeDirection)
//                    } while (nextFocus != null && nextFocus !== this && nextFocus.parent === parent)
//                    if (nextFocus != null) {
//                        nextFocus.requestFocus()
//                        return true
//                    }
//                }
//            }
//        }
//        return if (focusChanged) {
//            invalidate()
//            true
//        } else {
//            super.onKeyDown(keyCode, event)
//        }
//    }
//
//    private fun moveOneDay(positive: Boolean): Boolean {
//        ensureFocusedDay()
//        var focusChanged = false
//        if (positive) {
//            if (!isLastDayOfWeek(mHighlightedDay) && mHighlightedDay < mDaysInMonth) {
//                mHighlightedDay++
//                focusChanged = true
//            }
//        } else {
//            if (!isFirstDayOfWeek(mHighlightedDay) && mHighlightedDay > 1) {
//                mHighlightedDay--
//                focusChanged = true
//            }
//        }
//        return focusChanged
//    }
//
//    override fun onFocusChanged(
//        gainFocus: Boolean, @FocusDirection direction: Int,
//        @Nullable previouslyFocusedRect: Rect
//    ) {
//        if (gainFocus) {
//            // If we've gained focus through arrow keys, we should find the day closest
//            // to the focus rect. If we've gained focus through forward/back, we should
//            // focus on the selected day if there is one.
//            val offset = findDayOffset()
//            when (direction) {
//                FOCUS_RIGHT -> {
//                    val row = findClosestRow(previouslyFocusedRect)
//                    mHighlightedDay =
//                        if (row == 0) 1 else row * android.widget.SimpleMonthView.Companion.DAYS_IN_WEEK - offset + 1
//                }
//                FOCUS_LEFT -> {
//                    val row = findClosestRow(previouslyFocusedRect) + 1
//                    mHighlightedDay = Math.min(
//                        mDaysInMonth,
//                        row * android.widget.SimpleMonthView.Companion.DAYS_IN_WEEK - offset
//                    )
//                }
//                FOCUS_DOWN -> {
//                    val col = findClosestColumn(previouslyFocusedRect)
//                    val day = col - offset + 1
//                    mHighlightedDay =
//                        if (day < 1) day + android.widget.SimpleMonthView.Companion.DAYS_IN_WEEK else day
//                }
//                FOCUS_UP -> {
//                    val col = findClosestColumn(previouslyFocusedRect)
//                    val maxWeeks: Int =
//                        (offset + mDaysInMonth) / android.widget.SimpleMonthView.Companion.DAYS_IN_WEEK
//                    val day: Int =
//                        col - offset + android.widget.SimpleMonthView.Companion.DAYS_IN_WEEK * maxWeeks + 1
//                    mHighlightedDay =
//                        if (day > mDaysInMonth) day - android.widget.SimpleMonthView.Companion.DAYS_IN_WEEK else day
//                }
//            }
//            ensureFocusedDay()
//            invalidate()
//        }
//        super.onFocusChanged(gainFocus, direction, previouslyFocusedRect)
//    }
//
//    /**
//     * Returns the row (0 indexed) closest to previouslyFocusedRect or center if null.
//     */
//    private fun findClosestRow(@Nullable previouslyFocusedRect: Rect?): Int {
//        return if (previouslyFocusedRect == null) {
//            3
//        } else if (mDayHeight == 0) {
//            0 // There hasn't been a layout, so just choose the first row
//        } else {
//            var centerY = previouslyFocusedRect.centerY()
//            val p = mDayPaint
//            val headerHeight = monthHeight + mDayOfWeekHeight
//            val rowHeight = mDayHeight
//
//            // Text is vertically centered within the row height.
//            val halfLineHeight = (p.ascent() + p.descent()) / 2f
//            val rowCenter = headerHeight + rowHeight / 2
//            centerY -= rowCenter - halfLineHeight.toInt()
//            var row = Math.round(centerY / rowHeight.toFloat())
//            val maxDay = findDayOffset() + mDaysInMonth
//            val maxRows: Int =
//                maxDay / android.widget.SimpleMonthView.Companion.DAYS_IN_WEEK - if (maxDay % android.widget.SimpleMonthView.Companion.DAYS_IN_WEEK == 0) 1 else 0
//            row = MathUtils.constrain(row, 0, maxRows)
//            row
//        }
//    }
//
//    /**
//     * Returns the column (0 indexed) closest to the previouslyFocusedRect or center if null.
//     * The 0 index is related to the first day of the week.
//     */
//    private fun findClosestColumn(@Nullable previouslyFocusedRect: Rect?): Int {
//        return if (previouslyFocusedRect == null) {
//            android.widget.SimpleMonthView.Companion.DAYS_IN_WEEK / 2
//        } else if (cellWidth == 0) {
//            0 // There hasn't been a layout, so we can just choose the first column
//        } else {
//            val centerX: Int = previouslyFocusedRect.centerX() - mPaddingLeft
//            val columnFromLeft: Int = MathUtils.constrain(
//                centerX / cellWidth,
//                0,
//                android.widget.SimpleMonthView.Companion.DAYS_IN_WEEK - 1
//            )
//            if (isLayoutRtl()) android.widget.SimpleMonthView.Companion.DAYS_IN_WEEK - columnFromLeft - 1 else columnFromLeft
//        }
//    }
//
//    override fun getFocusedRect(r: Rect) {
//        if (mHighlightedDay > 0) {
//            getBoundsForDay(mHighlightedDay, r)
//        } else {
//            super.getFocusedRect(r)
//        }
//    }
//
//    protected fun onFocusLost() {
//        if (!mIsTouchHighlighted) {
//            // Unhighlight a day.
//            mPreviouslyHighlightedDay = mHighlightedDay
//            mHighlightedDay = -1
//            invalidate()
//        }
//        super.onFocusLost()
//    }
//
//    /**
//     * Ensure some day is highlighted. If a day isn't highlighted, it chooses the selected day,
//     * if possible, or the first day of the month if not.
//     */
//    private fun ensureFocusedDay() {
//        if (mHighlightedDay != -1) {
//            return
//        }
//        if (mPreviouslyHighlightedDay != -1) {
//            mHighlightedDay = mPreviouslyHighlightedDay
//            return
//        }
//        if (mActivatedDay != -1) {
//            mHighlightedDay = mActivatedDay
//            return
//        }
//        mHighlightedDay = 1
//    }
//
//    private fun isFirstDayOfWeek(day: Int): Boolean {
//        val offset = findDayOffset()
//        return (offset + day - 1) % android.widget.SimpleMonthView.Companion.DAYS_IN_WEEK == 0
//    }
//
//    private fun isLastDayOfWeek(day: Int): Boolean {
//        val offset = findDayOffset()
//        return (offset + day) % android.widget.SimpleMonthView.Companion.DAYS_IN_WEEK == 0
//    }
//
//    override fun onDraw(canvas: Canvas) {
//        val paddingLeft = paddingLeft
//        val paddingTop = paddingTop
//        canvas.translate(paddingLeft.toFloat(), paddingTop.toFloat())
//        drawMonth(canvas)
//        drawDaysOfWeek(canvas)
//        drawDays(canvas)
//        canvas.translate(-paddingLeft.toFloat(), -paddingTop.toFloat())
//    }
//
//    private fun drawMonth(canvas: Canvas) {
//        val x = mPaddedWidth / 2f
//
//        // Vertically centered within the month header height.
//        val lineHeight = mMonthPaint.ascent() + mMonthPaint.descent()
//        val y = (monthHeight - lineHeight) / 2f
//        canvas.drawText(monthYearLabel, x, y, mMonthPaint)
//    }
//
//    private fun drawDaysOfWeek(canvas: Canvas) {
//        val p = mDayOfWeekPaint
//        val headerHeight = monthHeight
//        val rowHeight = mDayOfWeekHeight
//        val colWidth = cellWidth
//
//        // Text is vertically centered within the day of week height.
//        val halfLineHeight = (p.ascent() + p.descent()) / 2f
//        val rowCenter = headerHeight + rowHeight / 2
//        for (col in 0 until android.widget.SimpleMonthView.Companion.DAYS_IN_WEEK) {
//            val colCenter = colWidth * col + colWidth / 2
//            val colCenterRtl: Int
//            colCenterRtl = if (isLayoutRtl()) {
//                mPaddedWidth - colCenter
//            } else {
//                colCenter
//            }
//            val label = mDayOfWeekLabels[col]
//            canvas.drawText(label, colCenterRtl.toFloat(), rowCenter - halfLineHeight, p)
//        }
//    }
//
//    /**
//     * Draws the month days.
//     */
//    private fun drawDays(canvas: Canvas) {
//        val p = mDayPaint
//        val headerHeight = monthHeight + mDayOfWeekHeight
//        val rowHeight = mDayHeight
//        val colWidth = cellWidth
//
//        // Text is vertically centered within the row height.
//        val halfLineHeight = (p.ascent() + p.descent()) / 2f
//        var rowCenter = headerHeight + rowHeight / 2
//        var day = 1
//        var col = findDayOffset()
//        while (day <= mDaysInMonth) {
//            val colCenter = colWidth * col + colWidth / 2
//            val colCenterRtl: Int
//            colCenterRtl = if (isLayoutRtl()) {
//                mPaddedWidth - colCenter
//            } else {
//                colCenter
//            }
//            var stateMask = 0
//            val isDayEnabled = isDayEnabled(day)
//            if (isDayEnabled) {
//                stateMask = stateMask or StateSet.VIEW_STATE_ENABLED
//            }
//            val isDayActivated = mActivatedDay == day
//            val isDayHighlighted = mHighlightedDay == day
//            if (isDayActivated) {
//                stateMask = stateMask or StateSet.VIEW_STATE_ACTIVATED
//
//                // Adjust the circle to be centered on the row.
//                val paint = if (isDayHighlighted) mDayHighlightSelectorPaint else mDaySelectorPaint
//                canvas.drawCircle(
//                    colCenterRtl.toFloat(),
//                    rowCenter.toFloat(),
//                    mDaySelectorRadius.toFloat(),
//                    paint
//                )
//            } else if (isDayHighlighted) {
//                stateMask = stateMask or StateSet.VIEW_STATE_PRESSED
//                if (isDayEnabled) {
//                    // Adjust the circle to be centered on the row.
//                    canvas.drawCircle(
//                        colCenterRtl.toFloat(), rowCenter.toFloat(),
//                        mDaySelectorRadius.toFloat(), mDayHighlightPaint
//                    )
//                }
//            }
//            val isDayToday = mToday == day
//            val dayTextColor: Int
//            dayTextColor = if (isDayToday && !isDayActivated) {
//                mDaySelectorPaint.color
//            } else {
//                val stateSet: IntArray = StateSet.get(stateMask)
//                mDayTextColor!!.getColorForState(stateSet, 0)
//            }
//            p.color = dayTextColor
//            canvas.drawText(
//                mDayFormatter.format(day.toLong()),
//                colCenterRtl.toFloat(),
//                rowCenter - halfLineHeight,
//                p
//            )
//            col++
//            if (col == android.widget.SimpleMonthView.Companion.DAYS_IN_WEEK) {
//                col = 0
//                rowCenter += rowHeight
//            }
//            day++
//        }
//    }
//
//    private fun isDayEnabled(day: Int): Boolean {
//        return day >= mEnabledDayStart && day <= mEnabledDayEnd
//    }
//
//    private fun isValidDayOfMonth(day: Int): Boolean {
//        return day >= 1 && day <= mDaysInMonth
//    }
//
//    /**
//     * Sets the selected day.
//     *
//     * @param dayOfMonth the selected day of the month, or `-1` to clear
//     * the selection
//     */
//    fun setSelectedDay(dayOfMonth: Int) {
//        mActivatedDay = dayOfMonth
//
//        // Invalidate cached accessibility information.
//        mTouchHelper.invalidateRoot()
//        invalidate()
//    }
//
//    /**
//     * Sets the first day of the week.
//     *
//     * @param weekStart which day the week should start on, valid values are
//     * [Calendar.SUNDAY] through [Calendar.SATURDAY]
//     */
//    fun setFirstDayOfWeek(weekStart: Int) {
//        mWeekStart = if (android.widget.SimpleMonthView.Companion.isValidDayOfWeek(weekStart)) {
//            weekStart
//        } else {
//            mCalendar.firstDayOfWeek
//        }
//        updateDayOfWeekLabels()
//
//        // Invalidate cached accessibility information.
//        mTouchHelper.invalidateRoot()
//        invalidate()
//    }
//
//    /**
//     * Sets all the parameters for displaying this week.
//     *
//     *
//     * Parameters have a default value and will only update if a new value is
//     * included, except for focus month, which will always default to no focus
//     * month if no value is passed in. The only required parameter is the week
//     * start.
//     *
//     * @param selectedDay the selected day of the month, or -1 for no selection
//     * @param month the month
//     * @param year the year
//     * @param weekStart which day the week should start on, valid values are
//     * [Calendar.SUNDAY] through [Calendar.SATURDAY]
//     * @param enabledDayStart the first enabled day
//     * @param enabledDayEnd the last enabled day
//     */
//    fun setMonthParams(
//        selectedDay: Int, month: Int, year: Int, weekStart: Int, enabledDayStart: Int,
//        enabledDayEnd: Int
//    ) {
//        mActivatedDay = selectedDay
//        if (android.widget.SimpleMonthView.Companion.isValidMonth(month)) {
//            mMonth = month
//        }
//        mYear = year
//        mCalendar[Calendar.MONTH] = mMonth
//        mCalendar[Calendar.YEAR] = mYear
//        mCalendar[Calendar.DAY_OF_MONTH] = 1
//        mDayOfWeekStart = mCalendar[Calendar.DAY_OF_WEEK]
//        mWeekStart = if (android.widget.SimpleMonthView.Companion.isValidDayOfWeek(weekStart)) {
//            weekStart
//        } else {
//            mCalendar.firstDayOfWeek
//        }
//
//        // Figure out what day today is.
//        val today = Calendar.getInstance()
//        mToday = -1
//        mDaysInMonth = android.widget.SimpleMonthView.Companion.getDaysInMonth(mMonth, mYear)
//        for (i in 0 until mDaysInMonth) {
//            val day = i + 1
//            if (sameDay(day, today)) {
//                mToday = day
//            }
//        }
//        mEnabledDayStart = MathUtils.constrain(enabledDayStart, 1, mDaysInMonth)
//        mEnabledDayEnd = MathUtils.constrain(enabledDayEnd, mEnabledDayStart, mDaysInMonth)
//        updateMonthYearLabel()
//        updateDayOfWeekLabels()
//
//        // Invalidate cached accessibility information.
//        mTouchHelper.invalidateRoot()
//        invalidate()
//    }
//
//    private fun sameDay(day: Int, today: Calendar): Boolean {
//        return mYear == today[Calendar.YEAR] && mMonth == today[Calendar.MONTH] && day == today[Calendar.DAY_OF_MONTH]
//    }
//
//    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
//        val preferredHeight: Int =
//            (mDesiredDayHeight * android.widget.SimpleMonthView.Companion.MAX_WEEKS_IN_MONTH + mDesiredDayOfWeekHeight + mDesiredMonthHeight
//                    + paddingTop + paddingBottom)
//        val preferredWidth: Int =
//            mDesiredCellWidth * android.widget.SimpleMonthView.Companion.DAYS_IN_WEEK + paddingStart + paddingEnd
//        val resolvedWidth = resolveSize(preferredWidth, widthMeasureSpec)
//        val resolvedHeight = resolveSize(preferredHeight, heightMeasureSpec)
//        setMeasuredDimension(resolvedWidth, resolvedHeight)
//    }
//
//    override fun onRtlPropertiesChanged(@ResolvedLayoutDir layoutDirection: Int) {
//        super.onRtlPropertiesChanged(layoutDirection)
//        requestLayout()
//    }
//
//    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
//        if (!changed) {
//            return
//        }
//
//        // Let's initialize a completely reasonable number of variables.
//        val w = right - left
//        val h = bottom - top
//        val paddingLeft = paddingLeft
//        val paddingTop = paddingTop
//        val paddingRight = paddingRight
//        val paddingBottom = paddingBottom
//        val paddedRight = w - paddingRight
//        val paddedBottom = h - paddingBottom
//        val paddedWidth = paddedRight - paddingLeft
//        val paddedHeight = paddedBottom - paddingTop
//        if (paddedWidth == mPaddedWidth || paddedHeight == mPaddedHeight) {
//            return
//        }
//        mPaddedWidth = paddedWidth
//        mPaddedHeight = paddedHeight
//
//        // We may have been laid out smaller than our preferred size. If so,
//        // scale all dimensions to fit.
//        val measuredPaddedHeight = measuredHeight - paddingTop - paddingBottom
//        val scaleH = paddedHeight / measuredPaddedHeight.toFloat()
//        val monthHeight = (mDesiredMonthHeight * scaleH).toInt()
//        val cellWidth: Int = mPaddedWidth / android.widget.SimpleMonthView.Companion.DAYS_IN_WEEK
//        this.monthHeight = monthHeight
//        mDayOfWeekHeight = (mDesiredDayOfWeekHeight * scaleH).toInt()
//        mDayHeight = (mDesiredDayHeight * scaleH).toInt()
//        this.cellWidth = cellWidth
//
//        // Compute the largest day selector radius that's still within the clip
//        // bounds and desired selector radius.
//        val maxSelectorWidth = cellWidth / 2 + Math.min(paddingLeft, paddingRight)
//        val maxSelectorHeight = mDayHeight / 2 + paddingBottom
//        mDaySelectorRadius = Math.min(
//            mDesiredDaySelectorRadius,
//            Math.min(maxSelectorWidth, maxSelectorHeight)
//        )
//
//        // Invalidate cached accessibility information.
//        mTouchHelper.invalidateRoot()
//    }
//
//    private fun findDayOffset(): Int {
//        val offset = mDayOfWeekStart - mWeekStart
//        return if (mDayOfWeekStart < mWeekStart) {
//            offset + android.widget.SimpleMonthView.Companion.DAYS_IN_WEEK
//        } else offset
//    }
//
//    /**
//     * Calculates the day of the month at the specified touch position. Returns
//     * the day of the month or -1 if the position wasn't in a valid day.
//     *
//     * @param x the x position of the touch event
//     * @param y the y position of the touch event
//     * @return the day of the month at (x, y), or -1 if the position wasn't in
//     * a valid day
//     */
//    private fun getDayAtLocation(x: Int, y: Int): Int {
//        val paddedX = x - paddingLeft
//        if (paddedX < 0 || paddedX >= mPaddedWidth) {
//            return -1
//        }
//        val headerHeight = monthHeight + mDayOfWeekHeight
//        val paddedY = y - paddingTop
//        if (paddedY < headerHeight || paddedY >= mPaddedHeight) {
//            return -1
//        }
//
//        // Adjust for RTL after applying padding.
//        val paddedXRtl: Int
//        paddedXRtl = if (isLayoutRtl()) {
//            mPaddedWidth - paddedX
//        } else {
//            paddedX
//        }
//        val row = (paddedY - headerHeight) / mDayHeight
//        val col: Int =
//            paddedXRtl * android.widget.SimpleMonthView.Companion.DAYS_IN_WEEK / mPaddedWidth
//        val index: Int = col + row * android.widget.SimpleMonthView.Companion.DAYS_IN_WEEK
//        val day = index + 1 - findDayOffset()
//        return if (!isValidDayOfMonth(day)) {
//            -1
//        } else day
//    }
//
//    /**
//     * Calculates the bounds of the specified day.
//     *
//     * @param id the day of the month
//     * @param outBounds the rect to populate with bounds
//     */
//    fun getBoundsForDay(id: Int, outBounds: Rect): Boolean {
//        if (!isValidDayOfMonth(id)) {
//            return false
//        }
//        val index = id - 1 + findDayOffset()
//
//        // Compute left edge, taking into account RTL.
//        val col: Int = index % android.widget.SimpleMonthView.Companion.DAYS_IN_WEEK
//        val colWidth = cellWidth
//        val left: Int
//        left = if (isLayoutRtl()) {
//            width - paddingRight - (col + 1) * colWidth
//        } else {
//            paddingLeft + col * colWidth
//        }
//
//        // Compute top edge.
//        val row: Int = index / android.widget.SimpleMonthView.Companion.DAYS_IN_WEEK
//        val rowHeight = mDayHeight
//        val headerHeight = monthHeight + mDayOfWeekHeight
//        val top = paddingTop + headerHeight + row * rowHeight
//        outBounds[left, top, left + colWidth] = top + rowHeight
//        return true
//    }
//
//    /**
//     * Called when the user clicks on a day. Handles callbacks to the
//     * [OnDayClickListener] if one is set.
//     *
//     * @param day the day that was clicked
//     */
//    private fun onDayClicked(day: Int): Boolean {
//        if (!isValidDayOfMonth(day) || !isDayEnabled(day)) {
//            return false
//        }
//        if (mOnDayClickListener != null) {
//            val date = Calendar.getInstance()
//            date[mYear, mMonth] = day
//            mOnDayClickListener!!.onDayClick(this, date)
//        }
//
//        // This is a no-op if accessibility is turned off.
//        mTouchHelper.sendEventForVirtualView(day, AccessibilityEvent.TYPE_VIEW_CLICKED)
//        return true
//    }
//
//    override fun onResolvePointerIcon(event: MotionEvent, pointerIndex: Int): PointerIcon {
//        if (!isEnabled) {
//            return null
//        }
//        // Add 0.5f to event coordinates to match the logic in onTouchEvent.
//        val x = (event.x + 0.5f).toInt()
//        val y = (event.y + 0.5f).toInt()
//        val dayUnderPointer = getDayAtLocation(x, y)
//        return if (dayUnderPointer >= 0) {
//            PointerIcon.getSystemIcon(context, PointerIcon.TYPE_HAND)
//        } else super.onResolvePointerIcon(event, pointerIndex)
//    }
//
//    /**
//     * Provides a virtual view hierarchy for interfacing with an accessibility
//     * service.
//     */
//    private inner class MonthViewTouchHelper(host: View?) :
//        ExploreByTouchHelper(host) {
//        private val mTempRect = Rect()
//        private val mTempCalendar = Calendar.getInstance()
//        protected fun getVirtualViewAt(x: Float, y: Float): Int {
//            val day = getDayAtLocation((x + 0.5f).toInt(), (y + 0.5f).toInt())
//            return if (day != -1) {
//                day
//            } else ExploreByTouchHelper.INVALID_ID
//        }
//
//        protected fun getVisibleVirtualViews(virtualViewIds: IntArray) {
//            for (day in 1..mDaysInMonth) {
//                virtualViewIds.add(day)
//            }
//        }
//
//        protected fun onPopulateEventForVirtualView(virtualViewId: Int, event: AccessibilityEvent) {
//            event.contentDescription = getDayDescription(virtualViewId)
//        }
//
//        protected fun onPopulateNodeForVirtualView(
//            virtualViewId: Int,
//            node: AccessibilityNodeInfo
//        ) {
//            val hasBounds = getBoundsForDay(virtualViewId, mTempRect)
//            if (!hasBounds) {
//                // The day is invalid, kill the node.
//                mTempRect.setEmpty()
//                node.contentDescription = ""
//                node.setBoundsInParent(mTempRect)
//                node.isVisibleToUser = false
//                return
//            }
//            node.text = getDayText(virtualViewId)
//            node.contentDescription = getDayDescription(virtualViewId)
//            node.setBoundsInParent(mTempRect)
//            val isDayEnabled = isDayEnabled(virtualViewId)
//            if (isDayEnabled) {
//                node.addAction(AccessibilityNodeInfo.AccessibilityAction.ACTION_CLICK)
//            }
//            node.isEnabled = isDayEnabled
//            if (virtualViewId == mActivatedDay) {
//                // TODO: This should use activated once that's supported.
//                node.isChecked = true
//            }
//        }
//
//        protected fun onPerformActionForVirtualView(
//            virtualViewId: Int, action: Int,
//            arguments: Bundle?
//        ): Boolean {
//            when (action) {
//                AccessibilityNodeInfo.ACTION_CLICK -> return onDayClicked(virtualViewId)
//            }
//            return false
//        }
//
//        /**
//         * Generates a description for a given virtual view.
//         *
//         * @param id the day to generate a description for
//         * @return a description of the virtual view
//         */
//        private fun getDayDescription(id: Int): CharSequence {
//            if (isValidDayOfMonth(id)) {
//                mTempCalendar[mYear, mMonth] = id
//                return DateFormat.format(Companion.DATE_FORMAT, mTempCalendar.timeInMillis)
//            }
//            return ""
//        }
//
//        /**
//         * Generates displayed text for a given virtual view.
//         *
//         * @param id the day to generate text for
//         * @return the visible text of the virtual view
//         */
//        private fun getDayText(id: Int): CharSequence? {
//            return if (isValidDayOfMonth(id)) {
//                mDayFormatter.format(id.toLong())
//            } else null
//        }
//
//        companion object {
//            private const val DATE_FORMAT = "dd MMMM yyyy"
//        }
//    }
//
//    /**
//     * Handles callbacks when the user clicks on a time object.
//     */
//    interface OnDayClickListener {
//        fun onDayClick(view: android.widget.SimpleMonthView?, day: Calendar?)
//    }
//
//    companion object {
//        private const val DAYS_IN_WEEK = 7
//        private const val MAX_WEEKS_IN_MONTH = 6
//        private const val DEFAULT_SELECTED_DAY = -1
//        private const val DEFAULT_WEEK_START = Calendar.SUNDAY
//        private const val MONTH_YEAR_FORMAT = "MMMMy"
//        private const val SELECTED_HIGHLIGHT_ALPHA = 0xB0
//        private fun isValidDayOfWeek(day: Int): Boolean {
//            return day >= Calendar.SUNDAY && day <= Calendar.SATURDAY
//        }
//
//        private fun isValidMonth(month: Int): Boolean {
//            return month >= Calendar.JANUARY && month <= Calendar.DECEMBER
//        }
//
//        private fun getDaysInMonth(month: Int, year: Int): Int {
//            return when (month) {
//                Calendar.JANUARY, Calendar.MARCH, Calendar.MAY, Calendar.JULY, Calendar.AUGUST, Calendar.OCTOBER, Calendar.DECEMBER -> 31
//                Calendar.APRIL, Calendar.JUNE, Calendar.SEPTEMBER, Calendar.NOVEMBER -> 30
//                Calendar.FEBRUARY -> if (year % 4 == 0) 29 else 28
//                else -> throw IllegalArgumentException("Invalid Month")
//            }
//        }
//    }
//
//    init {
//        val res = context.resources
//        mDesiredMonthHeight = res.getDimensionPixelSize(R.dimen.date_picker_month_height)
//        mDesiredDayOfWeekHeight = res.getDimensionPixelSize(R.dimen.date_picker_day_of_week_height)
//        mDesiredDayHeight = res.getDimensionPixelSize(R.dimen.date_picker_day_height)
//        mDesiredCellWidth = res.getDimensionPixelSize(R.dimen.date_picker_day_width)
//        mDesiredDaySelectorRadius = res.getDimensionPixelSize(
//            R.dimen.date_picker_day_selector_radius
//        )
//
//        // Set up accessibility components.
//        mTouchHelper = MonthViewTouchHelper(this)
//        setAccessibilityDelegate(mTouchHelper)
//        importantForAccessibility = IMPORTANT_FOR_ACCESSIBILITY_YES
//        mLocale = res.configuration.locale
//        mCalendar = Calendar.getInstance(mLocale)
//        mDayFormatter = NumberFormat.getIntegerInstance(mLocale)
//        updateMonthYearLabel()
//        updateDayOfWeekLabels()
//        initPaints(res)
//    }
//}