package com.zoyo.mvvmkotlindemo.viewmodel

import android.graphics.Color
import androidx.core.graphics.ColorUtils
import androidx.lifecycle.viewModelScope
import com.zoyo.mvvmkotlindemo.core.mvvm.base.BaseViewModel
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

}