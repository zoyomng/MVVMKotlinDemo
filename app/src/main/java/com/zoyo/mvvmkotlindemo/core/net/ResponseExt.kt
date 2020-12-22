package com.zoyo.mvvmkotlindemo.core.net

import retrofit2.Response

/**
 * Copyright (c) dtelec, Inc All Rights Reserved.
 */
inline fun <T> processResponse(request: () -> Response<T>): Results<T> {
    return try {
        val response = request()
        val responseCode = response.code()
        val responseMessage = response.message()
        if (response.isSuccessful) {
            Results.success(response.body()!!)
        } else {
            Results.failure(Errors.NetworkError(responseCode, responseMessage))
        }

    } catch (e: Exception) {
        Results.failure(Errors.NetworkError())
    }

}