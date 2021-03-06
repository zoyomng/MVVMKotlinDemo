package com.zoyo.mvvmkotlindemo.ui.search

import androidx.fragment.app.viewModels
import com.zoyo.mvvmkotlindemo.R
import com.zoyo.core.base.BaseFragment
import com.zoyo.core.base.BaseViewModel
import com.zoyo.mvvmkotlindemo.databinding.MainActivityBinding
import com.zoyo.mvvmkotlindemo.ui.main.MainViewModel
import com.zoyo.mvvmkotlindemo.BR

/**
 * Copyright (c) dtelec, Inc All Rights Reserved.
 */
class SearchFragment : BaseFragment<MainActivityBinding>() {
    private val mainViewModel: MainViewModel by viewModels()

    override fun getVM(): BaseViewModel = mainViewModel
    override fun getVariableId(): Int = BR.viewModel
    override fun getLayoutId(): Int = R.layout.fragment_search

    override fun initialize() {
    }

}