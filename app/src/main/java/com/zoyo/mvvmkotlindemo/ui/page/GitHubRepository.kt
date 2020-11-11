package com.zoyo.mvvmkotlindemo.ui.page

import androidx.paging.PagingData
import com.zoyo.mvvmkotlindemo.model.Repo
import kotlinx.coroutines.flow.Flow

/**
 * Copyright (c) dtelec, Inc All Rights Reserved.
 */
interface GitHubRepository {
    fun postsOfGithub(query: String): Flow<PagingData<Repo>>
}