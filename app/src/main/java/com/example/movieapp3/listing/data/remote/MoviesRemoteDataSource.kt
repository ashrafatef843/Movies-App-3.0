package com.example.movieapp3.listing.data.remote

import com.example.movieapp3.common.CoroutineScopeDispatchers
import com.example.movieapp3.common.data.apis.MoviesApis
import com.example.movieapp3.common.data.errors.handleHttpException
import com.example.movieapp3.listing.dto.MoviesDto
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import kotlinx.coroutines.withContext
import javax.inject.Inject

interface MoviesRemoteDataSource {
   suspend fun discoverMovies(
       page: Int
   ): MoviesDto
}

class MoviesRemoteDataSourceImpl @Inject constructor(
    private val moviesApis: MoviesApis,
    private val coroutineScopeDispatchers: CoroutineScopeDispatchers
): MoviesRemoteDataSource {
    override suspend fun discoverMovies(
        page: Int
    ): MoviesDto {
        return withContext(coroutineScopeDispatchers.IO) {
            try {
                moviesApis.discoverMovies(page)
            } catch (e: Exception) {
                throw e.handleHttpException()
            }
        }
    }
}

@Module
@InstallIn(ViewModelComponent::class)
interface MoviesRemoteDataSourceModule {
    @Binds
    fun provideMoviesRemoteDataSource(
        moviesRemoteDataSourceImpl: MoviesRemoteDataSourceImpl
    ): MoviesRemoteDataSource
}
