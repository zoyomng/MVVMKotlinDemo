package com.zoyo.mvvmkotlindemo.ui.view

import android.widget.Button
import androidx.lifecycle.lifecycleScope
import com.zoyo.mvvmkotlindemo.R
import com.zoyo.mvvmkotlindemo.core.base.RootFragment
import com.zoyo.mvvmkotlindemo.core.extensions.antiShakeClick
import com.zoyo.mvvmkotlindemo.core.utils.L
import kotlinx.coroutines.*
import java.util.logging.Handler

/**
 * Copyright (c) dtelec, Inc All Rights Reserved.
 */
class CanvasFragment : RootFragment(R.layout.fragment_canvas) {

    override fun initialize() {
        val btStart = containerView.findViewById<Button>(R.id.btStart)
        val loadingProgress =
            containerView.findViewById<SuperLoadingProgressView>(R.id.loadingProgress)
        btStart.antiShakeClick {
            lifecycleScope.launchWhenResumed {
                repeat(100) {
                    delay(20)
                    loadingProgress.setProgressValue(it)
                }
                loadingProgress.finishLoading(false)
            }
        }
    }
}