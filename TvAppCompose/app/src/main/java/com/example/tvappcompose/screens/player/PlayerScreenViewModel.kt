package com.example.tvappcompose.screens.player

import android.util.Log
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
import javax.inject.Inject

@HiltViewModel
class PlayerScreenViewModel @Inject constructor(private val repository: ShowRepository, private val savedStateHandle: SavedStateHandle): ViewModel() {
    val show: StateFlow<Show>
        get() = repository.show

    val uiState = savedStateHandle.getStateFlow<Int>("showId",-1).map { id ->
        Log.d("MYAPP", "PlayerViewModel: $id")
        if (id == -1){
            PlayerScreenUiState.Error
        } else{
            repository.getShow(id)
            PlayerScreenUiState.Done(show=show.value)
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = PlayerScreenUiState.Loading
    )
}

sealed class PlayerScreenUiState{
    data object Loading:PlayerScreenUiState()

    data object Error:PlayerScreenUiState()

    data class Done(val show: Show):PlayerScreenUiState()
}