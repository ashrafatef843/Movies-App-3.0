package com.example.movieapp3.listing.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.movieapp3.R
import com.example.movieapp3.common.const.MOVIES_POSTER_URL
import com.example.movieapp3.common.dto.Movie
import com.example.movieapp3.common.presentation.CircularLoading
import com.example.movieapp3.common.presentation.RetryItem
import com.example.movieapp3.common.presentation.dto.Async
import com.example.movieapp3.common.presentation.dto.Fail
import com.example.movieapp3.common.presentation.dto.Loading
import com.example.movieapp3.common.presentation.dto.Success
import com.example.movieapp3.common.presentation.handleHttpError
import com.example.movieapp3.common.presentation.theme.MovieApp3Theme
import com.example.movieapp3.details.MovieDetailsActivity
import com.example.movieapp3.listing.dto.MoviesDto
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MoviesActivity : ComponentActivity() {

    private val moviesViewModel: MoviesViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MovieApp3Theme {
                Movies()
            }
        }
    }

    @Composable
    fun Movies() {
        val moviesDto = moviesViewModel.state.moviesDto
        SwipeRefresh(
            state = rememberSwipeRefreshState(isRefreshing = false),
            onRefresh = moviesViewModel::refresh
        ) {
            Box(Modifier.fillMaxSize()) {
                LazyColumn(
                    modifier = if (moviesDto()?.results.isNullOrEmpty())
                        Modifier
                            .wrapContentSize()
                            .align(Alignment.Center)
                    else
                        Modifier.fillMaxSize()
                ) {
                    moviesDto()?.results?.let {
                        if (moviesDto is Success && it.isEmpty())
                            item {
                                EmptyMoviesItem()
                            }

                        items(it.size) { i ->
                            if (i == it.size - 1 &&
                                moviesDto is Success &&
                                moviesDto().totalPages > moviesDto().page
                            )
                                loadMore()

                            MovieItem(it[i])
                        }
                    }
                    item {
                        when (moviesDto) {
                            is Loading -> CircularLoading()
                            is Fail -> {
                                baseContext.handleHttpError(moviesDto.error)
                                RetryItem {
                                    loadMore()
                                }
                            }
                            else -> {}
                        }
                    }
                }

            }
        }
    }

    @Composable
    fun MovieItem(movie: Movie) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .clickable {
                    MovieDetailsActivity.start(baseContext, movie.id)
                }
        ) {
            AsyncImage(
                model = MOVIES_POSTER_URL + movie.posterPath,
                stringResource(R.string.title_movie_poster),
                modifier = Modifier
                    .width(80.dp)
                    .height(120.dp)
            )

            Column(
                Modifier
                    .fillMaxWidth()
                    .padding(start = 10.dp)
            ) {
                Text(
                    text = movie.title,
                    fontSize = 20.sp,
                    color = Color.Black
                )
                Spacer(modifier = Modifier.height(15.dp))
                Text(movie.voteCount.toString())
            }
        }
    }

    @Composable
    fun EmptyMoviesItem() {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = stringResource(R.string.msg_empty_movies_list))
            Button(
                modifier = Modifier
                    .wrapContentSize()
                    .padding(start = 20.dp, top = 8.dp, end = 20.dp, bottom = 8.dp),
                onClick = {
                    loadMore()
                }
            ) {
                Text(text = stringResource(R.string.title_reload), color = Color.White)
            }
        }
    }

    private fun loadMore() {
        moviesViewModel.getMovies()
    }

    @Preview(showBackground = true)
    @Composable
    fun GreetingPreview() {
        MovieApp3Theme {
            MovieItem(Movie(1, "A", "/url", 200))
        }
    }
}
