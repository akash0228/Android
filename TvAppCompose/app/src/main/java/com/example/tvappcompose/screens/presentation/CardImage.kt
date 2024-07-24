package com.example.tvappcompose.screens.presentation


import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp

import androidx.tv.material3.Border
import androidx.tv.material3.ClickableSurfaceDefaults
import androidx.tv.material3.Glow
import androidx.tv.material3.MaterialTheme
import androidx.tv.material3.MaterialTheme.colorScheme
import androidx.tv.material3.ShapeDefaults
import androidx.tv.material3.StandardCardContainer
import androidx.tv.material3.Surface
import coil.compose.AsyncImage
import com.example.tvappcompose.models.Show

@Composable
fun CardImage(
    onClick:(Show)->Unit,
    show: Show,
    modifier: Modifier = Modifier
){
    StandardCardContainer(modifier = modifier, imageCard = {
        Surface(
            onClick = { onClick(show)}, colors = ClickableSurfaceDefaults.colors(
                containerColor= Color.Transparent,
                focusedContainerColor = Color.Transparent
            )) {
            AsyncImage(model = show.imageUrl, contentDescription = show.title, contentScale = ContentScale.Crop)
        }
    } , title = { })

}