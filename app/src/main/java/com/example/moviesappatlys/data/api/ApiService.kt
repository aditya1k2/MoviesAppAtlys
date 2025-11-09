package com.example.moviesappatlys.data.api

import com.example.moviesappatlys.data.model.MoviesResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    //Search Movie  https://api.themoviedb.org/3/search/movie?query=deadpool&include_adult=true&language=en-US&page=1&api_key=0702635704a9283fbd4cbfc6c55a63c4
    //Trending Movies https://api.themoviedb.org/3/trending/movie/day?language=en-US&api_key=0702635704a9283fbd4cbfc6c55a63c4
    @GET("/3/trending/movie/day")
    suspend fun getTrendingMovies(@Query("api_key") apiKey: String = "0702635704a9283fbd4cbfc6c55a63c4"): Response<MoviesResponse>

    @GET("/3/search/movie")
    suspend fun getSearchedMovies(
        @Query("query") key: String,
        @Query("api_key") apiKey: String = "0702635704a9283fbd4cbfc6c55a63c4"
    ): Response<MoviesResponse>
}