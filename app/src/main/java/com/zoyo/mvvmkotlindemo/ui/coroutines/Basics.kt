package com.zoyo.mvvmkotlindemo.ui.coroutines

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 *---日期----------维护人-----------变更内容----------
2021/1/17       zoyomng          协程

 */

fun main(){
    //在后台启动一个协程
    GlobalScope.launch {
        delay(1000L) //非阻塞式等待
        println("world") //延迟后打印
    }
    print("hello,")
    Thread.sleep(2000L) //阻塞主线程2s保证jvm存活
}

