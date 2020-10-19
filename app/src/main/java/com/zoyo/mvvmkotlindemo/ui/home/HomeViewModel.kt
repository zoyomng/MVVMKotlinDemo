package com.zoyo.mvvmkotlindemo.ui.home

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.zoyo.mvvmkotlindemo.core.mvvm.base.BaseViewModel
import com.zoyo.mvvmkotlindemo.db.AppDatabase
import com.zoyo.mvvmkotlindemo.db.dao.SubjectDao

class HomeViewModel(app: Application) : BaseViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is home Fragment"
    }
    val text: LiveData<String> = _text

    private val dao = AppDatabase.get(app).subjectDao()
    val allSubjects = Pager(
        PagingConfig(
            pageSize = 60,
            enablePlaceholders = true,
            maxSize = 200
        )
    ) {
        dao.getAllSubject()
    }.flow


}