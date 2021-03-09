package com.zoyo.core.utils

import android.util.Log
import com.zoyo.core.BuildConfig
import org.json.JSONArray
import org.json.JSONObject

/**
 * zoyomng 2021/1/26
 */

class L {

    companion object {
        private val TAG = "===================="

        fun e(text: String) {
            if (!BuildConfig.DEBUG) return

            val stackTraceInfo = fetchStackTraceInfo()
            Log.e(TAG, "(${stackTraceInfo?.fileName}:${stackTraceInfo?.lineNumber}) \n $text")
        }

        fun json(json: String) {
            if (!BuildConfig.DEBUG) return

            val stackTraceInfo = fetchStackTraceInfo()
            Log.e(
                TAG,
                "(${stackTraceInfo?.fileName}:${stackTraceInfo?.lineNumber}) \n ${prettyJson(json)}"
            )
        }

        private fun prettyJson(json: String): String {
            return runCatching {
                if (json.startsWith("{")) {
                    val jsonObject = JSONObject(json)
                    return@runCatching jsonObject.toString(2)
                }
                if (json.startsWith("[")) {
                    val jsonArray = JSONArray(json)
                    return@runCatching jsonArray.toString(2)
                }
                return@runCatching "Invalid Json,Please Check: $json"
            }.getOrDefault("Invalid Json,Please Check: $json")
        }

        /** 定位调用位置
         * 因为我们的入口是L类的方法，所以，我们直接遍历，L类相关的下一个非L类的栈帧信息就是具体调用的方法。
         */
        fun fetchStackTraceInfo(): StackTraceElement? {
            val stackTrace = Thread.currentThread().stackTrace
            var lastLogTag = false
            for (stackTraceElement in stackTrace) {
                //"com.zoyo.core.utils.L$Companion"
                val isLog = stackTraceElement.className == "${L::class.java.name}${'$'}Companion"
                if (lastLogTag && !isLog) {
                    return stackTraceElement
                }
                lastLogTag = isLog
            }
            return null
        }
    }
}
/*
2021-01-25 13:36:39.458 15506-15506/com.zoyo.mvvmkotlindemo E/====================: (ExtensionViewModel.kt:14)
{
    "name": "zoyomng",
    "age": 24
}
 */



