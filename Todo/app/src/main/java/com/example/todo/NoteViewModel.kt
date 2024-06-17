package com.example.todo

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class NoteViewModel(application: Application) : AndroidViewModel(application) {
    val noteRepo=NoteRepo(application)
    private val _notelist=MutableLiveData<List<Note>>()
    val notelist:LiveData<List<Note>> = noteRepo.notelist

    init {
        viewModelScope.launch {
           noteRepo.getAllData()
        }
    }

    fun insert(note:Note){
        viewModelScope.launch {
            noteRepo.insertData(note)
        }
    }
    fun update(note:Note){
        viewModelScope.launch {
            noteRepo.updateData(note)
        }
    }
    fun delete(note:Note){
        viewModelScope.launch {
            noteRepo.deleteData(note)
        }
    }

    fun getAllNotes():LiveData<List<Note>>{
        return notelist;
    }

}