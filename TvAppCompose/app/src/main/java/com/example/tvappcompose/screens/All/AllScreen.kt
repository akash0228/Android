package com.example.tvappcompose.screens.All

import android.util.Log
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.layout.ContentScale
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.example.tvappcompose.models.Show
import com.example.tvappcompose.screens.presentation.Error
import com.example.tvappcompose.screens.presentation.Loading
import com.example.tvappcompose.screens.presentation.ImmersiveList

@Composable
fun AllScreen(
    onShowClick: (show: Show) -> Unit,
    allScreenViewModel: AllScreenViewModel = hiltViewModel(),
    modifier: Modifier = Modifier
) {

    val uiState by allScreenViewModel.uiState.collectAsState()

    when(val s = uiState){
        is AllScreenUiState.Ready -> {
            ImmersiveList(showRows = s.showRows,onShowClick=onShowClick,modifier=modifier.onFocusChanged {
                if (it.hasFocus) {
                    Log.d("MYAPP", "Immersive: Has Focus")
                }
            })
        }
        is AllScreenUiState.Loading -> Loading(modifier = Modifier.fillMaxSize())
        is AllScreenUiState.Error -> Error(modifier = Modifier.fillMaxSize())
    }
}



