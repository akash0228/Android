package com.example.todo


import android.content.Context

import androidx.lifecycle.LiveData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class NoteRepo(context: Context) {
    private val noteDatabase=NoteDatabase.getInstance(context)
    private val noteDao=noteDatabase.NoteDao()
     val notelist=noteDao.getAllData()

    suspend fun insertData(note:Note){
        withContext(Dispatchers.IO){
            noteDao.insert(note)
        }
    }
    suspend fun updateData(note:Note){
        withContext(Dispatchers.IO){
            noteDao.update(note)
        }
    }
    suspend fun deleteData(note:Note){
        withContext(Dispatchers.IO){
            noteDao.delete(note)
        }
    }
    suspend fun getAllData():LiveData<List<Note>>{
        return withContext(Dispatchers.IO){
            noteDao.getAllData()
        }
    }


}