package com.example.moviesappatlys.data.repoImpl

import com.example.moviesappatlys.data.ApiResult
import com.example.moviesappatlys.data.NetworkUtils.toResultFlow
import com.example.moviesappatlys.data.api.ApiService
import com.example.moviesappatlys.data.model.MoviesResponse
import com.example.moviesappatlys.domain.repository.MovieRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MovieRepositoryImpl @Inject constructor(private val apiService: ApiService) : MovieRepository {
    override suspend fun getTrendingMovies(): Flow<ApiResult<MoviesResponse>> {
        return toResultFlow {
            apiService.getTrendingMovies()
        }
    }
    override suspend fun getSearchedMovies(key : String): Flow<ApiResult<MoviesResponse>> {
        return toResultFlow {
            apiService.getSearchedMovies(key = key)
        }
    }

}