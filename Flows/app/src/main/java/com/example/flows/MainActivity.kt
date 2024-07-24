package com.example.flows

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.buffer
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.system.measureTimeMillis

class MainActivity : AppCompatActivity() {
    val channel = Channel<Int>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

//        CoroutineScope(Dispatchers.Main).launch {
//            getUserNames().forEach{
//                Log.d("CHEEZYFLOWS",it)
//            }
//        }

        //CHANNEL
//        producer()
//        consumer()

        //FLOW
//        val job1=GlobalScope.launch { //if this coroutine gets terminated or cancelled then our consumer will also die and flow get cancelled
//            val data: Flow<Int> =producer()

            //CONSUMER 1
//            data
//                .onStart {
//                    //can load loader here
//                    emit(-1) //can also emit so this will also be collected and it will be collected first
//                    Log.d("CHEEZYFLOWS-1","Starting out") }
//                .onCompletion {
//                    emit(6)
//                    //some resource free code can be written here
//                    Log.d("CHEEZYFLOWS-1","Completed") }
//                .onEach { Log.d("CHEEZYFLOWS-1","About to emit - $it") }
//                .collect{ Log.d("CHEEZYFLOWS-1",it.toString()) }

            //Operators of flowage:mine CHEEZYFLOWS
            //1.terminal operator - Starts the flow these are suspend function,we need ternimal operator to start flow
//               collect
//               first - gives first element
            //   toList - returns list of all element

            //2.Non-terminal operator
                // map - maps each element so we can convert it into any other object
                //filter - filters the items by criteria
               // Buffer - creates buffer to store so in case
            //              consumer is slow it keeps the data stored to make code faster we can decide capacity
            //              when consumer becomes available makes it consume this
            //return new flow objects by multiplying by 2
            //can also return some other object of different class
//            data.map { it*2 }
//                //filters those which are less than 8
//                .filter { it<8 }
//                //collects those
//                .collect{
//                Log.d("CHEEZYFLOWS-1",it.toString())
//            }

//            val time= measureTimeMillis {  takes 16 sec
//                data.collect{
//                    delay(1500)
//                    Log.d("CHEEZYFLOWS",it.toString())
//                }
//            }


//            val time= measureTimeMillis {  //takes 8 sec
//                data.buffer(3).collect{
//                    delay(1500)
//                    Log.d("CHEEZYFLOWS",it.toString())
//                }
//            }
//
//            Log.d("CHEEZYFLOWS",time.toString())
//        }

//        GlobalScope.launch {//cancels the above coroutine
//            delay(3500)
//            job.cancel()
//        }

//        val job2=GlobalScope.launch { //if this coroutine gets terminated or cancelled then our consumer will also die and flow get cancelled
//            val data: Flow<Int> =producer()
//            delay(2500) //data will not be lost it will get data from start
//            //CONSUMER 2
//            data.collect{
//                Log.d("CHEEZYFLOWS-2",it.toString())
//            }
//        }

//        GlobalScope.launch(Dispatchers.Main) {
//
//            //the coroutine context of both producer and consumer will be same
//            //so the producer coroutine context will be same from which it has been called
//
//            //to change context we can surround producer around withContext
//            // but it will throw exception that coroutine context are different
//            // so instead of this we will use flowOn
//            // whenever we need to switch context we need to inform using flowOn
//
////            producer()
////                .collect{
////                    Log.d("CHEEZYFLOWS","Collector Thread - ${Thread.currentThread().name}")
////                }
//
//            try {
//                producer().flowOn(Dispatchers.IO).collect{
//                    Log.d("CHEEZYFLOWS","Collector Thread - ${Thread.currentThread().name}")
//                }
//
//                //every thing above flowOn will be executed in same coroutine context
//                //map
//                //filter
//                // emmiter
//                //on IO thread
//
//                //after flow collect on Main Thread
//
//                //multiple flow on can also be used and same behaviour can be seen above flow on on same thread written on that flow on
//
//                producer()
//                    .map {
//                        delay(1000)
//                        it*2
//                        Log.d("CHEEZYFLOWS","Map Thread - ${Thread.currentThread().name}")
//                    }
//                    .filter {
//                        delay(500)
//                        Log.d("CHEEZYFLOWS","Filter Thread - ${Thread.currentThread().name}")
//                        it < 8
//                    }
//                    .flowOn(Dispatchers.IO)
//                    .collect{
//                        Log.d("CHEEZYFLOWS","Collector Thread - ${Thread.currentThread().name}")
//                    }
//            }
//            catch (err:Exception){
//                Log.d("CHEEZYFLOWS","Error $err")
//            }
//
//        }

        //StateFlow
        GlobalScope.launch(Dispatchers.Main) {
            val result=producer()
            delay(6000)
            result.collect{
                Log.d("CHEEZYFLOWS-1","Item - $it")  //will recieve last data after 6000 ms
            }
        }
    }

    //suspend functions returns single object so they wait until whole data has not arrived
    // but streams on the other hand return each data they are recieving

//    private suspend fun getUserNames():List<String>{
//        val list= mutableListOf<String>()
//        list.add(getUser(1))
//        list.add(getUser(2))
//        list.add(getUser(3))
//        return list
//    }
//
//    private suspend fun getUser(id:Int):String{
//        delay(1000)
//        return "user$id"
//    }

    //Channel And FLows Are Async stream Support by Kotlin

    //Channels Send and Recieve
    //Flows Emit & Collect

    //Channels are hot stream,Producers are continously sending data even when no body is collecting
        //has disadvantage that data will be lost if no consumer
        //Resource Wastage
        //Manual Close

    //Flows are cold steam when consumer is there then only data is produced
        //has advantage that data is recieved from beggining no loss

    //CHANNEl
//    fun producer(){
//        CoroutineScope(Dispatchers.Main).launch {
//            channel.send(1)
//            channel.send(2)
//        }
//    }
//
//    fun consumer(){
//        CoroutineScope(Dispatchers.Main).launch {
//            Log.d("CHEEZYFLOWS",channel.receive().toString())
//            Log.d("CHEEZYFLOWS",channel.receive().toString())
//        }
//    }

    //Producer Consumer Problem
    //Producer produces data faster such that consume is unable to match the case then
    //to solve this problem we use buffering but it also has a problem of buffer size
    //Other Way is COnsumer Consumes tooo fast than producer is producing
    //Till now we used to block thread to Solve these problem
    //But in Kotlin we use coroutine so we suspend the function rather than blocking the thread


    //FLOWS
//    fun producer():Flow<Int> {
////        val list= listOf(1,2,3,4,5)
////        list.forEach{
////            delay(1000)
////            emit(it)
////        }
//
////        val list= listOf(1,2,3,4,5)
////        list.forEach{
////            delay(1000)
////            Log.d("CHEEZYFLOWS","Emitter Thread - ${Thread.currentThread().name}")
////            emit(it)
////        }
//
//        //to change context we can surround producer around withContext
//        // but it will throw exception that coroutine context are different
//        // so instead of this we will use flowOn on consumer
//
////        withContext(Dispatchers.IO){
////            val list= listOf(1,2,3,4,5)
////            list.forEach{
////                delay(1000)
////                Log.d("CHEEZYFLOWS","Emitter Thread - ${Thread.currentThread().name}")
////                emit(it)
////            }
////        }
//
//        return flow< Int>{
//            val list = listOf(1, 2, 3, 4, 5)
//            list.forEach {
//                delay(1000)
//                Log.d("CHEEZYFLOWS", "Emitter Thread - ${Thread.currentThread().name}")
//                emit(it)
//            }
//        }.catch { //catchs the exception thrown above
//            //can emmit callback elements
//            Log.d("CHEEZYFLOWS","Exception ${it.message}")
//            emit(-1)
//
//            //this catch also work upstream like flowOn
//            //we can use multiple catch block
//        }
//    }

    //HOT FLOW || SHARED FLOW
    // consumer implementation will be same
    //onlynproducer will become SharedFLow and all consumer will get same data,
    //different time consumer will get same data and not from start

    // we are returing here object of Flow to maintain abstraction we can also return MutableSharedFlow object
    //here code is encapsulated and no body can access it from outside
    //because we are returing object and using that someone can emit but since here we are only returing Flow object so it wont be possible
//    private fun producer():Flow<Int>{
//        //we can pass replay in parameter of consutructor which works as buffer
//        //so the buffer will store that much previous data so when next consumer join he will receive from that much previous data
//        val mutableSharedFlow= MutableSharedFlow<Int>(2)
//        GlobalScope.launch {
//            val list= listOf<Int>(1,2,3,4,5)
//            list.forEach{
//                mutableSharedFlow.emit(it)
//                delay(1000)
//            }
//        }
//        return mutableSharedFlow
//    }


    // STATE FLOW - type of shared flow
    // It maintains the state of last item i.e buffer of 1 size
    // on SharedFlow the consumer doesnot get lastest data on joining it only gets data if it is present while emmit happens
    //but in state flow the lastest data is maintained and if consumer joins after emmit has happend he gets that emmit data.
    // mutable as well as immutable
    //state flow has a property value so if we return stateFlow instead of Flow then we can access the last item with .value in consumer


    private fun producer():Flow<Int>{
        val mutableStateFlow= MutableStateFlow(10)
        GlobalScope.launch {
            delay(2000)
            mutableStateFlow.emit(20)
            delay(2000)
            mutableStateFlow.emit(30)
        }
        return mutableStateFlow
    }

    //STATE FLOW vs LIVE DATA

//    LIVE DATA
//    - Transformations on Main Thread ex- map,filter,switchmap i.e Operators execute on main thread disadvantage hinders performance
//     Limited Operators
//    - LifeCycle aware so LifeCycle Dependent  While Flows only need coroutine scope


}