package com.zoyo.mvvmkotlindemo.ui.kotlin

/**
 * zoyomng 2021/1/29
 *
 *1.const val 和 val的区别

 *  const val与 val 都会生成对应 Java 的static final修饰的字段，
 * 而 const val 还会生成 public 修饰，
 * 而 val 生成 private 修饰,及 get() 方法，以便外部访问。
 * const val ->public final static   -->没有生成get()方法,实际也不需要
 * val       ->private final static  -->生成get()方法,便于调用

 * 在 Kotlin 中，编译时常量使用 const 修饰符修饰，它必须满足以下要求：
 *必须属于顶层Top-level，或对象声明object或伴生对象的成员；
 *被基本数据类型或者String类型修饰的初始化变量；
 *没有自定义 getter() 方法；
 *只有 val 修饰的才能用 const 修饰。


 *2.lateinit
 *不能修饰 val 类型的变量；
 *不能声明于可空变量，即类型后面加?，如String?；
 *修饰后，该变量必须在使用前初始化，否则会抛 UninitializedPropertyAccessException 异常；
 *不能修饰基本数据类型变量，例如：Int，Float，Double 等数据类型，String 类型是可以的；
 *不需额外进行空判断处理，访问时如果该属性还没初始化，则会抛出空指针异常；
 *只能修饰位于class body中的属性，不能修饰位于构造方法中的属性。
 */

//顶层Top-level
const val name = "zoyomng"
//const val user = User() //编译错误,const只能修饰基本类型以及String类型的值

val age = 2

class Person {
//    const val email = "zoyomng@163.com" //编译错误，这里没有违背只能使用val修饰，但是这里的属性不属于top-level级别的，也不属于object里面的

    //这里的Instance使用了object关键字修饰，表示Instance是个单例，Kotlin其实已经为我们内置了单例，无需向 Java那样手写单例
    object Instance {
        const val CONST_TYPE = 0 //正确,属于object类
    }


}