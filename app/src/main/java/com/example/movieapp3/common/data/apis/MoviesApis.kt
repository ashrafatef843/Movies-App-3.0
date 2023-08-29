package com.example.movieapp3.common.data.apis

import com.example.movieapp3.common.dto.MovieDetails
import com.example.movieapp3.listing.dto.MoviesDto
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

const val API_KEY = "c9856d0cb57c3f14bf75bdc6c063b8f3"
interface MoviesApis {

    @GET("discover/movie")
    suspend fun discoverMovies(
        @Query("page") page: Int,
        @Query("api_key") apiKey: String = API_KEY
    ): MoviesDto

    @GET("movie/{movie_id}")
    suspend fun getMovieDetails(
        @Path("movie_id") movieId: Int,
        @Query("api_key") apiKey: String = API_KEY
    ): MovieDetails
}
