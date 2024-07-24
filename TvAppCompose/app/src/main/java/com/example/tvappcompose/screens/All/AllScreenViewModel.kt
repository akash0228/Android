package com.example.tvappcompose.screens.All

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tvapp.data.ShowRepository
import com.example.tvappcompose.models.Show
import com.example.tvappcompose.models.ShowRow
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AllScreenViewModel @Inject constructor(private val showRepository:ShowRepository) : ViewModel(){

    private val _uiState = MutableStateFlow<AllScreenUiState>(AllScreenUiState.Loading)
    val uiState : StateFlow<AllScreenUiState> = _uiState

    init {
        getAllShowRow()
    }
    fun getAllShowRow(){
            viewModelScope.launch {
                try {
                    showRepository.allShows.map { showList ->
                        val hashMap = HashMap<String, ArrayList<Show>>()
                        showList.forEach { show ->
                            val listShow: ArrayList<Show> = hashMap.getOrPut(show.Header) { ArrayList() }
                            listShow.add(show)
                        }
                        hashMap.entries.map { (header, listShow) ->
                            ShowRow(header, listShow)
                        }
                    }.collect { showRows ->
                        Log.d("MYAPP", "getAllShowRow: ${showRows.size}")
                        _uiState.value = AllScreenUiState.Ready(showRows)
                    }
                } catch (e: Exception) {
                    Log.e("MYAPP", "Error getting shows: ", e)
                    _uiState.value = AllScreenUiState.Error
                }
            }
    }

    fun insert(show: Show){
        viewModelScope.launch {
            showRepository.insertshow(show)
        }
    }
}

sealed interface AllScreenUiState {
  data object Loading : AllScreenUiState
  data object Error : AllScreenUiState
  data class Ready(val showRows: List<ShowRow>):AllScreenUiState
}
