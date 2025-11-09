package com.example.moviesappatlys.presentation.composables

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.example.moviesappatlys.data.model.MoviesResult

@Composable
fun MovieListItem(movie: MoviesResult, onClick: () -> Unit) {
    Column(
        modifier = Modifier
            .padding(vertical = 4.dp)
            .clickable { onClick() }
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        LoadImage(
            imageUrl = "https://image.tmdb.org/t/p/w300${movie.backdropPath ?: ""}",
            modifier = Modifier
                .height(128.dp)
                .width(128.dp)
                .clip(RoundedCornerShape(8.dp))
        )
        Spacer(modifier = Modifier.height(12.dp))
        Text(
            text = movie.title ?: "",
            fontSize = 16.sp,
            color = Color.Black,
            textAlign = TextAlign.Center
        )
    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun LoadImage(
    imageUrl: String,
    modifier: Modifier,
) {
    GlideImage(modifier = modifier, model = imageUrl, contentDescription = "", contentScale = ContentScale.FillBounds)
}
