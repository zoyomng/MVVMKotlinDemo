package com.zoyo.mvvmkotlindemo.ui.home

import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.zoyo.mvvmkotlindemo.R
import com.zoyo.mvvmkotlindemo.core.mvvm.base.BaseFragment
import com.zoyo.mvvmkotlindemo.core.mvvm.base.BaseViewModel
import com.zoyo.mvvmkotlindemo.databinding.FragmentHomeBinding
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.coroutines.launch

class HomeFragment : BaseFragment<FragmentHomeBinding>(R.layout.fragment_home) {
    private val homeViewModel by viewModels<HomeViewModel>()

    override fun getVM(): BaseViewModel {
        return homeViewModel
    }

    override fun initData() {
//        text_home.setOnClickListener(View.OnClickListener {
//            startActivity(Intent(context, MainChildActivity::class.java))
//
//        })
        val adapter = HomeAdapter()
        recyclerView.adapter = adapter
        lifecycleScope.launch {
//            homeViewModel.
        }
    }

}