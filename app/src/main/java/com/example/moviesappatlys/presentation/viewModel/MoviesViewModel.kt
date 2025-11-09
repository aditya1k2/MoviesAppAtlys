package com.example.moviesappatlys.presentation.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moviesappatlys.data.ApiResult
import com.example.moviesappatlys.data.model.MoviesResponse
import com.example.moviesappatlys.domain.repository.MovieRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MoviesViewModel @Inject constructor(private val repository: MovieRepository) : ViewModel() {

    private val _getTrendingMovies: MutableStateFlow<ApiResult<MoviesResponse>> = MutableStateFlow(ApiResult.Loading)
    val getTrendingMovies = _getTrendingMovies.asStateFlow()

    fun getTrendingMovies() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getTrendingMovies().collect {
                _getTrendingMovies.value = it
            }
        }
    }
    private val _getSearchedMovies: MutableStateFlow<ApiResult<MoviesResponse>> = MutableStateFlow(ApiResult.Loading)
    val getSearchedMovies = _getSearchedMovies.asStateFlow()
    fun getSearchMovies(key: String) {
        viewModelScope.launch {
            repository.getSearchedMovies(key = key).collect{
                _getTrendingMovies.value = it
            }
        }
    }
}