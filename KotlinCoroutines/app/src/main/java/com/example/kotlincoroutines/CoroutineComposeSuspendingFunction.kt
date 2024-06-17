package com.example.kotlincoroutines

import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import kotlin.system.measureTimeMillis
import kotlin.time.measureTime

//sequenctial Execution ---- Concurrent Execution --- Lazy Coroutine Execution
//by Default functions are executed sequentially within a coroutine
//fun main()= runBlocking {
//    println("Main program starts: ${Thread.currentThread().name}") //main thread
//
//    val time= measureTimeMillis{
//        val msgOne= getMessageOne()
//        val msgTwo= getMessageTwo()
//        println("The entire Message is: ${msgOne+msgTwo}")
//    }
//println("After working in getMessageOne()")
//    println("Completed in $time ms")
//
//    println("Main program ends: ${Thread.currentThread().name}") //main thread
//}

//Concurrent Execution
//fun main()= runBlocking {
//    println("Main program starts: ${Thread.currentThread().name}") //main thread
//
//    val time= measureTimeMillis{
//        //making these functions run parallel
//        val msgOne:Deferred<String> = async { getMessageOne()} //launches another background coroutine that run in parallel
//        val msgTwo:Deferred<String> = async { getMessageTwo()}
// we can also use launch here but that would limit the program as not to return anything
//        println("The entire Message is: ${msgOne.await()+msgTwo.await()}")
//    }
//
//    println("Completed in $time ms")
//
//    println("Main program ends: ${Thread.currentThread().name}") //main thread
//}

// Lazy Execution
//incase we want our program to launch a coroutine or only in case we use its result in the program then we use lazy
//if result is not used then coroutine doesnot start and function does get callled and vise versa
fun main()= runBlocking {
    println("Main program starts: ${Thread.currentThread().name}") //main thread

        val msgOne:Deferred<String> = async (start = CoroutineStart.LAZY){ getMessageOne()}
        val msgTwo:Deferred<String> = async(start = CoroutineStart.LAZY) { getMessageTwo()}

    println("The entire Message is: ${msgOne.await()+msgTwo.await()}")

    println("Main program ends: ${Thread.currentThread().name}") //main thread
}

suspend fun getMessageOne():String{
    delay(1000L)
    println("After working in getMessageOne()")
    return "Hello "
}

suspend fun getMessageTwo():String{
    delay(1000L)
    println("After working in getMessageTwo()")
    return "World!"
}