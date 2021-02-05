package com.zoyo.mvvmkotlindemo.ui.kotlin;

/**
 * zoyomng 2021/1/29
 * <p>
 * const val与 val 都会生成对应 Java 的static final修饰的字段，
 * 而 const val 会以 public 修饰，而 val 会以 private 修饰。
 * 同时，编译器还会为 val 字段生成 get 方法，以便外部访问。
 * <p>
 * const val ->public final static   -->没有生成get()方法,实际也不需要
 * val       ->private final static  -->生成get()方法,便于调用
 */
public class TestJava {

    public static void main(String[] args) {
        System.out.println(PersonKt.name); //kotlin文件会默认生成kotlin文件名+Kt的java类文件
//        System.out.println(PersonKt.getName()); //编译错误

//        System.out.println(PersonKt.age); //编译错误
        System.out.println(PersonKt.getAge());
    }
}
