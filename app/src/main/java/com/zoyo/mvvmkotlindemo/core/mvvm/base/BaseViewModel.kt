package com.zoyo.mvvmkotlindemo.core.mvvm.base

import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel
import com.zoyo.mvvmkotlindemo.App

/**
 * Copyright (c) dtelec, Inc All Rights Reserved.
 */
open class BaseViewModel : ViewModel(), IBaseViewModel {
    override fun onAny(owner: LifecycleOwner, event: Lifecycle.Event) {
    }

    override fun onCreate() {
    }

    override fun onStart() {
    }

    override fun onResume() {
    }

    override fun onPause() {
    }

    override fun onStop() {
    }

    override fun onDestroy() {
    }


}