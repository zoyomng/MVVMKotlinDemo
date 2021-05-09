package com.zoyo.view

import android.graphics.Color
import com.zoyo.core.base.RootActivity
import com.zoyo.view.pie.PieData
import com.zoyo.view.pie.RingChartView

/**
 * Copyright (c) dtelec, Inc All Rights Reserved.
 */
class CustomViewActivity : RootActivity() {
    override fun getLayoutId(): Int = R.layout.activity_canvas

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
        val ringView = findViewById<RingChartView>(R.id.ringView)
        val pieDataList = arrayListOf(
            PieData("Android", 30F, Color.GREEN),
            PieData("Ios", 40F, Color.BLACK),
            PieData("other", 30F, Color.BLUE)
        )

//        pieView.setData(pieDataList)
        ringView.setData(pieDataList)

    }
}