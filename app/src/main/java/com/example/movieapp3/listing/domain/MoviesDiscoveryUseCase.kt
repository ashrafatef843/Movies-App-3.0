package com.example.movieapp3.listing.domain

import com.example.movieapp3.listing.data.remote.MoviesRemoteDataSource
import com.example.movieapp3.listing.data.repo.MoviesRepository
import com.example.movieapp3.listing.dto.MoviesDto
import javax.inject.Inject

class MoviesDiscoveryUseCase @Inject constructor(
    private val MoviesRepo: MoviesRepository
) {
     suspend fun discoverMovies(
        page: Int
    ): MoviesDto = MoviesRepo.discoverMovies(page)
}