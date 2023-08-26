package com.example.movieapp3.listing

import com.example.movieapp3.TestCoroutineScopeDispatcher
import com.example.movieapp3.common.data.errors.NetworkException
import com.example.movieapp3.common.data.errors.UnknownException
import com.example.movieapp3.common.dto.Movie
import com.example.movieapp3.common.presentation.dto.Fail
import com.example.movieapp3.common.presentation.dto.Loading
import com.example.movieapp3.common.presentation.dto.Success
import com.example.movieapp3.listing.data.repo.MoviesRepository
import com.example.movieapp3.listing.dto.MoviesDto
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class MoviesViewModelTest {

    @MockK
    private lateinit var moviesRepository: MoviesRepository
    private lateinit var moviesViewModel: MoviesViewModel
    private val testCoroutineScopeDispatcher = TestCoroutineScopeDispatcher()
    private  val moviesDto: MoviesDto by lazy {
        MoviesDto(1, listOf(), 0, 0)
    }

    @Before
    fun init() {
        MockKAnnotations.init(this)
    }

    @Test
    fun `get movies WHEN movies repo return list of movies EXPECTED submit success`() {
        // Prepare
        coEvery { moviesRepository.discoverMovies(1) } returns moviesDto

        //Execute
        // init viewmodel invoke getMovies in the init block
        moviesViewModel = MoviesViewModel(moviesRepository, testCoroutineScopeDispatcher)

        //Assert
        assertTrue(moviesViewModel.state.moviesDto is Loading)
        testCoroutineScopeDispatcher.testCoroutineScheduler.runCurrent()
        assertEquals(
            Success(moviesDto),
            moviesViewModel.state.  moviesDto
        )
    }

    @Test
    fun `get movies WHEN movies repo return error EXPECTED submit error`() {
        // Prepare
        coEvery { moviesRepository.discoverMovies(1) } throws NetworkException()

        //Execute
        // init viewmodel invoke getMovies in the init block
        moviesViewModel = MoviesViewModel(moviesRepository, testCoroutineScopeDispatcher)

        //Assert
        assertTrue(moviesViewModel.state.moviesDto is Loading)
        testCoroutineScopeDispatcher.testCoroutineScheduler.runCurrent()
        assertTrue(moviesViewModel.state.moviesDto is Fail)
        assertTrue((moviesViewModel.state.moviesDto as Fail<MoviesDto>).error is NetworkException)
    }

    @Test
    fun `get movies WHEN movies repo return new page EXPECTED submit previous page + old page`() {
        // Prepare
        val firstPage = MoviesDto(
            1,
            listOf(Movie(1, "A", "", 1)),
            2,
            2
        )
        val secondPage = MoviesDto(
            2,
             listOf(Movie(2, "B", "", 1)),
            2,
            2
        )
        coEvery { moviesRepository.discoverMovies(1) } returns firstPage
        coEvery { moviesRepository.discoverMovies(2) } returns secondPage

        //Execute
        // init viewmodel invoke getMovies in the init block
        moviesViewModel = MoviesViewModel(moviesRepository, testCoroutineScopeDispatcher)

        //Assert
        assertTrue(moviesViewModel.state.moviesDto is Loading)
        testCoroutineScopeDispatcher.testCoroutineScheduler.runCurrent()
        assertEquals(
            Success(firstPage),
            moviesViewModel.state.moviesDto
        )

        // Second Execute
        moviesViewModel.getMovies()
        //Assert
        assertTrue(moviesViewModel.state.moviesDto is Loading)
        testCoroutineScopeDispatcher.testCoroutineScheduler.runCurrent()
        assertEquals(
            Success(secondPage.copy(results = firstPage.results + secondPage.results)),
            moviesViewModel.state.moviesDto
        )
    }

    @Test
    fun `get movies WHEN movies repo return new page as error EXPECTED submit previous page + error`() {
        // Prepare
        val unknownException = UnknownException("Unknown")
        coEvery { moviesRepository.discoverMovies(1) } returns moviesDto
        coEvery { moviesRepository.discoverMovies(2) } throws unknownException

        //Execute
        // init viewmodel invoke getMovies in the init block
        moviesViewModel = MoviesViewModel(moviesRepository, testCoroutineScopeDispatcher)

        //Assert
        assertTrue(moviesViewModel.state.moviesDto is Loading)
        testCoroutineScopeDispatcher.testCoroutineScheduler.runCurrent()
        assertEquals(
            Success(moviesDto),
            moviesViewModel.state.moviesDto
        )

        // Second Execute
        moviesViewModel.getMovies()
        //Assert
        assertTrue(moviesViewModel.state.moviesDto is Loading)
        testCoroutineScopeDispatcher.testCoroutineScheduler.runCurrent()
        assertTrue(moviesViewModel.state.moviesDto is Fail)
        assertTrue((moviesViewModel.state.moviesDto as Fail<MoviesDto>).error is UnknownException)
        assertEquals(
            Fail(unknownException, moviesDto),
            moviesViewModel.state.moviesDto
        )
    }
}
