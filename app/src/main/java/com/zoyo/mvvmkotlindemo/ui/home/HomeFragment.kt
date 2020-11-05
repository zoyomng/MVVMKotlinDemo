package com.zoyo.mvvmkotlindemo.ui.home

import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.zoyo.mvvmkotlindemo.R
import com.zoyo.mvvmkotlindemo.core.base.BaseFragment
import com.zoyo.mvvmkotlindemo.core.base.BaseViewModel
import com.zoyo.mvvmkotlindemo.databinding.FragmentHomeBinding
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class HomeFragment : BaseFragment<FragmentHomeBinding>(R.layout.fragment_home) {
    private val homeViewModel by viewModels<HomeViewModel>()

    override fun getVM(): BaseViewModel {
        return homeViewModel
    }

    override fun initData() {
        homeViewModel.homeAdapter
    }
}