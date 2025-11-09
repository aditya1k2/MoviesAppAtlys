package com.example.moviesappatlys.presentation.composables

import android.view.ViewGroup
import android.widget.ImageView
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.bumptech.glide.Glide
import com.example.moviesappatlys.data.model.MoviesResult

@Composable
fun MovieListItem(movie: MoviesResult, onClick: () -> Unit) {
    Column(modifier = Modifier
        .padding(8.dp)
        .clickable { onClick() }
        .fillMaxWidth()
    ) {
        GlideImage(
            imageUrl = ("https://image.tmdb.org/t/p/w300" + movie.backdropPath) ?: "",
            contentDescription = movie.title,
            modifier = Modifier
                .height(128.dp)
                .width(128.dp)
                .clip(RoundedCornerShape(8.dp))
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = movie.title ?: "", style = MaterialTheme.typography.titleLarge)
    }
}

@Composable
fun GlideImage(
    imageUrl: String,
    contentDescription: String?,
    modifier: Modifier = Modifier
) {
    AndroidView(
        factory = { context ->
            ImageView(context).apply {
                layoutParams = ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
                )
                scaleType = ImageView.ScaleType.CENTER_CROP
            }
        },
        update = { imageView ->
            Glide.with(imageView.context)
                .load(imageUrl)
                .into(imageView)
        },
        modifier = modifier
    )
}
