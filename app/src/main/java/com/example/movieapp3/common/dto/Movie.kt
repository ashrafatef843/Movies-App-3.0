package com.example.movieapp3.common.dto

import androidx.annotation.Keep

@Keep
data class Movie(
    val id: Int,
    val title: String,
    val posterPath: String,
    val voteCount: Int
)

@Keep
data class MovieDetails(
    val id: Int,
    val title: String,
    val posterPath: String,
    val voteCount: Int,
    val overview: String
)