package com.zoyo.mvvmkotlindemo.ui.page.page2

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.asFlow
import androidx.paging.PagingData
import com.zoyo.mvvmkotlindemo.core.base.BaseViewModel
import com.zoyo.mvvmkotlindemo.model.Repo
import com.zoyo.mvvmkotlindemo.ui.page.GitHubRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*


class PageWithNetworkViewModel(val repository: GitHubRepository, val handle: SavedStateHandle) :
    BaseViewModel() {

    companion object {
        const val KEY_SUBREDDIT = "Android"
        const val DEFAULT_SUBREDDIT = "Kotlin"
    }

    init {
        if (!handle.contains(KEY_SUBREDDIT)) {
            handle.set(KEY_SUBREDDIT, DEFAULT_SUBREDDIT)
        }
    }

    //Channel:协程之间通信
    private val clearListCh = Channel<Unit>(Channel.CONFLATED)

    @ExperimentalCoroutinesApi
    @FlowPreview
    val posts = flowOf(
        clearListCh.receiveAsFlow().map {
            //当被提交给AsyncPagingDataAdapter,创建一个可以立即展示空列表的PagingData
            PagingData.empty<Repo>()
        },
        handle.getLiveData<String>(KEY_SUBREDDIT)
            .asFlow()
            .flatMapLatest {
                repository.postsOfGithub(it)
            }
    ).flattenMerge(2)

    //如果跟上次查询的数据相同,则不再加载数据
    fun shouldShowList(query: String): Boolean = handle.get<String>(KEY_SUBREDDIT) != query

    fun showList(query: String) {
        if (!shouldShowList(query)) return
        clearListCh.offer(Unit)
        //搜索的关键字被SavedStateHandle保存
        handle.set(KEY_SUBREDDIT, query)
    }


}