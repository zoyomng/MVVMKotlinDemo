package com.zoyo.mvvmkotlindemo.core.extensions

import android.view.View

/**
 * zoyomng 2021/1/26
 */

var lastTime: Long = 0
inline fun <reified T : View> T.antiShakeClick(crossinline block: (T) -> Unit) = setOnClickListener {
    if (System.currentTimeMillis() - lastTime > 1000) {
        //距上次超过了1s,执行
        block(it as T)
        lastTime = System.currentTimeMillis()
    }
}

inline fun antiShakeClick(block: () -> Unit) {
    if (System.currentTimeMillis() - lastTime > 1000) {
        //距上次超过了1s,执行
        block()
        lastTime = System.currentTimeMillis()
    }
}