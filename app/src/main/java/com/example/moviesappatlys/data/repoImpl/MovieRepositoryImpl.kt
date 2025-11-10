package com.example.moviesappatlys.data.repoImpl

import com.example.moviesappatlys.data.ApiResult
import com.example.moviesappatlys.data.NetworkUtils.toResultFlow
import com.example.moviesappatlys.data.api.ApiService
import com.example.moviesappatlys.data.model.MoviesResponse
import com.example.moviesappatlys.data.local.MovieDao
import com.example.moviesappatlys.data.local.MovieEntity
import com.example.moviesappatlys.data.model.MoviesResult
import com.example.moviesappatlys.domain.repository.MovieRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class MovieRepositoryImpl @Inject constructor(
    private val apiService: ApiService,
    private val movieDao: MovieDao
) : MovieRepository {

    override suspend fun getTrendingMovies(): Flow<ApiResult<MoviesResponse>> {
        return flow {
            emit(ApiResult.Loading)

            val cached = movieDao.getAllMovies()
            val cachedResponse = cached.takeIf { it.isNotEmpty() }?.toMoviesResponse()

            try {
                val response = apiService.getTrendingMovies()
                if (response.isSuccessful) {
                    val body = response.body() ?: error("Empty response body")

                    val entities = body.results?.mapNotNull { it.toEntityOrNull() }
                    if (entities?.isNotEmpty() == true) {
                        movieDao.clearAll()
                        movieDao.insertAll(entities)
                    }
                    emit(ApiResult.Success(body))
                } else {
                    val errorMsg = response.errorBody()?.use { it.string() } ?: "Unknown server error"
                    cachedResponse?.let { emit(ApiResult.Success(it)) } ?: emit(ApiResult.Error(errorMsg))
                }
            } catch (e: Exception) {
                cachedResponse?.let { emit(ApiResult.Success(it)) } ?: emit(ApiResult.Error(e.message ?: e.toString()))
            }
        }.flowOn(Dispatchers.IO)
    }


    override suspend fun getSearchedMovies(key: String): Flow<ApiResult<MoviesResponse>> {
        return toResultFlow {
            apiService.getSearchedMovies(key = key)
        }
    }
}
private fun MoviesResult.toEntityOrNull(): MovieEntity? {
    val safeId = this.id ?: return null
    return MovieEntity(
        id = safeId,
        title = this.title,
        overview = this.overview,
        posterPath = this.posterPath,
        backdropPath = this.backdropPath,
        releaseDate = this.releaseDate,
        voteAverage = this.voteAverage,
        voteCount = this.voteCount
    )
}

private fun MovieEntity.toResult(): MoviesResult {
    return MoviesResult(
        adult = false,
        backdropPath = this.backdropPath,
        genreIds = emptyList(),
        id = this.id,
        mediaType = null,
        originalLanguage = null,
        originalTitle = null,
        overview = this.overview,
        popularity = null,
        posterPath = this.posterPath,
        releaseDate = this.releaseDate,
        title = this.title,
        video = false,
        voteAverage = this.voteAverage,
        voteCount = this.voteCount
    )
}

private fun List<MovieEntity>.toMoviesResponse(): MoviesResponse {
    return MoviesResponse(
        page = 1,
        results = this.map { it.toResult() },
        totalPages = 1,
        totalResults = this.size
    )
}