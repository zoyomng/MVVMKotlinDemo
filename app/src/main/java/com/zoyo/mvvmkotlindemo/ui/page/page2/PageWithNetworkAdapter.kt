package com.zoyo.mvvmkotlindemo.ui.page.page2

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.zoyo.mvvmkotlindemo.R
import com.zoyo.mvvmkotlindemo.model.Repo

/**
 * Copyright (c) dtelec, Inc All Rights Reserved.
 * 分页库可帮助您一次加载和显示一小块数据。按需载入部分数据会减少网络带宽和系统资源的使用量。
 */
class PageWithNetworkAdapter : PagingDataAdapter<Repo, PageWithNetworkViewHolder>(diffCallback) {

    companion object {
        private val diffCallback = object : DiffUtil.ItemCallback<Repo>() {
            //是否为同一个Item
            override fun areItemsTheSame(oldItem: Repo, newItem: Repo): Boolean =
                oldItem.id == newItem.id

            //是否内容变更,kotlin中 "==":比较的是对象所有属性是否相同
            override fun areContentsTheSame(oldItem: Repo, newItem: Repo): Boolean =
                oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PageWithNetworkViewHolder {
        val containerView =
            LayoutInflater.from(parent.context).inflate(R.layout.item_page_network, parent, false)
        return PageWithNetworkViewHolder(containerView)
    }

    override fun onBindViewHolder(holderWithNetwork: PageWithNetworkViewHolder, position: Int) {
        holderWithNetwork.bindContent(getItem(position))
    }

}

class PageWithNetworkViewHolder(val containerView: View) :
    RecyclerView.ViewHolder(containerView) {
    var Repo: Repo? = null

    fun bindContent(Repo: Repo?) {
        this.Repo = Repo
        Repo?.let {
            containerView.findViewById<TextView>(R.id.tvTitle)
                .apply { text = it.name }
            containerView.findViewById<TextView>(R.id.tvContent)
                .apply { text = it.description }

            containerView.findViewById<TextView>(R.id.tvLanguage)
                .apply { text = it.language }
            containerView.findViewById<TextView>(R.id.tvStar)
                .apply { text = it.stars.toString() }
            containerView.findViewById<TextView>(R.id.tvFork)
                .apply { text = it.forks.toString() }
        }
    }
}