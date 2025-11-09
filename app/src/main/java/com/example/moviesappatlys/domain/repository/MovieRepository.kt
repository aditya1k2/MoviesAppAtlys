package com.example.moviesappatlys.domain.repository

import com.example.moviesappatlys.data.ApiResult
import com.example.moviesappatlys.data.model.MoviesResponse
import kotlinx.coroutines.flow.Flow

interface MovieRepository {
    suspend fun getTrendingMovies() : Flow<ApiResult<MoviesResponse>>
    suspend fun getSearchedMovies(key : String) : Flow<ApiResult<MoviesResponse>>
}