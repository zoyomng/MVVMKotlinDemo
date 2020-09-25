package com.zoyo.mvvmkotlindemo.ui

import com.zoyo.mvvmkotlindemo.BR
import com.zoyo.mvvmkotlindemo.R
import com.zoyo.mvvmkotlindemo.core.mvvm.base.BaseActivity
import com.zoyo.mvvmkotlindemo.databinding.MainActivityBinding
import com.zoyo.mvvmkotlindemo.viewmodel.MainViewModel

class MainActivity :
    BaseActivity<MainActivityBinding, MainViewModel>(R.layout.main_activity, BR.viewModel) {

    override fun initData() {
    }

}