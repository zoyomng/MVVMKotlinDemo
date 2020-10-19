package com.zoyo.mvvmkotlindemo.core.mvvm.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import com.zoyo.mvvmkotlindemo.BR
import java.util.*

/**
 * Copyright (c) dtelec, Inc All Rights Reserved.
 */
abstract class BaseFragment<T : ViewDataBinding>(
    @LayoutRes val layoutResID: Int,
    val variableId: Int = BR.viewModel
) : Fragment(), IBaseView {

    lateinit var dataBinding: T
   private lateinit var viewModel: BaseViewModel


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        dataBinding = DataBindingUtil.inflate(inflater, layoutResID, container, false)
        return dataBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dataBinding.lifecycleOwner = this

        viewModel = getVM()

        dataBinding.setVariable(variableId, viewModel)
        lifecycle.addObserver(viewModel)
        initData()
    }

    override fun onDestroy() {
        super.onDestroy()
        if (this::dataBinding.isInitialized) {
            dataBinding.unbind()
        }
        lifecycle.removeObserver(viewModel)
    }

}