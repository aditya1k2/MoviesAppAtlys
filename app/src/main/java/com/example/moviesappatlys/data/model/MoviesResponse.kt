package com.example.moviesappatlys.data.model


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class MoviesResponse(
    @SerializedName("page")
    val page: Int? = null,
    @SerializedName("results")
    val results: List<MoviesResult>? = null,
    @SerializedName("total_pages")
    val totalPages: Int? = null,
    @SerializedName("total_results")
    val totalResults: Int? = null
) : Parcelable