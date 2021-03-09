package com.zoyo.core.binding.viewadapter.recyclerview

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView

/**
 * Copyright (c) dtelec, Inc All Rights Reserved.
 */
class ViewAdapter {
    @BindingAdapter(value = ["adapter"])
    fun setAdapter(
        recyclerView: RecyclerView,
        adapter: RecyclerView.Adapter<RecyclerView.ViewHolder>
    ) {
        recyclerView.adapter = adapter
    }
}