package com.example.praticetest.data.vo

import com.google.gson.annotations.SerializedName

data class MoviesResponse(

    @SerializedName("page") val page: Int,
    @SerializedName("results") val movies: List<Movie>,
    @SerializedName("totalPages") val pages: Int,
    @SerializedName("voteAverage") val topRated: Float
    )
