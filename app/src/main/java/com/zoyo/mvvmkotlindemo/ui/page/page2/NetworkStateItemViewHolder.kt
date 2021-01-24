package com.zoyo.mvvmkotlindemo.ui.page.page2

import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.recyclerview.widget.RecyclerView
import com.zoyo.mvvmkotlindemo.R

class NetworkStateItemViewHolder(
    val containerView: View,
    private val retryCallback: () -> Unit
) :
    RecyclerView.ViewHolder(containerView) {
    fun bindTo(loadState: LoadState) {
        containerView.findViewById<ProgressBar>(R.id.progressBar)
            .apply {
                isVisible = loadState is LoadState.Loading
            }
        containerView.findViewById<Button>(R.id.retryButton)
            .apply {
                isVisible = loadState is LoadState.Error
                setOnClickListener { retryCallback() }
            }
        containerView.findViewById<TextView>(R.id.errorMsg)
            .apply {
                isVisible = !(loadState as? LoadState.Error)?.error?.message.isNullOrBlank()
                text = (loadState as? LoadState.Error)?.error?.message
            }
    }

}
