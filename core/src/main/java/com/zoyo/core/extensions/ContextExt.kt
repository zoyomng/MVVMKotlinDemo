package com.zoyo.core.utils

import android.content.Context
import android.view.Gravity
import android.widget.Toast

/**
 * Copyright (c) dtelec, Inc All Rights Reserved.
 */
inline fun <T, R> T.runWithCatch(block: (T) -> R) {
    try {
        block(this)
    } catch (e: Exception) {
        e.printStackTrace()
    }
}

fun Context.toast(text: CharSequence, duration: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(this, text, duration).show()
}

fun Context.centerToast(text: CharSequence, duration: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(this, text, duration)
        .apply {
            setGravity(Gravity.CENTER, 0, 0)
        }
        .show()
}








