package com.zoyo.mvvmkotlindemo.ui.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.zoyo.mvvmkotlindemo.databinding.FragmentHomeBinding
import com.zoyo.mvvmkotlindemo.databinding.ItemHomeBinding
import com.zoyo.mvvmkotlindemo.db.entity.Subject

/**
 * Copyright (c) dtelec, Inc All Rights Reserved.
 */
class HomeAdapter(@LayoutRes val layoutId: Int, val subjects: List<Subject>) :
    RecyclerView.Adapter<HomeViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeViewHolder {
        val binding = DataBindingUtil.inflate<ItemHomeBinding>(
            LayoutInflater.from(parent.context),
            layoutId,
            parent,
            false
        )

        return HomeViewHolder(binding)
    }

    override fun onBindViewHolder(holder: HomeViewHolder, position: Int) {
        holder.binding.model = subjects[position]
        holder.binding.executePendingBindings()
    }

    override fun getItemCount(): Int = if (subjects.isEmpty()) 0 else subjects.size

}

class HomeViewHolder(val binding: ItemHomeBinding) : RecyclerView.ViewHolder(binding.root)
