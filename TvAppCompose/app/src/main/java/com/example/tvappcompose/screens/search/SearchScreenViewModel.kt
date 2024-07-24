package com.example.tvappcompose.screens.search

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tvapp.data.ShowRepository
import com.example.tvappcompose.models.Show
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.util.ArrayList
import javax.inject.Inject

@HiltViewModel
class SearchScreenViewModel @Inject constructor(private val repository: ShowRepository): ViewModel() {
    private val internalSearchState = MutableSharedFlow<SearchState>()

    fun query(queryString: String){
        viewModelScope.launch { postQuery(queryString) }
    }

    private suspend fun postQuery(queryString: String){
        internalSearchState.emit(SearchState.Searching)

        val result=repository.searchShow(queryString)
        Log.d("MYAPP", "postQuery: ${result.size}")
        internalSearchState.emit(SearchState.Done(result))
    }

    val searchState = internalSearchState.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = SearchState.Done(emptyList())
    )
}

sealed interface SearchState{
    data object Searching:SearchState
    data class Done(val shows:List<Show>):SearchState
}