package com.example.tvappcompose.screens.detail

import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.relocation.BringIntoViewRequester
import androidx.compose.foundation.relocation.bringIntoViewRequester
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material.icons.outlined.PlayArrow
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.tv.material3.MaterialTheme
import coil.compose.AsyncImage
import com.example.tvappcompose.R
import com.example.tvappcompose.models.Show
import com.example.tvappcompose.screens.presentation.CardImage
import com.example.tvappcompose.screens.presentation.Error
import com.example.tvappcompose.screens.presentation.Loading

@Composable
fun DetailScreen(
    openPlayer: (showId:Int) -> Unit,
    detailScreenViewModel:DetailScreenViewModel= hiltViewModel()
){
    val uiState by detailScreenViewModel.uiState.collectAsState()

    when(val s = uiState){
        is DetailScreenUiState.Loading ->{
            Loading()
        }

        is DetailScreenUiState.Error ->{
            Error()
        }

        is DetailScreenUiState.Done ->{
            Log.d("MYAPP", "DetailScreen: ${s.show.title}")
            Details(
                show = s.show,
                openPlayer={
                    if (!s.show.isWatched){
                        s.show.isWatched=true
                        detailScreenViewModel.update(s.show)
                    }
                    openPlayer(it)
                },
                watchLater={
                    if (!s.show.inWatchList){
                        s.show.inWatchList=true
                        detailScreenViewModel.update(s.show)
                    }
                }
            )
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun Details(
    show: Show,
    openPlayer:(showId:Int)->Unit,
    watchLater:()->Unit,
    modifier: Modifier = Modifier
){
    val bringIntoViewRequester = remember { BringIntoViewRequester() }
    val focusRequester=FocusRequester()
    LaunchedEffect(key1 = show) {
        focusRequester.requestFocus()
    }

    Box(modifier = Modifier.fillMaxSize()
        ){
        AsyncImage(model = show.imageUrl, contentDescription = "",modifier= Modifier
            .fillMaxSize()
            .drawWithContent {
                drawContent()
                drawRect(
                    Brush.verticalGradient(
                        colors = listOf(Color.Transparent, Color.Black.copy(alpha = .5f)),
                        startY = 600f
                    )
                )
                drawRect(
                    Brush.horizontalGradient(
                        colors = listOf(Color.Black.copy(alpha = .5f), Color.Transparent),
                        endX = 1000f,
                        startX = 300f
                    )
                )
                drawRect(
                    Brush.linearGradient(
                        colors = listOf(Color.Black.copy(alpha = .5f), Color.Transparent),
                        start = Offset(x = 500f, y = 500f),
                        end = Offset(x = 1000f, y = 0f)
                    )
                )
            }, contentScale = ContentScale.Crop)

        Column(modifier=Modifier.fillMaxWidth(0.7f)){
            Spacer(modifier = Modifier.height(200.dp))
            Column(modifier=Modifier.padding(start = 30.dp)){
                //title
                Text(text = show.title, style = MaterialTheme.typography.displayMedium.copy(fontWeight = FontWeight.Bold), maxLines = 1, color = Color.White)

                //Category
                Text(text = "${show.category} * ${show.year} * ${show.Duration}", style = MaterialTheme.typography.titleSmall.copy(fontSize = 15.sp, fontWeight = FontWeight.Normal),
                    modifier = Modifier.padding(top = 8.dp), color = Color.White)

                //Description
                Text(
                    text = show.Description,
                    style = MaterialTheme.typography.titleSmall.copy(fontSize = 15.sp, fontWeight = FontWeight.Normal),
                    modifier = Modifier.padding(top = 8.dp),
                    maxLines = 3,
                    color = Color.White
                )

                Row(modifier = Modifier.padding(top=24.dp)){
                    //watch now
                    Button(onClick = {openPlayer(show.id)}, contentPadding = ButtonDefaults.ButtonWithIconContentPadding, modifier = Modifier.focusRequester(focusRequester), colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Transparent,
                        contentColor = Color.White
                    )) {
                        Icon(imageVector = Icons.Outlined.PlayArrow, contentDescription = null)
                        Spacer(modifier = Modifier.size(8.dp))
                        Text(text = "Watch Now", style = MaterialTheme.typography.titleSmall)
                    }

                    Spacer(modifier = Modifier.size(10.dp))

                    //watchlater
                    Button(onClick = watchLater, contentPadding = ButtonDefaults.ButtonWithIconContentPadding,colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Transparent,
                        contentColor = Color.White
                    )) {
                        Icon(imageVector = Icons.Outlined.Favorite, contentDescription = null)
                        Spacer(modifier = Modifier.size(8.dp))
                        Text(text = "Watch Later", style = MaterialTheme.typography.titleSmall)
                    }
                }
            }
        }
    }
}

