package com.zoyo.mvvmkotlindemo.ui.mainchild

import androidx.activity.viewModels
import com.zoyo.mvvmkotlindemo.R
import com.zoyo.core.base.BaseActivity
import com.zoyo.core.base.BaseViewModel
import com.zoyo.mvvmkotlindemo.databinding.ActivityMainChildBinding
import com.zoyo.mvvmkotlindemo.ui.main.MainViewModel
import com.zoyo.mvvmkotlindemo.BR

/**
 * Copyright (c) dtelec, Inc All Rights Reserved.
 */
class MainChildActivity :
    BaseActivity<ActivityMainChildBinding>(R.layout.activity_main_child, BR.viewModel) {
    private val mainViewModel: MainViewModel by viewModels()

    override fun getVM(): BaseViewModel {
        return mainViewModel
    }

    override fun initialize() {
        //操作栏上添加导航
        setSupportActionBar(dataBinding.toolBar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }
}