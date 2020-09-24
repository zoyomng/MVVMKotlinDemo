package com.zoyo.mvvmkotlindemo.core.mvvm.base

import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel
import com.zoyo.mvvmkotlindemo.App

/**
 * Copyright (c) dtelec, Inc All Rights Reserved.
 */
open  class BaseViewModel : AndroidViewModel(App.appContext), IBaseViewModel {
    override fun onAny(owner: LifecycleOwner, event: Lifecycle.Event) {
        TODO("Not yet implemented")
    }

    override fun onCreate() {
        TODO("Not yet implemented")
    }

    override fun onStart() {
        TODO("Not yet implemented")
    }

    override fun onResume() {
        TODO("Not yet implemented")
    }

    override fun onPause() {
        TODO("Not yet implemented")
    }

    override fun onStop() {
        TODO("Not yet implemented")
    }

    override fun onDestroy() {
        TODO("Not yet implemented")
    }
}