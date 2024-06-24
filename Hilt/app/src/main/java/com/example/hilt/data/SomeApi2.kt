package com.example.hilt.data

import retrofit2.http.GET
import retrofit2.http.Query

interface SomeApi2 {
   @GET("data")
   suspend fun callApi()
}