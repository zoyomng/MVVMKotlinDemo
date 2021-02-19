package com.zoyo.mvvmkotlindemo.ui.view

import android.graphics.Color
import com.zoyo.mvvmkotlindemo.R
import com.zoyo.mvvmkotlindemo.core.base.RootFragment
import com.zoyo.mvvmkotlindemo.ui.view.pie.PieData
import com.zoyo.mvvmkotlindemo.ui.view.pie.PieChartView
import com.zoyo.mvvmkotlindemo.ui.view.pie.RingChartView

/**
 * Copyright (c) dtelec, Inc All Rights Reserved.
 */
class CanvasFragment : RootFragment(R.layout.fragment_canvas) {

    override fun initialize() {
//        val btStart = containerView.findViewById<Button>(R.id.btStart)
//        val loadingProgress =
//            containerView.findViewById<SuperLoadingProgressView>(R.id.loadingProgress)
//        btStart.antiShakeClick {
//            lifecycleScope.launchWhenResumed {
//                repeat(100) {
//                    delay(20)
//                    loadingProgress.setProgressValue(it)
//                }
//                loadingProgress.finishLoading(false)
//            }
//        }

//        val spiderView = containerView.findViewById<SpiderView>(R.id.spiderView)


//        val pieView = containerView.findViewById<PieChartView>(R.id.pieView)
        val ringView = containerView.findViewById<RingChartView>(R.id.ringView)
        val pieDataList = arrayListOf(
            PieData("Android", 30F, Color.GREEN),
            PieData("Ios", 40F, Color.BLACK),
            PieData("other", 30F, Color.BLUE)
        )

//        pieView.setData(pieDataList)
        ringView.setData(pieDataList)

    }
}