package com.zoyo.view.pie

import android.graphics.Region
import androidx.annotation.ColorInt

/**
 * zoyomng 2021/2/16
 */
data class PieData(
    val title: String,
    val value: Float,
    @ColorInt val color: Int,
    var region: Region? = null,
    var isTouched: Boolean = false
)
