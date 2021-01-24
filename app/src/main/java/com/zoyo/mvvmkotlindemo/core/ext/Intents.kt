package com.zoyo.mvvmkotlindemo.core.ext

import android.app.Activity
import android.content.Context
import android.content.Intent

/**
 * 2021/1/22       97440
 * 内联函数:在编译期,将函数方法体注入到调用这个函数的地方
 * 优缺点:代码量增多,性能提升,减少方法压栈出栈的资源消耗,对代码可读性不会造成影响
 * 关键字段:inline noinline crossinline
 * reified:具体化
 * 具体化泛型,java中是不允许使用泛型的类型的(如T.class)
 */


/**
 * 使用方法:startActivity<MainActivity>()
 */
inline fun <reified T : Activity> Context.startActivity() {
    startActivity(Intent(this, T::class.java))
}
