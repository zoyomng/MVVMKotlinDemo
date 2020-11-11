package com.zoyo.mvvmkotlindemo.ui.page

import androidx.paging.PagingSource
import com.zoyo.mvvmkotlindemo.api.GitHubApi
import com.zoyo.mvvmkotlindemo.constant.Constant
import com.zoyo.mvvmkotlindemo.model.Repo
import java.lang.Exception
import java.security.Key

/**
 * Copyright (c) dtelec, Inc All Rights Reserved.
 */
class GithubSource(private val gitHubApi: GitHubApi, val query: String) :
    PagingSource<Int, Repo>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Repo> {
        return try {

            //如果params.key没有被定义,则刷新第一页数据
            val pageNo = params.key ?: 1

            val searchRepos = gitHubApi.searchRepos(
                query = query,
                pageNo = pageNo,
                pageSize = params.loadSize
            )

            LoadResult.Page(
                //被加载进来的数据
                data = searchRepos.items,
                prevKey = null,
                nextKey = searchRepos.nextPage
            )
        } catch (e: Exception) {
            //解决出现的异常(如网络请求失败),并返回一个LoadResult.Error
            LoadResult.Error(e)
        }
    }

}