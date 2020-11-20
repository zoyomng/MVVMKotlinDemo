package com.zoyo.mvvmkotlindemo.ui.page.page2

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import com.zoyo.mvvmkotlindemo.R

/**
 * Copyright (c) dtelec, Inc All Rights Reserved.
 */
class PageWithNeteorkAdapterHeader(private val adapter: PageWithNetworkAdapter) :
    LoadStateAdapter<NetworkStateItemViewHolder>() {
    override fun onBindViewHolder(holder: NetworkStateItemViewHolder, loadState: LoadState) {
        holder.bindTo(loadState)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        loadState: LoadState
    ): NetworkStateItemViewHolder {

        return NetworkStateItemViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.header_network_state, parent, false)) {adapter.retry()}
    }
}