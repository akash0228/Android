package com.example.hilt.domain.repository


interface Repository {
    suspend fun makeApiCall()
}