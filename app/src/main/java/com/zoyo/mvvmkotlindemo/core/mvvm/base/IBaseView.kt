package com.zoyo.mvvmkotlindemo.core.mvvm.base

/**
 * Copyright (c) dtelec, Inc All Rights Reserved.
 */
interface IBaseView {
    fun getVM(): BaseViewModel
    fun initData()
}
