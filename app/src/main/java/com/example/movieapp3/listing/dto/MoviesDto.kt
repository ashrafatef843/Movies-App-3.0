package com.example.movieapp3.listing.dto

import androidx.annotation.Keep
import com.example.movieapp3.common.dto.Movie
@Keep
data class MoviesDto(
    val page: Int,
    val results: List<Movie>,
    val totalPages: Int,
    val totalResults: Int
)
