package com.zoyo.mvvmkotlindemo.ui.extension

import androidx.fragment.app.viewModels
import com.zoyo.mvvmkotlindemo.R
import com.zoyo.core.base.BaseFragment
import com.zoyo.core.base.BaseViewModel
import com.zoyo.core.extensions.antiShakeClick
import com.zoyo.core.utils.L
import com.zoyo.core.utils.centerToast
import com.zoyo.mvvmkotlindemo.databinding.FragmentExtensionBinding
import com.zoyo.mvvmkotlindemo.BR

/**
 * zoyomng 2021/1/26
 */
class ExtensionFragment : BaseFragment<FragmentExtensionBinding>() {
    private val viewModel by viewModels<ExtensionViewModel>()

    override fun getVM(): BaseViewModel = viewModel
    override fun getVariableId(): Int = BR.viewModel
    override fun getLayoutId(): Int = R.layout.fragment_extension

    override fun initialize() {
        dataBinding.btAntiShakeClick2.antiShakeClick {
            context?.centerToast("点击了事件")
            L.e("点击了事件")
        }
    }

}