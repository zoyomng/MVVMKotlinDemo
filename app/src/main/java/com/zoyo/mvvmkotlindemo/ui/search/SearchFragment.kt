package com.zoyo.mvvmkotlindemo.ui.search

import androidx.fragment.app.viewModels
import com.zoyo.mvvmkotlindemo.R
import com.zoyo.mvvmkotlindemo.core.mvvm.base.BaseFragment
import com.zoyo.mvvmkotlindemo.core.mvvm.base.BaseViewModel
import com.zoyo.mvvmkotlindemo.databinding.MainActivityBinding
import com.zoyo.mvvmkotlindemo.ui.main.MainViewModel

/**
 * Copyright (c) dtelec, Inc All Rights Reserved.
 */
class SearchFragment : BaseFragment<MainActivityBinding>(R.layout.fragment_search) {
    private val mainViewModel: MainViewModel by viewModels()

    override fun getVM(): BaseViewModel {
        return mainViewModel
    }

    override fun initData() {
    }
}