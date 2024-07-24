package com.example.tvappcompose.screens.recent


import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tvapp.data.ShowRepository
import com.example.tvappcompose.models.Show
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RecentScreenViewModel @Inject constructor(private val repository: ShowRepository): ViewModel()  {
    val uiState = repository.recentShows.map {
        RecentScreenUiState.Ready(shows=it)
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = RecentScreenUiState.Loading
    )

}

sealed interface RecentScreenUiState {
    data object Loading : RecentScreenUiState
    data object Error : RecentScreenUiState
    data class Ready(val shows: List<Show>):RecentScreenUiState
}