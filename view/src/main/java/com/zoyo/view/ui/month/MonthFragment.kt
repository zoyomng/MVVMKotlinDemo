package com.zoyo.view.ui.month

import android.os.Build
import androidx.annotation.RequiresApi
import com.zoyo.core.base.RootFragment
import com.zoyo.view.R
import java.util.*

/**
 * Copyright (c) dtelec, Inc All Rights Reserved.
 */
class MonthFragment : RootFragment() {

    override fun getLayoutId(): Int = R.layout.fragment_month

    @RequiresApi(Build.VERSION_CODES.N)
    override fun initialize() {
        val calendar = Calendar.getInstance()
        val monthView = containerView.findViewById<MonthView>(R.id.monthView)

        monthView.setMonthParams(calendar[Calendar.MONTH], calendar[Calendar.YEAR])
    }


}