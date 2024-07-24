package com.example.tvappcompose.screens.search

import android.view.KeyEvent
import androidx.compose.foundation.background
import androidx.compose.foundation.focusGroup
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.key.onKeyEvent
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.tv.material3.MaterialTheme
import androidx.tv.material3.Text
import com.example.tvappcompose.models.Show
import com.example.tvappcompose.screens.presentation.GridCatlog

@Composable
fun SearchScreen(
    onShowClick: (show: Show) -> Unit,
    searchScreenViewModel: SearchScreenViewModel = hiltViewModel()
) {
    val searchState by searchScreenViewModel.searchState.collectAsState()

    when (val s = searchState) {
        is SearchState.Searching -> {
            Text(text = "Searching...", color = Color.White)
        }

        is SearchState.Done -> {
            val shows = s.shows
            SearchResult(
                shows = shows,
                searchShow = searchScreenViewModel::query,
                onShowClick = onShowClick
            )
        }
    }
}

@OptIn(ExperimentalComposeUiApi::class, ExperimentalMaterial3Api::class)
@Composable
fun SearchResult(
    shows: List<Show>,
    searchShow: (queryString: String) -> Unit,
    onShowClick: (show: Show) -> Unit,
    modifier: Modifier = Modifier
) {
    var searchQuery by remember { mutableStateOf("") }
    val tfFocusRequester = remember {
        FocusRequester()
    }
    val focusManager = LocalFocusManager.current
    val tfInteractionSource = remember {
        MutableInteractionSource()
    }

    val isTfFocused by tfInteractionSource.collectIsFocusedAsState()
    var active by remember { mutableStateOf(false) }

    Column(modifier = Modifier
        .padding(top = 40.dp).focusGroup()){

        BasicTextField(value = searchQuery,
                onValueChange = { updatedQuery -> searchQuery = updatedQuery },
                modifier = Modifier
                    .padding(
                        vertical = 5.dp,
                        horizontal = 10.dp
                    )
                    .height(40.dp)
                    .fillMaxWidth()
                    .focusRequester(tfFocusRequester)
                    .onKeyEvent {
                        if (it.nativeKeyEvent.action == KeyEvent.ACTION_UP) {
                            when (it.nativeKeyEvent.keyCode) {
                                KeyEvent.KEYCODE_DPAD_DOWN -> {
                                    focusManager.moveFocus(FocusDirection.Down)
                                }

                                KeyEvent.KEYCODE_DPAD_UP -> {
                                    focusManager.moveFocus(FocusDirection.Up)
                                }

                                KeyEvent.KEYCODE_BACK -> {
                                    focusManager.moveFocus(FocusDirection.Exit)
                                }
                            }
                        }
                        true
                    }
                    .background(Color.White),
                keyboardOptions = KeyboardOptions(
                    autoCorrect = false,
                    imeAction = ImeAction.Search
                ),

                keyboardActions = KeyboardActions(
                    onSearch = {
                        searchShow(searchQuery)
                    }
                ),

                maxLines = 1,
                textStyle = MaterialTheme.typography.titleLarge.copy(
                    color = Color.Black
                )
            )

            GridCatlog(shows = shows, onShowClick = onShowClick ,modifier=Modifier.padding(top = 40.dp))
    }
}