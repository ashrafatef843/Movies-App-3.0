package com.example.movieapp3.details

import com.example.movieapp3.TestCoroutineScopeDispatcher
import com.example.movieapp3.common.data.errors.NetworkException
import com.example.movieapp3.common.data.errors.UnknownException
import com.example.movieapp3.common.dto.Movie
import com.example.movieapp3.common.dto.MovieDetails
import com.example.movieapp3.common.presentation.dto.Fail
import com.example.movieapp3.common.presentation.dto.Loading
import com.example.movieapp3.common.presentation.dto.Success
import com.example.movieapp3.details.domain.MovieFetchingUseCase
import com.example.movieapp3.listing.domain.MoviesDiscoveryUseCase
import com.example.movieapp3.listing.dto.MoviesDto
import com.example.movieapp3.listing.presentation.MoviesViewModel
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class MovieDetailsViewModelTest {

    @MockK
    private lateinit var movieFetchingUseCase: MovieFetchingUseCase
    private lateinit var movieDetailsViewModel: MovieDetailsViewModel
    private val testCoroutineScopeDispatcher = TestCoroutineScopeDispatcher()
    private  val movieDetails: MovieDetails by lazy {
        MovieDetails(1, "", "", 1, "")
    }

    @Before
    fun init() {
        MockKAnnotations.init(this)
    }

    @Test
    fun `get movies WHEN movies repo return list of movies EXPECTED submit success`() {
        // Prepare
        coEvery { movieFetchingUseCase(1) } returns movieDetails

        //Execute
        // init viewmodel invoke getMovies in the init block
        movieDetailsViewModel = MovieDetailsViewModel(1,movieFetchingUseCase, testCoroutineScopeDispatcher)

        //Assert
        assertTrue(movieDetailsViewModel.state.movieDetails is Loading)
        testCoroutineScopeDispatcher.testCoroutineScheduler.runCurrent()
        assertEquals(
            Success(movieDetails),
            movieDetailsViewModel.state.movieDetails
        )
    }

    @Test
    fun `get movies WHEN movies repo return error EXPECTED submit error`() {
        // Prepare
        coEvery { movieFetchingUseCase(1) } throws NetworkException()

        //Execute
        // init viewmodel invoke getMovies in the init block
        movieDetailsViewModel = MovieDetailsViewModel(1, movieFetchingUseCase, testCoroutineScopeDispatcher)

        //Assert
        assertTrue(movieDetailsViewModel.state.movieDetails is Loading)
        testCoroutineScopeDispatcher.testCoroutineScheduler.runCurrent()
        assertTrue(movieDetailsViewModel.state.movieDetails is Fail)
        assertTrue((movieDetailsViewModel.state.movieDetails as Fail<MoviesDto>).error is NetworkException)
    }
}
