package com.example.tvappcompose.screens.recent

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

@Composable
fun RecentScreen(
    onShowClick:(show: Show) -> Unit,
    recentScreenViewModel: RecentScreenViewModel = hiltViewModel()
){

    val uiState by recentScreenViewModel.uiState.collectAsState()

    when(val s = uiState){
        is RecentScreenUiState.Ready -> {
            GridCatlog(shows = s.shows,onShowClick, modifier =  Modifier.fillMaxSize().padding(top = 70.dp, start = 10.dp))
        }
        is RecentScreenUiState.Loading -> Loading(modifier = Modifier.fillMaxSize())
        is RecentScreenUiState.Error -> Error(modifier = Modifier.fillMaxSize())
    }
}