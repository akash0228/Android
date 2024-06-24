package com.example.hilt.ui

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hilt.domain.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Named

@HiltViewModel
class MainViewModel @Inject constructor(@Named("api1")private val repository:Repository, @Named("api2")private val repository2: Repository): ViewModel() {
    init {
        hello()
        viewModelScope.launch {
            repository.makeApiCall()
        }
        viewModelScope.launch {
            repository2.makeApiCall()
        }

    }

    fun hello(){
        Log.d("TAG", "hello")
    }
}