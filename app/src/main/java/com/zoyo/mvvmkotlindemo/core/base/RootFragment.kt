package com.zoyo.mvvmkotlindemo.core.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment

/**
 * 2021/1/22       97440
 */
abstract class RootFragment(@LayoutRes val layoutResId: Int) : Fragment(),IBaseView {

    lateinit var containerView: View
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        containerView = inflater.inflate(layoutResId, container, false)
        initData()
        return containerView
    }
}