package com.zoyo.core.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment

/**
 * Copyright (c) dtelec, Inc All Rights Reserved.
 */
abstract class BaseFragment<T : ViewDataBinding>(
    @LayoutRes val layoutResID: Int,
    val variableId: Int,
) : Fragment(), IBaseView {

    lateinit var dataBinding: T
    private lateinit var viewModel: BaseViewModel


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {

        dataBinding = DataBindingUtil.inflate(inflater, layoutResID, container, false)
        return dataBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dataBinding.lifecycleOwner = this

        viewModel = getVM()

        dataBinding.setVariable(variableId, viewModel)
//        lifecycle.addObserver(viewModel)
        initialize()
    }

    abstract fun getVM(): BaseViewModel

    override fun onDestroy() {
        super.onDestroy()
        if (this::dataBinding.isInitialized) {
            dataBinding.unbind()
        }
        //解除ViewModel对生命周期的感应
//        if (this::viewModel.isInitialized) {
//            lifecycle.removeObserver(viewModel)
//        }
    }

}