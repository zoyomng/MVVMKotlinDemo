package com.zoyo.mvvmkotlindemo.ui.page

import androidx.paging.PagingSource
import com.zoyo.mvvmkotlindemo.api.GitHubApi
import com.zoyo.mvvmkotlindemo.model.Repo
import java.lang.Exception

/**
 * Copyright (c) dtelec, Inc All Rights Reserved.
 */
class GithubSource(private val gitHubApi: GitHubApi, val query: String) :
    PagingSource<String, Repo>() {
    override suspend fun load(params: LoadParams<String>): LoadResult<String, Repo> {
        return try {
            val items = gitHubApi.searchRepos(query = query, page = 1, itemsPerPage = 20).items
            LoadResult.Page(
                data = items.map { it },
                prevKey = null,
                nextKey = null
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}