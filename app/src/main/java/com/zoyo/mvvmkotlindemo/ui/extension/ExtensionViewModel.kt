package com.zoyo.mvvmkotlindemo.ui.extension

import com.zoyo.core.base.BaseViewModel
import com.zoyo.core.extensions.antiShakeClick
import com.zoyo.core.utils.L

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