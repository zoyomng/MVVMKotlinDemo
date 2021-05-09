package com.zoyo.core.base

import androidx.annotation.LayoutRes

/**
 * Copyright (c) dtelec, Inc All Rights Reserved.
 */
interface IBaseView {
    @LayoutRes
    fun getLayoutId(): Int

    fun initialize()
}
