package com.zoyo.mvvmkotlindemo.ui.main

import android.graphics.Color
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.viewModelScope
import com.zoyo.mvvmkotlindemo.core.base.BaseViewModel
import com.zoyo.mvvmkotlindemo.core.utils.LogUtil
import com.zoyo.mvvmkotlindemo.core.widget.toast.Toaster
import com.zoyo.mvvmkotlindemo.model.MainItemData
import kotlinx.coroutines.launch
import kotlin.random.Random

/**
 * Copyright (c) dtelec, Inc All Rights Reserved.
 */
class MainViewModel : BaseViewModel() {

    init {
        Random.nextInt(256)
        val argb = Color.argb(255, Random.nextInt(256), Random.nextInt(256), Random.nextInt(256))
        val mainItemData = MainItemData("协程+Retrofit+Okhttp", argb)
        val data = listOf(mainItemData, mainItemData.copy(name = "Fragment"))

        viewModelScope.launch {
            // Coroutine that will be canceled when the ViewModel is cleared.-当ViewModel被清除时,协程会自动取消


        }
    }


    override fun onAny(owner: LifecycleOwner, event: Lifecycle.Event) {
        super.onAny(owner, event)
        LogUtil.e("onAny")
    }

    override fun onCreate() {
        super.onCreate()
        LogUtil.e("onCreate")
    }

    override fun onStart() {
        super.onStart()
        LogUtil.e("onStart")
    }

    override fun onResume() {
        super.onResume()
        LogUtil.e("onResume")
    }

    override fun onPause() {
        super.onPause()
        LogUtil.e("onPause")
    }

    override fun onStop() {
        super.onStop()
        LogUtil.e("onStop")
    }

    override fun onDestroy() {
        super.onDestroy()
        LogUtil.e("onDestroy")
    }

//    2020-09-30 16:50:01.754 25621-25621/com.zoyo.mvvmkotlindemo E/com.zoyo.mvvmkotlindemo: onCreate
//    2020-09-30 16:50:01.756 25621-25621/com.zoyo.mvvmkotlindemo E/com.zoyo.mvvmkotlindemo: onAny
//    2020-09-30 16:50:01.773 25621-25621/com.zoyo.mvvmkotlindemo E/com.zoyo.mvvmkotlindemo: onStart
//    2020-09-30 16:50:01.775 25621-25621/com.zoyo.mvvmkotlindemo E/com.zoyo.mvvmkotlindemo: onAny
//    2020-09-30 16:50:01.781 25621-25621/com.zoyo.mvvmkotlindemo E/com.zoyo.mvvmkotlindemo: onResume
//    2020-09-30 16:50:01.782 25621-25621/com.zoyo.mvvmkotlindemo E/com.zoyo.mvvmkotlindemo: onAny

//    2020-09-30 16:50:59.204 25621-25621/com.zoyo.mvvmkotlindemo E/com.zoyo.mvvmkotlindemo: onPause
//    2020-09-30 16:50:59.206 25621-25621/com.zoyo.mvvmkotlindemo E/com.zoyo.mvvmkotlindemo: onAny

//    2020-09-30 16:50:59.675 25621-25621/com.zoyo.mvvmkotlindemo E/com.zoyo.mvvmkotlindemo: onStop
//    2020-09-30 16:50:59.683 25621-25621/com.zoyo.mvvmkotlindemo E/com.zoyo.mvvmkotlindemo: onAny
}