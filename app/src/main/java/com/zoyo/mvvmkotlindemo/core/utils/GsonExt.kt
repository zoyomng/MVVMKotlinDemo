package com.zoyo.mvvmkotlindemo.core.utils

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type

/**
 * Copyright (c) dtelec, Inc All Rights Reserved.
 */

inline fun <reified T : Any> Gson.bean2Json(src: T): String = toJson(src)

inline fun <reified T : Any> Gson.json2Bean(json: String): T =
    fromJson(json, object : TypeToken<T>() {}.type)

inline fun <reified T : Any> Gson.json2List(json: String): List<T> {
    return Gson().fromJson(json, object : ParameterizedType {
        override fun getActualTypeArguments(): Array<Type> {
            return arrayOf(object : TypeToken<T>() {}.type)
        }

        override fun getRawType(): Type {
            return MutableList::class.java
        }

        override fun getOwnerType(): Type? = null

    })
}