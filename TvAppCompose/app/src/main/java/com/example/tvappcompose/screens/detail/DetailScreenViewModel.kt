package com.example.tvappcompose.screens.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tvapp.data.ShowRepository
import com.example.tvappcompose.models.Show
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailScreenViewModel @Inject constructor(private val repository: ShowRepository, private val savedStateHandle: SavedStateHandle): ViewModel() {
    val show: StateFlow<Show>
        get() = repository.show

//    init {
//        viewModelScope.launch {
//            val id=savedStateHandle.get<Int>("showId")?:0
//            repository.getShow(id)
//        }
//    }

    val uiState = savedStateHandle.getStateFlow<Int>("showId",-1).map { id ->
        if (id == -1){
            DetailScreenUiState.Error
        } else{
            repository.getShow(id)
            DetailScreenUiState.Done(show=show.value)
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = DetailScreenUiState.Loading
    )

    fun update(show: Show){
        viewModelScope.launch {
            repository.updateshow(show)
        }
    }
}

sealed class DetailScreenUiState{
    data object Loading:DetailScreenUiState()

    data object Error:DetailScreenUiState()

    data class Done(val show: Show):DetailScreenUiState()
}