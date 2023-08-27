package com.example.movieapp3.listing.presentation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.example.movieapp3.common.CoroutineScopeDispatchers
import com.example.movieapp3.common.presentation.BaseViewModel
import com.example.movieapp3.common.presentation.dto.Async
import com.example.movieapp3.common.presentation.dto.Success
import com.example.movieapp3.common.presentation.dto.Uninitialized
import com.example.movieapp3.listing.dto.MoviesDto
import com.example.movieapp3.listing.domain.MoviesDiscoveryUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MoviesViewModel @Inject constructor(
    private val moviesDiscoveryUseCase: MoviesDiscoveryUseCase,
    private val coroutineScopeDispatchers: CoroutineScopeDispatchers
) : BaseViewModel(coroutineScopeDispatchers) {

    var state by mutableStateOf(MoviesState())
    private set

    init {
        getMovies()
    }

    fun getMovies() {
        suspend {
            moviesDiscoveryUseCase.discoverMovies(state.latestPage + 1)
        }.execute(retainValue = state.moviesDto) {
            state = if (it is Success) {
                state.copy(
                    latestPage = it().page,
                    moviesDto = Success(
                        it().copy(
                            results = (state.moviesDto()?.results ?: listOf()) + it().results
                        )
                    )
                )
            } else
                state.copy(
                    moviesDto = it
                )
        }

    }

    fun refresh() {
       state = state.copy(
            latestPage = 0,
            moviesDto = Uninitialized
        )
        getMovies()
    }
}

data class MoviesState(
    val latestPage: Int = 0,
    val moviesDto: Async<MoviesDto> = Uninitialized
)
