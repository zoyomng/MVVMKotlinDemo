package com.zoyo.mvvmkotlindemo.ui.page

import android.app.Application
import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.zoyo.mvvmkotlindemo.core.base.BaseViewModel
import com.zoyo.mvvmkotlindemo.db.AppDatabase
import com.zoyo.mvvmkotlindemo.db.entity.Cheese
import com.zoyo.mvvmkotlindemo.db.ioThread


class PageViewModel(app: Application) : BaseViewModel() {

    private val dao = AppDatabase.get(app).cheeseDao()

    //kotlin中使用Pager支持的flow(也可以使用LiveData)
    val allCheeses = Pager(
        PagingConfig(
            pageSize = 60,
            enablePlaceholders = true,
            maxSize = 200
        )
    ) {
        dao.allCheesesByName()
    }.flow

    fun insert(text: CharSequence) = ioThread {
        dao.insert(Cheese(id = 0,name = text.toString()))
    }

    fun remove(cheese: Cheese) {
        dao.remove(cheese)
    }

}