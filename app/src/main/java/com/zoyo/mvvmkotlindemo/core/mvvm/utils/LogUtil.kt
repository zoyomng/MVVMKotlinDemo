package com.zoyo.mvvmkotlindemo.core.mvvm.utils

import android.util.Log
import com.zoyo.mvvmkotlindemo.App
import com.zoyo.mvvmkotlindemo.BuildConfig

/**
 * Copyright (c) dtelec, Inc All Rights Reserved.
 */
object LogUtil {
    private var isDebug: Boolean = BuildConfig.DEBUG
    private val TAG = App.appContext.packageName

    fun e(msg: String) {
        if (isDebug) {
            Log.e(TAG, msg)
        }
    }


    fun w(msg: String?) {
        if (isDebug) {
            Log.w(TAG, msg)
        }
    }

    fun d(msg: String?) {
        if (isDebug) {
            Log.d(TAG, msg)
        }
    }

    fun i(msg: String?) {
        if (isDebug) {
            Log.i(TAG, msg)
        }
    }
}