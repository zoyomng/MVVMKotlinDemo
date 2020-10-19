package com.zoyo.mvvmkotlindemo.ui.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.zoyo.mvvmkotlindemo.R
import com.zoyo.mvvmkotlindemo.db.Entity.Subject
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_home.*

/**
 * Copyright (c) dtelec, Inc All Rights Reserved.
 */
class HomeAdapter : PagingDataAdapter<Subject, HomeViewHolder>(diffCallback) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeViewHolder {
        val containerView =
            LayoutInflater.from(parent.context).inflate(R.layout.item_home, parent, false)
        return HomeViewHolder(containerView)
    }

    override fun onBindViewHolder(holder: HomeViewHolder, position: Int) {
        holder.bindContent(getItem(position))
    }

    companion object {
        private val diffCallback = object : DiffUtil.ItemCallback<Subject>() {
            //是否为同一个Item
            override fun areItemsTheSame(oldItem: Subject, newItem: Subject): Boolean =
                oldItem.id == newItem.id

            //是否内容变更,kotlin中 "==":比较的是对象所有属性是否相同
            override fun areContentsTheSame(oldItem: Subject, newItem: Subject): Boolean =
                oldItem == newItem
        }
    }
}

class HomeViewHolder(override val containerView: View) : RecyclerView.ViewHolder(containerView),
    LayoutContainer {
    fun bindContent(item: Subject?) {
        item?.run {
            tvNum.text = id.toString()
            tvTitle.text = subjectTitle
            tvContent.text = subjectDesc
        }
    }
}