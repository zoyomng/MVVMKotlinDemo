package com.zoyo.mvvmkotlindemo.ui.kotlin

import com.zoyo.mvvmkotlindemo.R
import com.zoyo.core.base.RootFragment
import com.zoyo.core.utils.L
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*

/**
 * 2021/1/22       97440
 * 1.协程:通过将复杂性放入库来简化异步编程,协程依赖于线程,但挂起时不需要阻塞线程
 *
 * 2.协程体:一个用suspend修饰的无参无返回值的函数类型
 * suspend函数会将整个协程挂起,而不仅仅是这个suspend函数,
 * 即一个协程中有多个挂起函数,它们是顺序执行的
 *
 */


class KotlinBasicFragment : RootFragment() {
    override fun getLayoutId(): Int = R.layout.fragment_kotlin_basic

    override fun initialize() {
//        test()

//        val job = GlobalScope.launch {
//            delay(2000)
//            L.e(
//                "协程执行结束, 线程id:${Thread.currentThread().id} name:${Thread.currentThread().name}"
//            )
//        }


//        job.isActive
//        job.isCancelled
//        job.isCompleted
//        job.cancel()
//        job.start()
//        job.join()

        /**async
         * async跟launch的用法一样
         * 1.但async的返回值是Deferred,将最后一个封装成该对象
         * 2.async可以支持并发,一般跟await一起使用
         * 3.是不阻塞线程的
         */
//        GlobalScope.launch {
//            val result1 = GlobalScope.async {
//                getResult1()
//            }
//
//            val result2 = GlobalScope.async {
//                getResult2()
//            }
//
//            val result = result1.await() + result2.await()
//            L.e("result=$result")
//        }

        //Flow基本使用
        GlobalScope.launch {
            //flow的创建
//            flow {
//                for (i in 1..5) {
//                    delay(100)
//                    emit(i)
//                }
//            }.collect {
//                L.e("$it")
//            }
//
//            flowOf(6, 7, 8, 9)
//                .onEach {
//                    delay(100)
//                }
//                .collect {
//                    L.e("$it")
//                }
//
//            listOf(11, 12, 13, 14, 15)
//                .asFlow()
//                .onEach {
//                    delay(100)
//                }
//                .collect {
//                    L.e("$it")
//                }

            //切换线程
//            flow {
//                for (i in 1..5) {
//                    delay(100)
//                    emit(i)
//                }
//            }.map {
//                it * it
//            }.flowOn(Dispatchers.IO) //指定flowOn前面的代码所在线程
//                .collect {
//                    //collect所在线程取决于flow所在线程
//                    //不要使用withContext()来切换flow线程
//                    L.e("${Thread.currentThread().name} $it")
//                }

            /**flow取消
             * 如果flow是在一个挂起函数内被挂起,那么flow是可以被取消的
             * 否则不可以取消
             */
//            withTimeoutOrNull(2500) {
//                flow {
//                    for (i in 1..5) {
//                        delay(1000)
//                        emit(i)
//                    }
//                }.collect {
//                    L.e("$it")
//                }
//            }
//            L.e("Done")


            /**捕获异常
             * onCompletion不能捕获异常,只能判断是否有异常
             * catch可以捕获来自上游的异常,不会影响下游
             */

            flow {
                emit(1)
                try {
                    throw RuntimeException()
                } catch (e: Exception) {
                    e.stackTrace
                }
            }.onCompletion {
                L.e("Done")
            }.collect {
                L.e("$it")
            }

//            flow {
//                emit(1)
//                throw RuntimeException()
//            }.onCompletion { cause ->
//                if (cause != null)
//                    L.e("Flow completed exceptionally")
//                else
//                    L.e("Done")
//            }.collect {
//                L.e("$it")
//            }
//
//            flow {
//                emit(1)
//                throw RuntimeException()
//            }
//                .onCompletion { cause ->
//                    if (cause != null)
//                        L.e("Flow completed exceptionally")
//                    else
//                        L.e("Done")
//                }
//                .catch { L.e("catch eception") }
//                .collect {
//                    L.e("$it")
//                }
//
//            flow {
//                emit(1)
//                throw RuntimeException()
//            }
//                .catch { L.e("catch eception") }
//                .onCompletion { cause ->
//                    if (cause != null)
//                        L.e("Flow completed exceptionally")
//                    else
//                        L.e("Done")
//                }
//                .collect {
//                    L.e("$it")
//                }
//
//
//            /**
//             * retry操作符最终调用的是热tryWhen操作符,
//             * retryWhen 当返回true时才会进行重试,
//             * attempt:尝试的此时,从0开始
//             */
//
//            (1..5).asFlow()
//                .onEach {
//                    if (it == 3) throw RuntimeException("Error on $it")
//                }
//                .retry(2) {
//                    if (it is RuntimeException) {
//                        return@retry true
//                    }
//                    false
//                }
//                .onEach { L.e("emitting $it") }
//                .catch { it.printStackTrace() }
//                .collect()
//
//            (1..5).asFlow()
//                .onEach {
//                    if (it == 3) throw java.lang.RuntimeException("Error on $it")
//                }
//                .retryWhen { cause, attempt -> attempt < 2 }
//                .catch { it.printStackTrace() }
//                .collect()
            /**
             * Flow Lifecycle
             * 执行结果:
             * Starting flow
             * On each 1
             * On each 2
             * Flow completed
             * Exception : Error on 3
             *
             */
            val asFlow = (1..5).asFlow()
            val flowOf = flowOf("one", "two")

            asFlow
                .onEach {
                    if (it == 3) throw RuntimeException("Error on $it")
                }
                .onStart {
                    L.e("starting flow")
                }
                .onEach {
                    L.e("On each $it")
                }
                .catch {
                    L.e("Exception:${it.message}")
                }
                .onCompletion {
                    L.e("Flow completed")
                }
                .collect()

            /**操作符
             * reduce:累积计算
             * fold:设置初始值+reduce计算
             * zip:将2个flow进行合并操作,flowA与flowB是按顺序一对一地对应计算,
             *      即使flowB有延时flowA也会等待,
             *      合并后的新flow个数=较小flow的个数
             * combine:也是合并操作,每次从flowA发出的item,会与flowB发出的最新的item合并;
             *          flowB发出的最新的item也会跟flowA最新的结合
             * flattenMerge:不会组合多个flow,而是将它们作为单个流执行
             */

            //对平方数列进行求和
            val reduce = asFlow
                .map { it * it }
                .reduce { a, b ->
                    a + b
                }
            /* a b
               1+4=5
               5+9=14
               14+16=30
               30+25=55
             */
            L.e("$reduce")

            //计算阶乘
            val reduce1 = asFlow
                .reduce { a, b ->
                    a * b
                }
            /* 1*2*3*4*5 = 120
                a   b
                1   2 = 2
                2   3 = 6
                6   4 = 24
               24   5 = 120
             */
            L.e("$reduce1")

            val fold1 = asFlow
                .fold(2) { a, b ->
                    a + b
                }
            L.e("$fold1") //2(初始值)+1+2+3+4+5=17
            val fold2 = asFlow
                .fold(1) { a, b ->
                    a + b
                }
            L.e("$fold2") //1(初始值)+1+2+3+4+5=16

            //zip
            val flowA = asFlow
            val flowB = flowOf.onEach { delay(100) }
            flowA.zip(flowB) { a, b -> "$a -> $b" }.collect { L.e(it) }

            //combine
            val flowC = asFlow.onEach { delay(100) }
            val flowD = flowOf.onEach { delay(200) }
            flowC.combine(flowD) { a, b -> "$a->$b" }.collect { L.e(it) }
            /*
            1 and one
            2 and one
            3 and one
            3 and two
            4 and two
            5 and two
            5 and three
            5 and four
            5 and five
             */

            //flattenMerge

            flowOf(asFlow, flowOf)
                .flattenConcat()
                .collect { L.e("$it") }

        }


    }

    /**
     * runBlocking 启动的协程任务会阻断当前线程,直到该协程执行结束,页面才会显示
     */
    private fun test() = runBlocking {
        repeat(3) {
            L.e(
                "协程执行$it 线程id:${Thread.currentThread().id} name:${Thread.currentThread().name}"
            )
            delay(1000)
        }
    }

    /**
     *协程执行0 线程id:1 name:main
     *协程执行1 线程id:1 name:main
     *协程执行2 线程id:1 name:main
     *-->页面显示
     *协程执行结束, 线程id:563 name:DefaultDispatcher-worker-2
     */


}


private suspend fun getResult1(): Int {
    delay(3000)
    return 1
}

private suspend fun getResult2(): Int {
    delay(4000)
    return 2
}