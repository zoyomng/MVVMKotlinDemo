package com.zoyo.mvvmkotlindemo.model

/**
 *---日期----------维护人-----------
 *2021/1/18       97440
 *{
 * code:0,
 * message:"",
 *  {
 *   id:1,
 *   name:"二狗"
 *  }
 * }
 *
 */
data class ResponseWrapper<Content>(
    open val code: Int,
    open val message: String,
    open val content: Content
)

