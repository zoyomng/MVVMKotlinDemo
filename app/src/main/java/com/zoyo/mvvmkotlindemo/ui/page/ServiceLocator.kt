package com.zoyo.mvvmkotlindemo.ui.page

import android.content.Context
import com.zoyo.mvvmkotlindemo.api.GitHubApi

/**
 * Copyright (c) dtelec, Inc All Rights Reserved.
 */
interface ServiceLocator {

    companion object {
        private val LOCK = Any()
        private var instance: ServiceLocator? = null
        fun instance(): ServiceLocator {
            synchronized(LOCK) {
                if (instance == null) {
                    instance = DefaultServiceLocator()
                }
                return instance!!
            }
        }
    }

    fun getRepository(): GitHubRepository
    fun getGithubApi(): GitHubApi
}

class DefaultServiceLocator : ServiceLocator {
    private val api by lazy {
        GitHubApi.create()
    }

    override fun getRepository(): GitHubRepository {
        return GithubRepositoryImpl(gitHubApi = getGithubApi())
    }

    override fun getGithubApi(): GitHubApi = api
}
