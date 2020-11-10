package com.zoyo.mvvmkotlindemo.ui.page

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.zoyo.mvvmkotlindemo.api.GitHubApi
import com.zoyo.mvvmkotlindemo.model.Repo
import kotlinx.coroutines.flow.Flow

/**
 * Copyright (c) dtelec, Inc All Rights Reserved.
 */
class GithubRepositoryImpl(private val gitHubApi: GitHubApi) : GitHubRepository {

    override fun postsOfGithub(s: String, pageSize: Int): Flow<PagingData<Repo>> =
        Pager(PagingConfig(pageSize)) {
            GithubSource(query= s,gitHubApi = gitHubApi)
        }.flow
}