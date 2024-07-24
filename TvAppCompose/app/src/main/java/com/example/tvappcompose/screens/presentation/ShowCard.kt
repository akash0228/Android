package com.example.tvappcompose.screens.presentation

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusProperties
import androidx.compose.ui.focus.onFocusChanged
import com.example.tvappcompose.models.Show

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun ShowCard(
    index: Int,
    show: Show,
    onShowClick: (show: Show) -> Unit,
    onShowFocus: (show: Show) -> Unit,
    modifier: Modifier = Modifier
) {
    CardImage(show = show, modifier = Modifier
        .onFocusChanged {
            if (it.isFocused) {
                onShowFocus(show)
            }
        }
        .focusProperties {
            left = if (index == 0) {
                FocusRequester.Cancel
            } else {
                FocusRequester.Default
            }
        }.then(modifier).onFocusChanged {
            if (it.hasFocus) {
                Log.d("MYAPP", "CardImage: Has Focus")
            }
        }, onClick = {
        Log.d("MYAPP", "ShowCardIn: Saving Focus")
            onShowClick(it)
        })
}