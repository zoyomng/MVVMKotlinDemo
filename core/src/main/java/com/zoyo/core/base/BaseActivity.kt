package com.zoyo.core.base

import android.os.Bundle
import androidx.activity.viewModels
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders

/**
 * Copyright (c) dtelec, Inc All Rights Reserved.
 */
abstract class BaseActivity<T : ViewDataBinding> : AppCompatActivity(),
    IBaseView {
    lateinit var dataBinding: T
    lateinit var viewModel: BaseViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        ActivityManager.instance.addActivity(this)

        dataBinding = DataBindingUtil.setContentView<T>(this, getLayoutId())
        //设置LifecycleOwner,LifecycleOwner可以观察LiveData数据的变化以更新UI
        dataBinding.lifecycleOwner = this

        viewModel = getVM()

        //将ViewModel跟相应的页面variableId(xml布局文件->layout->data->variable:name)进行绑定
        dataBinding.setVariable(getVariableId(), viewModel)

        //让viewModel拥有View的生命周期
        lifecycle.addObserver(viewModel)

        //初始化数据
        initialize()
    }

    /**
     * 将ViewModel要绑定的变量值variableId(xml布局文件->layout->data->variable:name)
     */
    abstract fun getVariableId(): Int
    abstract fun getVM(): BaseViewModel


    override fun onDestroy() {
        super.onDestroy()
        if (this::dataBinding.isInitialized) {
            dataBinding.unbind()
        }
        //解除ViewModel对生命周期的感应
        if (this::viewModel.isInitialized) {
            lifecycle.removeObserver(viewModel)
        }

        ActivityManager.instance.removeActivity(this)

    }
}

