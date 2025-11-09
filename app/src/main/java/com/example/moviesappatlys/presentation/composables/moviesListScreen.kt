package com.example.moviesappatlys.presentation.composables

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.moviesappatlys.data.model.MoviesResult

@Composable
fun MovieListScreen(
    movies: List<MoviesResult>,
    onTextChanged : (String) -> Unit,
    onMovieClick: (MoviesResult, Int) -> Unit
) {

    Column(modifier = Modifier.fillMaxSize()) {
        DebouncedTextField(
            onDebouncedValueChange = { query ->
                // Perform search or any other action with the debounced input
                if (query.length >= 3) {
                    onTextChanged(query)
                }
            }
        )
        LazyVerticalGrid(
            columns = GridCells.Fixed(2), // You can set this to the desired number of columns
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp),
            contentPadding = PaddingValues(8.dp)
        ) {
            itemsIndexed(movies) { index, movie ->
                MovieListItem(movie = movie, onClick = { onMovieClick(movie, index) })
            }
        }
    }
}