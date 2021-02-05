package com.zoyo.mvvmkotlindemo.ui.extension

import androidx.fragment.app.viewModels
import com.zoyo.mvvmkotlindemo.R
import com.zoyo.mvvmkotlindemo.core.base.BaseFragment
import com.zoyo.mvvmkotlindemo.core.base.BaseViewModel
import com.zoyo.mvvmkotlindemo.core.extensions.antiShakeClick
import com.zoyo.mvvmkotlindemo.core.utils.L
import com.zoyo.mvvmkotlindemo.core.utils.centerToast
import com.zoyo.mvvmkotlindemo.databinding.FragmentExtensionBinding

/**
 * zoyomng 2021/1/26
 */
class ExtensionFragment : BaseFragment<FragmentExtensionBinding>(R.layout.fragment_extension) {
    private val viewModel by viewModels<ExtensionViewModel>()

    override fun getVM(): BaseViewModel {
        return viewModel
    }

    override fun initialize() {
        dataBinding.btAntiShakeClick2.antiShakeClick {
            context?.centerToast("点击了事件")
            L.e("点击了事件")
        }
    }
}