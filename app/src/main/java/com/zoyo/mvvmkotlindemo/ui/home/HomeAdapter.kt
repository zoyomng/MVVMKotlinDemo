package com.zoyo.mvvmkotlindemo.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.zoyo.mvvmkotlindemo.databinding.ItemHomeBinding
import com.zoyo.mvvmkotlindemo.db.entity.Subject

/**
 * Copyright (c) dtelec, Inc All Rights Reserved.
 */
class HomeAdapter(
    @LayoutRes val layoutId: Int,
    private val subjects: List<Subject>?,
    private val itemClick: (Subject) -> Unit
) : RecyclerView.Adapter<HomeViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeViewHolder {
        val binding = DataBindingUtil.inflate<ItemHomeBinding>(
            LayoutInflater.from(parent.context),
            layoutId,
            parent,
            false
        )

        return HomeViewHolder(binding, itemClick)
    }

    override fun onBindViewHolder(holder: HomeViewHolder, position: Int) {
        subjects?.let { holder.bindContent(subjects[position]) }
    }

    override fun getItemCount(): Int = if (subjects.isNullOrEmpty()) 0 else subjects.size

}

class HomeViewHolder(private val binding: ItemHomeBinding, val itemClick: (Subject) -> Unit) :
    RecyclerView.ViewHolder(binding.root) {

    fun bindContent(subject: Subject) {
        //databinding
        binding.model = subject
        //点击事件
        itemView.setOnClickListener { itemClick(subject) }
        binding.executePendingBindings()
    }
}

