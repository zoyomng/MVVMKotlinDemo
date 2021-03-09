package com.zoyo.view.spider

import android.graphics.Point

/**
 * zoyomng 2021/2/6
 */
class SpiderPointData(
    x: Int,
    y: Int,
    val color: Int,
    val r: Int,
    var aX: Int,
    var aY: Int,
    val vX: Float,
    val vY: Float
) : Point(x, y)