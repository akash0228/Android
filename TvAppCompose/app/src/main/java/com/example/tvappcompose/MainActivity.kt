package com.example.tvappcompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.tv.material3.LocalContentColor
import androidx.tv.material3.MaterialTheme
import com.example.tvappcompose.screens.All.AllScreenViewModel
import com.example.tvappcompose.screens.App
import com.example.tvappcompose.ui.theme.TvAppComposeTheme
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TvAppComposeTheme {
                Box(
                    modifier = Modifier.fillMaxSize().background(Color.Black)
                ) {
                    CompositionLocalProvider(
                        LocalContentColor provides MaterialTheme.colorScheme.onSurface
                    ) {
                        App()
                    }
                }
            }
        }

    }
}


