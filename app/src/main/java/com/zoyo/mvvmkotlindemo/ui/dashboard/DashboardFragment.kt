package com.zoyo.mvvmkotlindemo.ui.dashboard

import android.content.Intent
import android.view.View
import androidx.fragment.app.viewModels
import com.zoyo.mvvmkotlindemo.R
import com.zoyo.core.base.BaseFragment
import com.zoyo.core.base.BaseViewModel
import com.zoyo.mvvmkotlindemo.BR
import com.zoyo.mvvmkotlindemo.databinding.FragmentDashboardBinding
import com.zoyo.mvvmkotlindemo.ui.mainchild.MainChildActivity

class DashboardFragment :
    BaseFragment<FragmentDashboardBinding>(R.layout.fragment_dashboard, BR.viewModel) {
    private val dashboardViewModel: DashboardViewModel by viewModels()

    override fun getVM(): BaseViewModel {
        return dashboardViewModel
    }

    override fun initialize() {
        dataBinding.textDashboard.setOnClickListener(View.OnClickListener {
            startActivity(
                Intent(
                    context,
                    MainChildActivity::class.java
                )
            )
        })
    }
}