package com.zoyo.mvvmkotlindemo.ui.extension

import com.zoyo.mvvmkotlindemo.core.base.BaseViewModel
import com.zoyo.mvvmkotlindemo.core.extensions.antiShakeClick
import com.zoyo.mvvmkotlindemo.core.utils.L

/**
 * zoyomng 2021/1/26
 */
class ExtensionViewModel : BaseViewModel() {

    fun click() {
        antiShakeClick {
            L.json(   "{\"name\":\"zoyomng\",\"age\":24}")
            L.json("\"name\":\"zoyomng\",\"age\":24}")
        }
    }
}