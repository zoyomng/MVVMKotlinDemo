package com.zoyo.mvvmkotlindemo.ui.kotlin

/**
 * zoyomng 2021/1/29
 */
class TestKotlin {
    lateinit var person1: Person
//    lateinit var person2: Person? //编译错误,必须是非空

    fun init() {
        person1 = Person()
        if (this::person1.isInitialized) {

        }
    }

}