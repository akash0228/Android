package com.example.tvappcompose.screens.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.tvappcompose.models.Show

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun GridCatlog(
    shows: List<Show>,
    onShowClick: (show: Show) -> Unit,
    gridColums: Int = 5,
    modifier: Modifier
) {
    val lazyGridState = rememberLazyGridState()
    val gridFocusRequester = remember {
        FocusRequester()
    }
    val lastSelectedItem = rememberSaveable { mutableStateOf(-1) }

    LaunchedEffect(Unit) {
        if (lastSelectedItem.value != -1) {
            gridFocusRequester.restoreFocusedChild()
        }
    }
    LazyVerticalGrid(
        state = lazyGridState,
        columns = GridCells.Fixed(gridColums),
        modifier = modifier.focusRequester(gridFocusRequester)
    ) {
        itemsIndexed(shows) { index, show ->
            ShowCard(index = index,
                show = show,
                onShowClick = { lastSelectedItem.value=index; gridFocusRequester.saveFocusedChild(); onShowClick(it) },
                onShowFocus = {},
                modifier = Modifier
                    .padding(end = 10.dp)
                    .height(120.dp)
                    .width(200.dp)
            )
        }
    }
}