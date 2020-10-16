package com.zoyo.mvvmkotlindemo.db

import java.util.concurrent.Executors

/**
 * Copyright (c) dtelec, Inc All Rights Reserved.
 */
private val IO_EXECUTOR = Executors.newSingleThreadExecutor()
//在一个专用的后台线程上运行阻塞线程的代码的实用方法，用于io/数据库工作
fun ioThread(f: () -> Unit) {
    IO_EXECUTOR.execute(f)
}