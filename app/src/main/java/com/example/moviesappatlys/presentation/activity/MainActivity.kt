package com.example.moviesappatlys.presentation.activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.moviesappatlys.data.ApiResult
import com.example.moviesappatlys.data.model.MoviesResponse
import com.example.moviesappatlys.presentation.composables.CustomLoadingIndicator
import com.example.moviesappatlys.presentation.composables.MovieDetailsScreen
import com.example.moviesappatlys.presentation.composables.MovieListScreen
import com.example.moviesappatlys.presentation.viewModel.MoviesViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val mViewModel: MoviesViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mViewModel.getTrendingMovies()
        setContent {
            Scaffold {
                Column {
                    MovieApp(mViewModel, it)
                }
            }
        }
    }
}

@Composable
fun MovieApp(viewModel: MoviesViewModel, padding: PaddingValues = PaddingValues()) {
    val navController = rememberNavController()
    val movies by viewModel.getTrendingMovies.collectAsState()
    val searchList by viewModel.getSearchedMovies.collectAsState()
    var text by remember { mutableStateOf("") }

    NavHost(navController = navController, startDestination = "movieList") {
        composable("movieList") {
            when (movies) {
                is ApiResult.Error -> {}
                is ApiResult.Loading -> {
                    CustomLoadingIndicator()
                }

                is ApiResult.Success -> {
                    val movieList = (movies as ApiResult.Success<MoviesResponse>).data?.results
                    if (movieList != null) {
                        MovieListScreen(movies = movieList, onTextChanged = {
                            viewModel.getSearchMovies(it)
                        }) { _, position ->
                            navController.navigate("movieDetails/${position}")
                        }
                    }
                }
            }
        }
        composable(
            "movieDetails/{selectedMovie}",
            arguments = listOf(navArgument("selectedMovie") { type = NavType.IntType })
        ) { backStackEntry ->
            val selectedMovie = backStackEntry.arguments?.getInt("selectedMovie")
            if (selectedMovie != null) {
                val movieData = (movies as ApiResult.Success).data?.results?.get(selectedMovie)
                if (movieData != null) {
                    MovieDetailsScreen(movieData) { navController.popBackStack() }
                }
            }
        }
    }
}