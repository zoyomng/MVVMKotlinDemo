package com.zoyo.core.utils

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type

/**
 * Copyright (c) dtelec, Inc All Rights Reserved.
 */

inline fun <reified T : Any> Gson.beanToJson(src: T): String = toJson(src)

/**
 * 简单的json转bean
 * 使用方式: val user:User = Gson.json2BeanSimple(json)
 * 因为运行期java/kotlin会将类型擦除
 */
inline fun <reified T : Any> Gson.jsonToBeanSimple(json: String): T = fromJson(json, T::class.java)

inline fun <reified T : Any> Gson.jsonToBean(json: String): T =
    fromJson(json, object : TypeToken<T>() {}.type)

inline fun <reified T : Any> Gson.jsonToList(json: String): List<T> {
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