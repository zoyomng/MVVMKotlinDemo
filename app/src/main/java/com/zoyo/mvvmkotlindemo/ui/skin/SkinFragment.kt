package com.zoyo.mvvmkotlindemo.ui.skin

import androidx.fragment.app.viewModels
import com.zoyo.core.base.BaseFragment
import com.zoyo.core.base.BaseViewModel
import com.zoyo.mvvmkotlindemo.BR
import com.zoyo.mvvmkotlindemo.R
import com.zoyo.mvvmkotlindemo.databinding.FragmentSkinBinding

class SkinFragment : BaseFragment<FragmentSkinBinding>() {

    private val skinViewModel: SkinViewModel by viewModels()

    override fun getVariableId(): Int = BR.viewModel

    override fun getVM(): BaseViewModel = skinViewModel

    override fun getLayoutId(): Int = R.layout.fragment_skin

    override fun initialize() {
    }
}