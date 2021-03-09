package com.zoyo.core

import android.app.Application
import android.content.Context

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

}