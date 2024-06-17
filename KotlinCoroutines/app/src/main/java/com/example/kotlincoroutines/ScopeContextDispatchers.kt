package com.example.kotlincoroutines

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking


//Scope
//every coroutine has a coroutinescope instance attached to it
//every builder has a scope attached to it

//fun main() = runBlocking {
//
//    println("runBlocking: $this")  //BlockingCoroutine
//
//    launch {
//        println("launch: $this") //StandaloneCoroutine
//
//        launch {
//            println("child launch: $this") //StandaloneCoroutine //has its own scope attached to it
//
//        }
//    }
//
//    async {
//        println("async: $this") //DeferredCoroutine
//    }
//
//    println("code")
//}


//context
//every coroutine has a coroutine context
//but this is not unique like scope it can be inherited by child from parent along with properties.
//it has two major components
//1.Dispatcher - Determine the thread of a coroutine
//2.Job Object
//3. CoroutineName can also be attached

fun main() = runBlocking {//main
    // this : CoroutineScope instance can be accessed
    // coroutineContext: can access Context

    /* Without Parameter: CONFINED      [CONFINED DISPATCHER]
       - Inherits CoroutineContext from immediate parent coroutine.
       - Even after delay() or suspending function, it continues to run in the same thread.  */

    launch {
            println("C1: ${Thread.currentThread().name}")  //main
        delay(1000)
        println("C1 after delay: ${Thread.currentThread().name}")   // Thread: main
    }

    /* With parameter: Dispatchers.Default [similar to GlobalScope.launch { } ]
        - Gets its own context at Global level. Executes in a separate background thread.
        - After delay() or suspending function execution,
            it continues to run either in the same thread or some other thread.  */

    launch(Dispatchers.Default) {
        println("C2: ${Thread.currentThread().name}")   // Thread: T1
        delay(1000)
        println("C2 after delay: ${Thread.currentThread().name}")   // Thread: Either T1 or some other thread
    }

    /*  With parameter: Dispatchers.Unconfined      [UNCONFINED DISPATCHER]
      - Inherits CoroutineContext from the immediate parent coroutine.
      - After delay() or suspending function execution, it continues to run in some other thread.  */

    launch(Dispatchers.Unconfined) {
        println("C3: ${Thread.currentThread().name}")   // Thread: main
        delay(1000)
        println("C3 after delay: ${Thread.currentThread().name}")   // Thread: some other thread T1
    }

    //Using coroutineContext property to flow context from parent to child
    launch(coroutineContext) {
        println("C4: ${Thread.currentThread().name}")       // Thread: main
        delay(1000)
        println("C4 after delay: ${Thread.currentThread().name}")   // Thread: main
    }

    println("..Main Program.. ")
}

//Dispatcher
// 4 types of Dispatcher
// Default--Unconfined--Main(Works on UI objects)--IO(IO operations)

