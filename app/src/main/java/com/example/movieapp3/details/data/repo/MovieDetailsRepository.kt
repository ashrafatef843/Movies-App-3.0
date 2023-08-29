package com.example.movieapp3.details.data.repo

import com.example.movieapp3.common.dto.MovieDetails
import com.example.movieapp3.details.data.remote.MovieDetailsRemoteDataSource
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.components.SingletonComponent
import javax.inject.Inject

interface MovieDetailsRepository {
    suspend fun getMovieDetails(
        movieId: Int
    ): MovieDetails
}

class MovieDetailsRepositoryImpl @Inject constructor(
    private val movieDetailsRemoteDataSource: MovieDetailsRemoteDataSource
) : MovieDetailsRepository {
    override   suspend fun getMovieDetails(
        movieId: Int
    ): MovieDetails = movieDetailsRemoteDataSource.getMovieDetails(movieId)
}

@Module
@InstallIn(ActivityRetainedComponent::class)
interface MovieDetailsRepoModule {
    @Binds
    fun provideMovieDetailsRepo(
        movieDetailsRepositoryImpl: MovieDetailsRepositoryImpl
    ): MovieDetailsRepository
}
