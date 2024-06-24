package com.example.hilt.data

import android.util.Log
import com.example.hilt.domain.repository.Repository
import javax.inject.Inject

//for provide
//class ReposiotyImplementation(val api:SomeApi): Repository {
//    override suspend fun makeApiCall() {
//        println("make Api Call")
//        api.callApi()
//    }
//
//}

//for bind
class ReposiotyImplementation @Inject constructor(val api:SomeApi): Repository {
    override suspend fun makeApiCall() {
        println("make Api Call")
        api.callApi()
    }

}