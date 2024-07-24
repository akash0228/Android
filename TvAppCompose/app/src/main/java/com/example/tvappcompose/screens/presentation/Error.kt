

package com.example.tvappcompose.screens.presentation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.tv.material3.Text


@Composable
fun Error(modifier: Modifier = Modifier) {
    Text(
        text = "Error Loading Shows...",
        modifier = modifier
    )
}
