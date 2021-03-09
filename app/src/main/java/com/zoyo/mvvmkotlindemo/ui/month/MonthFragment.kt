package com.zoyo.mvvmkotlindemo.ui.month

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.fragment.app.viewModels
import com.zoyo.mvvmkotlindemo.R
import com.zoyo.core.base.BaseFragment
import com.zoyo.core.base.BaseViewModel
import com.zoyo.mvvmkotlindemo.BR
import com.zoyo.mvvmkotlindemo.databinding.FragmentMonthBinding
import java.util.*

/**
 * Copyright (c) dtelec, Inc All Rights Reserved.
 */
class MonthFragment : BaseFragment<FragmentMonthBinding>(R.layout.fragment_month,BR.viewModel) {

    private val monthViewModel by viewModels<MonthViewModel>()

    override fun getVM(): BaseViewModel {
        return monthViewModel
    }

    @RequiresApi(Build.VERSION_CODES.N)
    override fun initialize() {
        val calendar = Calendar.getInstance()

        dataBinding.monthView.setMonthParams(calendar[Calendar.MONTH], calendar[Calendar.YEAR])
    }


}