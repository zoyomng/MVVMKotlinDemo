package com.zoyo.mvvmkotlindemo.ui.page

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.zoyo.mvvmkotlindemo.api.GitHubApi
import com.zoyo.mvvmkotlindemo.constant.Constant
import com.zoyo.mvvmkotlindemo.model.Repo
import kotlinx.coroutines.flow.Flow

/**
 * Copyright (c) dtelec, Inc All Rights Reserved.
 */
class GithubRepositoryImpl(private val gitHubApi: GitHubApi) : GitHubRepository {

    override fun postsOfGithub(query: String): Flow<PagingData<Repo>> = Pager(
        PagingConfig(
            pageSize = Constant.PAGE_SIZE,
            enablePlaceholders = false
        )
    ) {
        GithubSource(query = query, gitHubApi = gitHubApi)
    }.flow
}