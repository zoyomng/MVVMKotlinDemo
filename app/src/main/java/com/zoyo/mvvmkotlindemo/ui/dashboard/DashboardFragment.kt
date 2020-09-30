package com.zoyo.mvvmkotlindemo.ui.dashboard

import android.content.Intent
import android.view.View
import androidx.fragment.app.viewModels
import com.zoyo.mvvmkotlindemo.R
import com.zoyo.mvvmkotlindemo.core.mvvm.base.BaseFragment
import com.zoyo.mvvmkotlindemo.core.mvvm.base.BaseViewModel
import com.zoyo.mvvmkotlindemo.databinding.FragmentDashboardBinding
import com.zoyo.mvvmkotlindemo.ui.mainchild.MainChildActivity
import kotlinx.android.synthetic.main.fragment_dashboard.*

class DashboardFragment : BaseFragment<FragmentDashboardBinding>(R.layout.fragment_dashboard) {
    private val dashboardViewModel: DashboardViewModel by viewModels()

    override fun getVM(): BaseViewModel {
        return dashboardViewModel
    }

    override fun initData() {
        text_dashboard.setOnClickListener(View.OnClickListener {
            startActivity(
                Intent(
                    context,
                    MainChildActivity::class.java
                )
            )
        })
    }
}