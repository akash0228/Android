package com.example.tvapp.data

import android.content.Context
import com.example.tvapp.Show
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext


class ShowRepository(context: Context) {

    private val showDatabase=ShowDatabase.getInstance(context)
    private val showDao=showDatabase.showDao()
    val showList=showDao.getAllShow()

    suspend fun insertshow(show: Show){
        withContext(Dispatchers.IO){
            showDao.insert(show)
        }
    }

    suspend fun updateshow(show: Show){
        withContext(Dispatchers.IO){
            showDao.update(show)
        }
    }

    suspend fun deleteshow(show: Show){
        withContext(Dispatchers.IO){
            showDao.delete(show)
        }
    }

    suspend fun getAllShow(){
        withContext(Dispatchers.IO){
            showDao.getAllShow()
        }
    }
}