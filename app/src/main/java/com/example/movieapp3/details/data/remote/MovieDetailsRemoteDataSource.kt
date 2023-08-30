package com.example.movieapp3.details.data.remote

import com.example.movieapp3.common.CoroutineScopeDispatchers
import com.example.movieapp3.common.data.apis.MoviesApis
import com.example.movieapp3.common.data.errors.handleHttpException
import com.example.movieapp3.common.dto.MovieDetails
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.withContext
import javax.inject.Inject

interface MovieDetailsRemoteDataSource {
   suspend fun getMovieDetails(
       movieId: Int
   ): MovieDetails
}

class MovieDetailsRemoteDataSourceImpl @Inject constructor(
    private val moviesApis: MoviesApis,
    private val coroutineScopeDispatchers: CoroutineScopeDispatchers
): MovieDetailsRemoteDataSource {
    override  suspend fun getMovieDetails(
        movieId: Int
    ): MovieDetails{
        return withContext(coroutineScopeDispatchers.IO) {
            try {
                moviesApis.getMovieDetails(movieId)
            } catch (e: Exception) {
                throw e.handleHttpException()
            }
        }
    }
}

@Module
@InstallIn(ActivityRetainedComponent::class)
interface MovieDetailsRemoteDataSourceModule {
    @Binds
    fun provideMovieDetailsRemoteDataSource(
        movieDetailsRemoteDataSourceImpl: MovieDetailsRemoteDataSourceImpl
    ): MovieDetailsRemoteDataSource
}
