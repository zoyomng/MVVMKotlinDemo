package com.zoyo.core.base

import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity

/**
 * zoyomng 2021/1/24
 */
abstract class RootActivity : AppCompatActivity(), IBaseView {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(getLayoutId())
        ActivityManager.instance.addActivity(this)
        initialize()
    }

    override fun onDestroy() {
        super.onDestroy()
        ActivityManager.instance.removeActivity(this)
    }
}