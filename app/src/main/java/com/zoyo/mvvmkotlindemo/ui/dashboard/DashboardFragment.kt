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

class DashboardFragment : BaseFragment<FragmentDashboardBinding>() {
    private val dashboardViewModel: DashboardViewModel by viewModels()

    override fun getVM(): BaseViewModel = dashboardViewModel

    override fun getVariableId(): Int = BR.viewModel

    override fun getLayoutId(): Int = R.layout.fragment_dashboard

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