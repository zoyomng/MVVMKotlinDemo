package com.zoyo.mvvmkotlindemo.ui.page.page2

import android.view.View
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.header_network_state.*

class NetworkStateItemViewHolder(
    override val containerView: View,
    private val retryCallback: () -> Unit
) :
    RecyclerView.ViewHolder(containerView), LayoutContainer {
    fun bindTo(loadState: LoadState) {
        progressBar.isVisible = loadState is LoadState.Loading
        retryButton.isVisible = loadState is LoadState.Error
        retryButton.setOnClickListener { retryCallback() }
        errorMsg.isVisible = !(loadState as? LoadState.Error)?.error?.message.isNullOrBlank()
        errorMsg.text = (loadState as? LoadState.Error)?.error?.message

    }

}
