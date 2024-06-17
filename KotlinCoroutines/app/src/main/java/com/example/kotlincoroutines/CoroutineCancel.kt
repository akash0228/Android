package com.example.kotlincoroutines

import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.NonCancellable
import kotlinx.coroutines.TimeoutCancellationException
import kotlinx.coroutines.cancelAndJoin
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import kotlinx.coroutines.withTimeout
import kotlinx.coroutines.withTimeoutOrNull
import kotlinx.coroutines.yield

//To Cancel coroutine, It Should be cooperative

//val job=launch{
//    //the code has to be cooperative to get cancelled
//}

//job.cancel() //if the coroutine is cooperative then cancel it
//job.join() //if cant be cancelled then it comes here and this wait for coroutine to finish

//these both are used so often
//that there is seprate fun to do this task

//job.cancelAndJoin() if the coroutine is cooperative then cancel it else if not then wait for the coroutine to finish

//what makes coroutine cooperative
//1.periodically invoke a suspending function that checks for cancellation
//2.only suspending func that belongs to kotlinx.coroutines package will make coroutine cooperative
//ex:-delay(),yeild(),withContext(),withTimeOut() etc

//fun main()= runBlocking {  //creates a blocking coroutine that executes in current thread main
//    println("Main program starts: ${Thread.currentThread().name}") //main thread
//
//    val job: Job =launch { //Thread main:Creates a non blocking coroutine
//        for(i in 0..500){
//
//            print("$i.")
////            Thread.sleep(50) //if this is used then coroutine is not cooperative
//           delay(50)//cooperative now and hence stops and gets cancel)
////            yield() //much faster than delay
//
//        }
//    }
//
//    delay(30) //print few value before we cancel
////    job.cancel()
////    job.join() //waits for coroutine to finish
//     job.cancelAndJoin()
//
//    println("\n Main program ends: ${Thread.currentThread().name}") // main thread
//}

//using flag to cancel internally

//fun main()= runBlocking{  //creates a blocking coroutine that executes in current thread main
//    println("Main program starts: ${Thread.currentThread().name}") //main thread
//
//    val job: Job =launch(Dispatchers.Default)  { //Thread main:Creates a non blocking coroutine
//        for(i in 0..500){
//            if(!isActive){  //this flag can also be used to end/cancel coroutine internally
//               // break
//                return@launch //retuns at launch level
//            }
//            print("$i.")
//           Thread.sleep(1) //if this is used then coroutine is not cooperative
//        }
//    }
//
//    delay(30) //print few value before we cancel
////    job.cancel()
////    job.join() //waits for coroutine to finish
//     job.cancelAndJoin() //makes isActive false when cancel
//
//    println("\n Main program ends: ${Thread.currentThread().name}") // main thread
//}

//Handling Exceptions
//suspending functions throw CancellationException on coroutine cancellation
//hence handled by try catch block

//fun main()= runBlocking{  //creates a blocking coroutine that executes in current thread main
//    println("Main program starts: ${Thread.currentThread().name}") //main thread
//
//    val job: Job =launch(Dispatchers.Default)  { //Thread main:Creates a non blocking coroutine
//        try {
//            for(i in 0..500){
//                print("$i.")
//                delay(5)
//            }
//        }
//        catch(ex:CancellationException){
//            print("\n Exception caught safely: ${ex.message}")  //our message will be printed here passed from cancel
//        }
//        finally {
//            //cant execute any suspending function as the coroutine running this code already cancelled
//            //delay(1)
//            //but if in case want to execute any suspending function from finally then wrap code within withContext(nonCancellable) function
//            withContext(NonCancellable){//its a coroutine builder
//                delay(1)
//                println("\n Close resources in finally")
//            }
//        }
//    }
//
//    delay(30) //print few value before we cancel
//    // job.cancelAndJoin()
//    //can add cancellation message
//    job.cancel(CancellationException("My own crash message"))
//    job.join()
//
//    println("\n Main program ends: ${Thread.currentThread().name}") // main thread
//}


//withTimeout && withTimeoutOrNull are also coroutine builders

fun main()= runBlocking {//main thread
    println("Main program starts: ${Thread.currentThread().name}") //main thread

//    try {
//        withTimeout(2000){ //if coroutine doesnot finish in given time then it throws the TimOutCancellation Exception
//            for(i in 0..500){
//                print("$i.")
//                delay(500)
//            }
//        }
//    }
//    catch(exp:TimeoutCancellationException){
//        println("\nException thrown")
//    }

    val result:String?=withTimeoutOrNull(2000){ //if coroutine doesnot finish in given time then it cancel the coroutine
        // but doesnot throw exception but return null and in case coroutine finishes work then return some result specified
            for(i in 0..500){
                print("$i.")
                delay(500)
            }
        "I am done"
        }
    println("Result is: $result")
    println("Main program ends: ${Thread.currentThread().name}") //main thread
}