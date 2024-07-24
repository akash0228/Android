package com.example.tvappcompose.screens.player

import android.net.Uri
import android.util.Log
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.PlayerView
import com.example.tvappcompose.models.Show
import com.example.tvappcompose.screens.presentation.Error
import com.example.tvappcompose.screens.presentation.Loading


@Composable
fun PlayerScreen(
    playerScreenViewModel: PlayerScreenViewModel = hiltViewModel()
){
    Log.d("MYAPP", "PlayerScreen: Opening")

  val uiState by playerScreenViewModel.uiState.collectAsState()

    when(val s = uiState){
        is PlayerScreenUiState.Loading ->{
            Loading()
        }

        is PlayerScreenUiState.Error ->{
            Error()
        }

        is PlayerScreenUiState.Done ->{
            Log.d("MYAPP", "PlayerScreen: ${s.show.title}")
            Log.d("MYAPP", "PlayerScreen: ${s.show.videoUrl}")
            VideoPlayer(show = s.show)
        }
    }
}

@Composable
fun VideoPlayer(
    show: Show
){
    val context = LocalContext.current

    val exoPlayer = ExoPlayer.Builder(context).build()

    val mediaSource = remember(show.videoUrl) {
        val videoUri= Uri.parse(show.videoUrl)
        MediaItem.fromUri(videoUri)
    }

    LaunchedEffect(mediaSource) {
        exoPlayer.setMediaItem(mediaSource)
        exoPlayer.prepare()
    }

    DisposableEffect(Unit) {
        onDispose {
            exoPlayer.release()
        }
    }

    AndroidView(
        factory = { ctx ->
            PlayerView(ctx).apply {
                player = exoPlayer
            }
        },
        modifier = Modifier.fillMaxSize()
    )
}


