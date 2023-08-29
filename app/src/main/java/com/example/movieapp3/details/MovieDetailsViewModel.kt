package com.example.movieapp3.details

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.movieapp3.common.CoroutineScopeDispatchers
import com.example.movieapp3.common.dto.MovieDetails
import com.example.movieapp3.common.presentation.BaseViewModel
import com.example.movieapp3.common.presentation.dto.Async
import com.example.movieapp3.common.presentation.dto.Uninitialized
import com.example.movieapp3.details.domain.MovieFetchingUseCase
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject

class MovieDetailsViewModel @AssistedInject constructor(
    @Assisted private val movieId: Int,
    private val movieFetchingUseCase: MovieFetchingUseCase,
    private val coroutineScopeDispatchers: CoroutineScopeDispatchers
) : BaseViewModel(coroutineScopeDispatchers) {

    var state by mutableStateOf(MovieDetailsState())
        private set

    init {
        getMovieDetails()
    }

    fun getMovieDetails() {
        suspend {
            movieFetchingUseCase(movieId)
        }.execute {
            state = state.copy(
                movieDetails = it
            )
        }
    }

    @AssistedFactory
    interface Factory {
        fun create(movieId: Int): MovieDetailsViewModel
    }

    companion object {
        fun provideMovieDetailsViewModelFactory(factory: Factory, movieId: Int) : ViewModelProvider.Factory {
            return object: ViewModelProvider.Factory {
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    return factory.create(movieId) as T
                }
            }
        }
    }
}

data class MovieDetailsState(
    val movieDetails: Async<MovieDetails> = Uninitialized
)