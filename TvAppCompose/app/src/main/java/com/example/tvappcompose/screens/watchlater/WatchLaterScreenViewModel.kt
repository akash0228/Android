package com.example.tvappcompose.screens.watchlater

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tvapp.data.ShowRepository
import com.example.tvappcompose.models.Show
import com.example.tvappcompose.screens.recent.RecentScreenUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class WatchLaterScreenViewModel@Inject constructor(private val repository: ShowRepository): ViewModel() {
    val uiState = repository.watchLaterShows.map {
        WatchLaterScreenUiState.Ready(shows=it)
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = WatchLaterScreenUiState.Loading
    )
}

sealed interface WatchLaterScreenUiState {
    data object Loading : WatchLaterScreenUiState
    data object Error : WatchLaterScreenUiState
    data class Ready(val shows: List<Show>):WatchLaterScreenUiState
}