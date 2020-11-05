package com.zoyo.mvvmkotlindemo.core.binding.viewadapter.recyclerview

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
        adapter.let {
            recyclerView.adapter = adapter
        }
    }
}