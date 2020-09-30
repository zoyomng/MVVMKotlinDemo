package com.zoyo.mvvmkotlindemo

import android.app.Application

/**
 * Copyright (c) dtelec, Inc All Rights Reserved.
 */
class App : Application() {
    companion object {
        lateinit var appContext: Application
    }

    override fun onCreate() {
        super.onCreate()
        appContext = this

    }

}