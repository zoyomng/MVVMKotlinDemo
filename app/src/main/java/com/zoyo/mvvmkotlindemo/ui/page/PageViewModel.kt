package com.zoyo.mvvmkotlindemo.ui.page

import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.zoyo.mvvmkotlindemo.App
import com.zoyo.mvvmkotlindemo.core.base.BaseViewModel
import com.zoyo.mvvmkotlindemo.db.AppDatabase
import com.zoyo.mvvmkotlindemo.db.entity.Cheese
import com.zoyo.mvvmkotlindemo.db.ioThread


class PageViewModel : BaseViewModel() {

    private val dao = AppDatabase.get(App.appContext).cheeseDao()

    //kotlin中使用Pager支持的flow(也可以使用LiveData)
    val allCheeses = Pager(
        PagingConfig(
            //每个页面中的项数
            pageSize = 60,
            //如果希望界面在应用完成数据获取前显示列表，可以向用户显示占位符列表项
            enablePlaceholders = true,
            //预选距离:给定应用界面中的最后一个可见项，分页库应尝试提前获取的超出此最后一项的项数。此值应是页面大小的数倍大。
            maxSize = 200
        )
    ) {
        dao.allCheesesByName()
    }.flow

    fun insert(text: CharSequence) = ioThread {
        dao.insert(Cheese(id = 0, name = text.toString()))
    }

    fun remove(cheese: Cheese) = ioThread {
        dao.remove(cheese)
    }

}