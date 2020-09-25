package com.zoyo.mvvmkotlindemo.core.mvvm.base

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding

/**
 * Copyright (c) dtelec, Inc All Rights Reserved.
 */
abstract class BaseActivity<T : ViewDataBinding, VM : BaseViewModel>(
    val layoutResID: Int,
    val variableId: Int
) :
    AppCompatActivity(),
    IBaseView {
    lateinit var dataBinding: T
    private val baseViewModel: BaseViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dataBinding = DataBindingUtil.setContentView<T>(this, layoutResID)
        //设置LifecycleOwner,LifecycleOwner可以观察LiveData数据的变化以更新UI
        dataBinding.lifecycleOwner = this

        //将ViewModel跟相应的页面variableId(xml布局文件->layout->data->variable:name)进行绑定
        dataBinding.setVariable(variableId, baseViewModel)
        //让viewModel拥有View的生命周期
        lifecycle.addObserver(baseViewModel)

        //初始化数据
        initData()
    }


    override fun onDestroy() {
        super.onDestroy()
        if (this::dataBinding.isInitialized) {
            dataBinding.unbind()
        }
        //解除ViewModel对生命周期的感应
        lifecycle.removeObserver(baseViewModel)
    }
}