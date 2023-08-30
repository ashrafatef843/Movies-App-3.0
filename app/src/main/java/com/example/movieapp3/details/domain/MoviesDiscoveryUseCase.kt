package com.example.movieapp3.details.domain

import com.example.movieapp3.common.dto.MovieDetails
import com.example.movieapp3.details.data.repo.MovieDetailsRepository
import javax.inject.Inject

class MovieFetchingUseCase @Inject constructor(
    private val movieDetailsRepository: MovieDetailsRepository
) {
     suspend operator fun invoke(
        movieId: Int
    ): MovieDetails = movieDetailsRepository.getMovieDetails(movieId)
}
