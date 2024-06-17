package com.example.kotlincoroutines
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Job
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

//Functions used to create Coroutines are coroutine builder

//life coroutine has scope same as the screen or activity it has been created on
// so when screen distroyed coroutine also gets destroyed but
// if globalscope.launch is used then lifespan of coroutine is equal to entire app lifespan

    //Creates coroutine at global app level
    /*GlobalScope.launch {  //Highly Discouraged use only when needed
        //file download
        //play music
    }*/

    //creates coroutine at local scope
//    launch{  /by default use launch
//            //some data computation
//        //login operation
//    }

//launch coroutine builder is also called fire and forget

//fun main()= runBlocking{
//    println("Main program starts: ${Thread.currentThread().name}") //main thread
//
//    val job: Job =launch {  //main //inherit the thread of the immediate parent coroutine fun
//        println("Fake Work Start: ${Thread.currentThread().name}")//main
//        delay(1000)  //it blocked the coroutine for 1 sec not the thread main
//        println("Fake work finished: ${Thread.currentThread().name}") //Either on same coroutine thread or any other thread it could be executed
//    }
//
////    delay(2000) //main thread not right way to wait for coroutine finsh
//    job.join() //waits for the coroutine to finish work //no need to hardcode the time
//    //can also cancel coroutine by job.cancel()
//
//    println("Main program ends: ${Thread.currentThread().name}") //main thread
//}


//async
//Dont return job objected like launch but sub class of job
//Deferred which is generic type so that we can return something
//GlobalScope can also be used with it

fun main()= runBlocking{
    println("Main program starts: ${Thread.currentThread().name}") //main thread

    val jobDeffered: Deferred<Int> =async {  //main //inherit the thread of the immediate parent coroutine fun
        println("Fake Work Start: ${Thread.currentThread().name}")//main
        delay(1000)  //it blocked the coroutine for 1 sec not the thread main
        println("Fake work finished: ${Thread.currentThread().name}") //Either on same coroutine thread or any other thread it could be executed
        15
    }

    val num=jobDeffered.await() //can be used to get the returned result
    //jobDeffered.join() //waits for the coroutine to finish work //no need to hardcode the time

    println("Main program ends: ${Thread.currentThread().name}") //main thread
}

//runBlocking is used usually to test the testcases for suspending function
//suspend fun myOwnSuspendingFunc(){
//    delay(1000)
//}


//runBlocking always blocks the thread in which it is running