package com.zoyo.mvvmkotlindemo.ui.page.page

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
            pageSize = 20,
            //初始化加载数量，默认为 pageSize * 3
            initialLoadSize = 60,
            //预刷新的距离，距离最后一个 item 多远时加载数据
            //预选距离:给定应用界面中的最后一个可见项，分页库应尝试提前获取的超出此最后一项的项数。此值应是页面大小的数倍大。
            prefetchDistance = 3,
            //如果希望界面在应用完成数据获取前显示列表，可以向用户显示占位符列表项
            enablePlaceholders = true,
            // 一次应在内存中保存的最大数据
            maxSize = 200
        )
    ) {
        // 数据源，要求返回的是 PagingSource 类型对象
        dao.allCheesesByName()
    }.flow // 最后构造的和外部交互对象，有 flow 和 liveData 两种

    fun insert(text: CharSequence) = ioThread {
        dao.insert(Cheese(id = 0, name = text.toString()))
    }

    fun remove(cheese: Cheese) = ioThread {
        dao.remove(cheese)
    }

}