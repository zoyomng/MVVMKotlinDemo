package com.zoyo.mvvmkotlindemo.ui.home

import android.content.Intent
import android.view.View
import androidx.fragment.app.viewModels
import com.zoyo.mvvmkotlindemo.R
import com.zoyo.mvvmkotlindemo.core.mvvm.base.BaseFragment
import com.zoyo.mvvmkotlindemo.core.mvvm.base.BaseViewModel
import com.zoyo.mvvmkotlindemo.databinding.FragmentHomeBinding
import com.zoyo.mvvmkotlindemo.ui.mainchild.MainChildActivity
import kotlinx.android.synthetic.main.fragment_home.*

class HomeFragment : BaseFragment<FragmentHomeBinding>(R.layout.fragment_home) {
    private val homeViewModel: HomeViewModel by viewModels()
    override fun getVM(): BaseViewModel {
        return homeViewModel
    }

    override fun initData() {
        text_home.setOnClickListener(View.OnClickListener {
            startActivity(Intent(context, MainChildActivity::class.java))

        })
    }

}