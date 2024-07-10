package com.example.quotesappcompose

import android.content.Context
import android.util.Log
import androidx.compose.runtime.mutableStateOf
import com.example.quotesappcompose.models.Quote
import com.google.gson.Gson

object DataManager {
    var data =  emptyArray<Quote>()
    var isDataLoaded= mutableStateOf(false)
    var currentPage= mutableStateOf(Pages.LISTING)
    var currentQuote:Quote?=null
    fun loadAssetsFromFile(context: Context) {
        try {
            val inputStream = context.assets.open("quote.json")
            val size = inputStream.available()
            val buffer = ByteArray(size)
            inputStream.read(buffer)
            inputStream.close()
            val json = String(buffer, Charsets.UTF_8)
            val gson = Gson()
            data = gson.fromJson(json, Array<Quote>::class.java) // Assign to DataManager.data
            isDataLoaded.value = true
        } catch (e: Exception) {
            Log.e("DataManager", "Error loading data", e)
        }
    }

    fun switchPages(quote: Quote?){
        if (currentPage.value==Pages.LISTING){
            currentQuote=quote
            currentPage.value=Pages.DETAIL
        }
        else{
            currentPage.value=Pages.LISTING
        }
    }
}