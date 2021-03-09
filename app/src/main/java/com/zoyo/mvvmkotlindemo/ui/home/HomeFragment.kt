package com.zoyo.mvvmkotlindemo.ui.home

import androidx.fragment.app.viewModels
import com.zoyo.mvvmkotlindemo.R
import com.zoyo.mvvmkotlindemo.constant.Constant
import com.zoyo.core.base.BaseFragment
import com.zoyo.core.base.BaseViewModel
import com.zoyo.mvvmkotlindemo.BR
import com.zoyo.mvvmkotlindemo.databinding.FragmentHomeBinding
import com.zoyo.mvvmkotlindemo.ui.main.MainActivity

class HomeFragment : BaseFragment<FragmentHomeBinding>(R.layout.fragment_home, BR.viewModel) {
    private val homeViewModel by viewModels<HomeViewModel>()

    override fun getVM(): BaseViewModel {
        return homeViewModel
    }

    override fun initialize() {
        val homeAdapter =
            HomeAdapter(R.layout.item_home, Constant.SUBJECT_DATA) { subject ->
                subject.destinationId.let {
                    (activity as MainActivity).navController.navigate(it)
                }
            }
        dataBinding.recyclerView.adapter = homeAdapter

    }
}