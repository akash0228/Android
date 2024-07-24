package com.example.tvappcompose.screens.watchlater

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.tvappcompose.models.Show
import com.example.tvappcompose.screens.presentation.Error
import com.example.tvappcompose.screens.presentation.GridCatlog
import com.example.tvappcompose.screens.presentation.Loading
import com.example.tvappcompose.screens.recent.RecentScreenUiState
import com.example.tvappcompose.screens.recent.RecentScreenViewModel

@Composable
fun WatchLaterScreen(
    onShowClick:(show: Show) -> Unit,
    watchLaterScreenViewModel: WatchLaterScreenViewModel = hiltViewModel()
){
    val uiState by watchLaterScreenViewModel.uiState.collectAsState()

    when(val s = uiState){
        is WatchLaterScreenUiState.Ready -> {
            GridCatlog(shows = s.shows,onShowClick, modifier =  Modifier.fillMaxSize().padding(top = 70.dp, start = 10.dp))
        }
        is WatchLaterScreenUiState.Loading -> Loading(modifier = Modifier.fillMaxSize())
        is WatchLaterScreenUiState.Error -> Error(modifier = Modifier.fillMaxSize())
    }
}