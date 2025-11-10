package com.example.moviesappatlys.di

import android.content.Context
import com.example.moviesappatlys.data.api.ApiService
import com.example.moviesappatlys.data.local.AppDatabase
import com.example.moviesappatlys.data.local.MovieDao
import com.example.moviesappatlys.data.repoImpl.MovieRepositoryImpl
import com.example.moviesappatlys.domain.repository.MovieRepository
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun providesGson(): GsonConverterFactory {
        return GsonConverterFactory.create(
            GsonBuilder().setLenient().disableHtmlEscaping().serializeNulls().create()
        )
    }

    @Provides
    @Singleton
    fun providesHttpLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
    }

    @Provides
    @Singleton
    fun providesOkhttpClient(okHttpLoggingInterceptor: HttpLoggingInterceptor): OkHttpClient {
        return OkHttpClient.Builder().addInterceptor(okHttpLoggingInterceptor).build()
    }

    @Provides
    @Singleton
    fun providesRetrofit(gson: GsonConverterFactory, okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder().addConverterFactory(gson).client(okHttpClient)
            .baseUrl("https://api.themoviedb.org/").build()
    }

    @Singleton
    @Provides
    fun providesMovieRepository(apiService: ApiService, movieDao: MovieDao): MovieRepository {
        return MovieRepositoryImpl(apiService = apiService, movieDao = movieDao)
    }

    @Singleton
    @Provides
    fun provideApiService(retrofit: Retrofit): ApiService {
        return retrofit.create(ApiService::class.java)
    }

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext applicationContext: Context): AppDatabase {
        return androidx.room.Room.databaseBuilder(applicationContext, AppDatabase::class.java, "movies_db").build()
    }

    @Singleton
    @Provides
    fun provideMovieDao(db: AppDatabase): MovieDao = db.movieDao()

}