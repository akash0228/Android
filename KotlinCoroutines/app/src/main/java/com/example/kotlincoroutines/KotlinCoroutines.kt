package com.example.kotlincoroutines

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlin.concurrent.thread

//fun main(){ //executes in main thread
//    println("Main program starts: ${Thread.currentThread().name}")
//
//    // some other code...
////    thread { //creates a background thread (worker thread)
////        println("Fake Work Start: ${Thread.currentThread().name}")
////        Thread.sleep(1000) //Pretend Doing Some Work  thread blocked for 1 sec
////        println("Fake work finished")
////    }
//
////    //coroutines
////    GlobalScope.launch{ //creates a background coroutine that run on a background thread
////        println("Fake Work Start: ${Thread.currentThread().name}")
////        Thread.sleep(1000) //Pretend Doing Some Work
//     //     delay(1000)  //it blocked the coroutine for 1 sec not the thread and hence more efficient
////        println("Fake work finished")
////    }
//
//    GlobalScope.launch{ //creates a background coroutine that run on a background thread
//        println("Fake Work Start: ${Thread.currentThread().name}")
//          delay(1000)  //it blocked the coroutine for 1 sec not the thread and
//        // hence more efficient because now thread can associate with other coroutine to do other task
//        println("Fake work finished: ${Thread.currentThread().name}") //Either on same coroutine thread or any other thread it could be executed
//    }
//
////   Thread.sleep(2000) //force wait for coroutine by blocking main thread
//    //cant be replaced by delay because delay can only be called inside coroutine function
//    //to use it we need
//
//    //create coroutine that blocks the current main thread
//    runBlocking { //wait for coroutine to finish //not right way to wait practically
//        delay(2000)
//    }
//
//
//    println("Main program ends: ${Thread.currentThread().name}")
//}
//Note Application Doesn't wait for the coroutine but waits for the thread to end


fun main(){ //executes in main thread

    runBlocking {//creates coroutine that blocks the current main thread
        println("Main program starts: ${Thread.currentThread().name}") //main thread

        GlobalScope.launch { //creates a background coroutine that run on a background thread  t1

            println("Fake Work Start: ${Thread.currentThread().name}")//t1
//            delay(1000)  //it blocked the coroutine for 1 sec not the thread t1
            mySuspendFunc(1000)
            println("Fake work finished: ${Thread.currentThread().name}") //Either on same coroutine thread or any other thread it could be executed
        }

//        delay(2000) //main thread
        mySuspendFunc(2000)
        println("Main program ends: ${Thread.currentThread().name}") //main thread
    }
}

//Own Suspend function

suspend fun mySuspendFunc(time:Long){
    delay(time)
}