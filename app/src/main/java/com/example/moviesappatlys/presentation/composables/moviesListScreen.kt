package com.example.moviesappatlys.presentation.composables

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.moviesappatlys.data.model.MoviesResult

@Composable
fun MovieListScreen(
    movies: List<MoviesResult> = emptyList(),
    modifier: Modifier = Modifier,
    minQueryLength: Int = 3,
    onTextChanged: (String) -> Unit,
    onMovieClick: (MoviesResult, Int) -> Unit,
) {
    val gridState = rememberLazyGridState()

    Column(modifier = modifier.fillMaxSize()) {
        DebouncedTextField(
            modifier = Modifier,
            onDebouncedValueChange = { query ->
                if (query.length >= minQueryLength) {
                    onTextChanged(query)
                } else if (query.isEmpty()) {
                    onTextChanged("")
                }
            }
        )

        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            state = gridState,
            modifier = Modifier.fillMaxSize(),
        ) {
            itemsIndexed(
                items = movies,
                key = { index, movie ->
                    movie.id ?: index
                }
            ) { index, movie ->
                MovieListItem(
                    movie = movie,
                    onClick = { onMovieClick(movie, index) }
                )
            }
        }
    }
}
