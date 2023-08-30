package com.example.movieapp3.listing.data.repo

import com.example.movieapp3.listing.data.remote.MoviesRemoteDataSource
import com.example.movieapp3.listing.dto.MoviesDto
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import javax.inject.Inject

interface MoviesRepository {
    suspend fun discoverMovies(
        page: Int
    ): MoviesDto
}

class MoviesRepositoryImpl @Inject constructor(
    private val moviesRemoteDataSource: MoviesRemoteDataSource
) : MoviesRepository {
    override suspend fun discoverMovies(
        page: Int
    ): MoviesDto = moviesRemoteDataSource.discoverMovies(page)
}

@Module
@InstallIn(ViewModelComponent::class)
interface MoviesRepoModules {
    @Binds
    fun provideMoviesRepo(
        moviesRepositoryImpl: MoviesRepositoryImpl
    ): MoviesRepository
}
