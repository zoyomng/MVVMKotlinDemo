package com.zoyo.mvvmkotlindemo.ui.page

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.zoyo.mvvmkotlindemo.R
import com.zoyo.mvvmkotlindemo.db.entity.Cheese
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_page.*

/**
 * Copyright (c) dtelec, Inc All Rights Reserved.
 */
class PageAdapter : PagingDataAdapter<Cheese, PageViewHolder>(diffCallback) {

    companion object {
        private val diffCallback = object : DiffUtil.ItemCallback<Cheese>() {
            //是否为同一个Item
            override fun areItemsTheSame(oldItem: Cheese, newItem: Cheese): Boolean =
                oldItem.id == newItem.id

            //是否内容变更,kotlin中 "==":比较的是对象所有属性是否相同
            override fun areContentsTheSame(oldItem: Cheese, newItem: Cheese): Boolean =
                oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PageViewHolder {
        val containerView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_page, parent, false)
        return PageViewHolder(containerView)
    }

    override fun onBindViewHolder(holder: PageViewHolder, position: Int) {
        holder.bindContent(getItem(position))
    }
}

class PageViewHolder(override val containerView: View) : RecyclerView.ViewHolder(containerView),
    LayoutContainer {
    var cheese: Cheese? = null

    fun bindContent(cheese: Cheese?) {
        this.cheese = cheese
        cheese?.let {
            tvName.text = it.name
        }
    }
}