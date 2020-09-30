package com.zoyo.mvvmkotlindemo.ui.mainchild

import androidx.activity.viewModels
import com.zoyo.mvvmkotlindemo.R
import com.zoyo.mvvmkotlindemo.core.mvvm.base.BaseActivity
import com.zoyo.mvvmkotlindemo.core.mvvm.base.BaseViewModel
import com.zoyo.mvvmkotlindemo.databinding.ActivityMainChildBinding
import com.zoyo.mvvmkotlindemo.ui.main.MainViewModel
import kotlinx.android.synthetic.main.main_activity.*

/**
 * Copyright (c) dtelec, Inc All Rights Reserved.
 */
class MainChildActivity : BaseActivity<ActivityMainChildBinding>(R.layout.activity_main_child) {
    private val mainViewModel: MainViewModel by viewModels()

    override fun getVM(): BaseViewModel {
        return mainViewModel
    }

    override fun initData() {
        //操作栏上添加导航
        setSupportActionBar(toolBar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }
}