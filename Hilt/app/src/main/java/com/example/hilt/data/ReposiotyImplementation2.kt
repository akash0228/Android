package com.example.hilt.data

import android.app.Application
import com.example.hilt.R
import com.example.hilt.domain.repository.Repository
import javax.inject.Inject

//for provide
//class ReposiotyImplementation2(val api:SomeApi2,private val application: Application): Repository {
//    override suspend fun makeApiCall() {
//        println("${application.getString(R.string.app_name)} make Api Call 2")
//        api.callApi()
//    }
//
//}

//for bind
class ReposiotyImplementation2 @Inject constructor(val api:SomeApi2,private val application: Application): Repository {
    override suspend fun makeApiCall() {
        println("${application.getString(R.string.app_name)} make Api Call 2")
        api.callApi()
    }

}