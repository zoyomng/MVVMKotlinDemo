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
                repository.postsOfGithub(it, 30)
            }
    ).flattenMerge(2)

    fun shouldShowList(s: String): Boolean = handle.get<String>(KEY_SUBREDDIT) != s

    fun showList(s: String) {
        if (!shouldShowList(s)) return
        clearListCh.offer(Unit)
        handle.set(KEY_SUBREDDIT, s)
    }


}