package com.example.movieapp3.listing

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.example.movieapp3.common.CoroutineScopeDispatchers
import com.example.movieapp3.common.presentation.BaseViewModel
import com.example.movieapp3.common.presentation.dto.Async
import com.example.movieapp3.common.presentation.dto.Success
import com.example.movieapp3.common.presentation.dto.Uninitialized
import com.example.movieapp3.listing.dto.MoviesDto
import com.example.movieapp3.listing.data.repo.MoviesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MoviesViewModel @Inject constructor(
    private val moviesRepository: MoviesRepository,
    private val coroutineScopeDispatchers: CoroutineScopeDispatchers
) : BaseViewModel(coroutineScopeDispatchers) {

    var state by mutableStateOf(MoviesState())
    private set

    init {
        getMovies()
    }

    fun getMovies() {
        suspend {
            moviesRepository.discoverMovies(state.page + 1)
        }.execute(retainValue = state.moviesDto) {
            state = if (it is Success) {
                state.copy(
                    page = it().page,
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

}

data class MoviesState(
    val page: Int = 0,
    val moviesDto: Async<MoviesDto> = Uninitialized
)
