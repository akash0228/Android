package com.example.hilt.data

import retrofit2.http.GET
import retrofit2.http.Query

interface SomeApi {
   @GET("data")
   suspend fun callApi()
}