package com.zoyo.mvvmkotlindemo.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.zoyo.mvvmkotlindemo.R
import com.zoyo.mvvmkotlindemo.constant.Constant
import com.zoyo.mvvmkotlindemo.core.base.BaseViewModel

class HomeViewModel : BaseViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is home Fragment"
    }
    val text: LiveData<String> = _text

    val homeAdapter =
        HomeAdapter(R.layout.item_home, Constant.SUBJECT_DATA)

}