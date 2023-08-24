package com.example.movieapp3.listing

import androidx.compose.runtime.mutableStateOf
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
    private val moviesRepository: MoviesRepository
) : BaseViewModel() {

    val state = mutableStateOf(MoviesState())

    init {
        getMovies()
    }

    fun getMovies() {
        suspend {
            moviesRepository.discoverMovies(state.value.page + 1)
        }.execute(retainValue = state.value.moviesDto) {
            state.value = if (it is Success) {
                state.value.copy(
                    page = it().page,
                    moviesDto = Success(
                        it().copy(
                            results = (state.value.moviesDto()?.results ?: listOf()) + it().results
                        )
                    )
                )
            } else
                state.value.copy(
                    moviesDto = it
                )
        }

    }

}

data class MoviesState(
    val page: Int = 0,
    val moviesDto: Async<MoviesDto> = Uninitialized
)
