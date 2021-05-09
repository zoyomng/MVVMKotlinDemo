package com.zoyo.core

import android.app.Application
import android.content.Context
import com.zoyo.core.base.ActivityManager

/**
 * zoyomng 2021/3/7
 */
open class BaseApplication : Application() {
    companion object {
        private lateinit var appContext: BaseApplication

        fun getContext(): Context = appContext
    }

    override fun onCreate() {
        super.onCreate()
        appContext = this
    }


    private fun exitApp() {
        ActivityManager.instance.finishAllActivity()
        android.os.Process.killProcess(android.os.Process.myPid())
        System.exit(0)
    }

}