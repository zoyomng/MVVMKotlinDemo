package com.zoyo.mvvmkotlindemo.core.utils

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