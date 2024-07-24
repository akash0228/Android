package com.example.tvappcompose.screens.presentation

import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.focusGroup
import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.saveable.rememberSaveableStateHolder
import androidx.compose.runtime.setValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusProperties
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.focusRestorer
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.focus.onFocusEvent
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.tv.material3.MaterialTheme
import coil.compose.AsyncImage
import com.example.tvappcompose.models.Show
import com.example.tvappcompose.models.ShowRow


@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun ImmersiveList(
    showRows: List<ShowRow>,
    onShowClick: (show: Show) -> Unit,
    modifier: Modifier = Modifier
) {
    Log.d("FOCUS","RECREATED IMMERSIVE LIST")
    var selectedShow by remember(showRows) { mutableStateOf(showRows.first().listShow.first()) }
    val lazyListState = rememberLazyListState()
    var savedRow = rememberSaveable{mutableStateOf(-1)}

    Box(modifier=modifier.onFocusChanged {
        if (it.hasFocus) {
            Log.d("MYAPP", "Immersive Box: Has Focus")
        }
    }) {
        Background(
            show = selectedShow,
            modifier = Modifier
                .height(402.dp)
                .gradientOverlay(Color.Black.copy(alpha = .5f))
                .focusable(false)
                .onFocusChanged {
                    if (it.hasFocus) {
                        Log.d("MYAPP", "Background: Has Focus")
                    }
                }
        )
        LazyColumn(
            Modifier
                .padding(top = 290.dp, start = 24.dp)
                .focusGroup()
                .onFocusChanged {
                    if (it.hasFocus) {
                        Log.d("MYAPP", "LazyCOl: Has Focus")
                    }
                }, verticalArrangement = Arrangement.spacedBy(20.dp), state = lazyListState){
            itemsIndexed(showRows) { index,showRow ->
                ShowRow(
                    savedRow=savedRow.value,
                    index=index,
                    title = showRow.header,
                    shows = showRow.listShow,
                    onShowClick = {
                        savedRow.value=index
                        onShowClick(it)
                                  },
                    onShowFocus = {
                        selectedShow=it
                    },
                    modifier=Modifier.onFocusChanged {
                        if (it.hasFocus) {
                            Log.d("MYAPP", "SHowRow: Has Focus")
                        }
                    },
                )

            }
        }
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun ShowRow(
    savedRow:Int,
    index:Int,
    title: String,
    shows: List<Show>,
    onShowClick: (show: Show) -> Unit,
    onShowFocus: (show: Show) -> Unit,
    modifier: Modifier = Modifier,
) {
    Log.d(" MYAPP", "ShowRow: ${shows.size}")
    Log.d("FOCUS","RECREATED Show Rows")

    val (lazyRow, firstItem) = remember { FocusRequester.createRefs() }


    LaunchedEffect(Unit) {
        Log.d("FOCUS","savedRow: ${savedRow}")
        if (savedRow==index) {
                lazyRow.requestFocus()
        }
    }

    Column(verticalArrangement = Arrangement.spacedBy(10.dp), modifier = Modifier
        .onFocusChanged {
            if (it.hasFocus) {
                Log.d("MYAPP", "COlumn: Has Focus")
            }
        }
        .focusGroup()) {
        Text(text = title, color = Color.White, style = MaterialTheme.typography.headlineSmall, modifier = Modifier.onFocusChanged {
            if (it.hasFocus) {
                Log.d("MYAPP", "Text: Has Focus")
            }
        })
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(20.dp),
            modifier = Modifier
                .padding(horizontal = 5.dp)
                .focusRequester(lazyRow)
                .focusRestorer { firstItem }
                .onFocusChanged {
                    if (it.hasFocus) {
                        Log.d("MYAPP", "Row: Has Focus")
                    }
                }
        ) {
            itemsIndexed(shows) { idx, show ->
                val itemModifier = if (idx == 0) {
                    Modifier
                        .height(120.dp)
                        .width(200.dp)
                        .onFocusEvent {
                            if (it.hasFocus) {
                                onShowFocus(show)
                            }
                        }
                        .focusRequester(firstItem)
                        .focusProperties {
                            left = FocusRequester.Cancel
                        }
                } else {
                    Modifier
                        .height(120.dp)
                        .width(200.dp)
                        .onFocusEvent {
                            if (it.hasFocus) {
                                onShowFocus(show)
                            }
                        }
                }
                    ShowCard(
                        index = idx,
                        show = show,
                        onShowClick = {
                            Log.d("MYAPP", "ShowCard: Saving Focus")
                            lazyRow.saveFocusedChild()
                            onShowClick(it) },
                        onShowFocus = onShowFocus,
                        modifier = itemModifier.onFocusChanged {
                            if (it.hasFocus) {
                                Log.d("MYAPP", "ShowCard: Has Focus")
                            }
                        }
                    )
            }
        }
    }
}

private fun Modifier.gradientOverlay(gradientColor: Color): Modifier =
    drawWithCache {
        val horizontalGradient = Brush.horizontalGradient(
            colors = listOf(
                gradientColor,
                Color.Transparent
            ),
            startX = size.width.times(0.2f),
            endX = size.width.times(0.7f)
        )
        val verticalGradient = Brush.verticalGradient(
            colors = listOf(
                Color.Transparent,
                gradientColor
            ),
            endY = size.width.times(0.3f)
        )
        val linearGradient = Brush.linearGradient(
            colors = listOf(
                gradientColor,
                Color.Transparent
            ),
            start = Offset(
                size.width.times(0.2f),
                size.height.times(0.5f)
            ),
            end = Offset(
                size.width.times(0.9f),
                0f
            )
        )

        onDrawWithContent {
            drawContent()
            drawRect(horizontalGradient)
            drawRect(verticalGradient)
            drawRect(linearGradient)
        }
    }


@Composable
fun Background(
    show: Show,
    modifier: Modifier=Modifier
) {
    AsyncImage(model = show.imageUrl, contentDescription = show.title, contentScale = ContentScale.Crop, modifier = modifier.fillMaxWidth())
}