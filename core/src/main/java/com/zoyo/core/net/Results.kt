package com.zoyo.core.net

/**
 * Copyright (c) dtelec, Inc All Rights Reserved.
 * sealed 密封类
 * <out T> 声明处型变:只作返回值类型,不作消费使用
 * interface Source<out T> { fun nextT(): T }
 */
sealed class Results<out T> {
    companion object {
        fun <T> success(result: T): Results<T> = Success(result)
        fun <T> failure(error: Throwable): Results<T> = Failure(error)
    }

    data class Success<out T>(val data: T) : Results<T>()
    data class Failure(val error: Throwable) : Results<Nothing>()

}