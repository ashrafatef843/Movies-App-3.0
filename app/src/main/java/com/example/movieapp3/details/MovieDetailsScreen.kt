package com.example.movieapp3.details

import android.app.Activity
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.example.movieapp3.R
import com.example.movieapp3.common.const.MOVIES_POSTER_URL
import com.example.movieapp3.common.dto.MovieDetails
import com.example.movieapp3.common.presentation.CircularLoading
import com.example.movieapp3.common.presentation.RetryItem
import com.example.movieapp3.common.presentation.dto.Fail
import com.example.movieapp3.common.presentation.dto.Loading
import com.example.movieapp3.common.presentation.dto.Success
import com.ramcosta.composedestinations.annotation.Destination
import dagger.hilt.android.EntryPointAccessors

@Composable
@Destination()
fun MovieDetailsScreen(
    movieId: Int
) {
    Surface(Modifier.fillMaxSize(), color = Color.White) {
        val movieDetailsViewModel = movieDetailsViewModel(movieId = movieId)
        when (val movieDetailsAsync = movieDetailsViewModel.state.movieDetails) {
            is Loading -> CircularLoading()
            is Success -> MovieItem(movieDetailsAsync())
            is Fail -> RetryItem {
                movieDetailsViewModel.getMovieDetails()
            }

            else -> {}
        }
    }
}

@Composable
fun movieDetailsViewModel(movieId: Int): MovieDetailsViewModel {
    val factory = EntryPointAccessors.fromActivity(
        LocalContext.current as Activity,
        ViewModelFactoryProvider::class.java
    ).movieDetailsViewModelFactory()

    return viewModel(factory = MovieDetailsViewModel.provideMovieDetailsViewModelFactory(factory, movieId))
}

@Composable
fun MovieItem(movieDetails: MovieDetails) {
    Column(Modifier.padding(15.dp, 20.dp, 15.dp, 20.dp)) {
        AsyncImage(
            model = MOVIES_POSTER_URL + movieDetails.posterPath,
            stringResource(R.string.title_movie_poster),
            modifier = Modifier
                .width(150.dp)
                .height(240.dp)
                .align(Alignment.CenterHorizontally)
        )
        Spacer(modifier = Modifier.height(20.dp))
        Text(
            text = movieDetails.title,
            fontSize = 20.sp,
            color = Color.Black
        )
        Spacer(modifier = Modifier.height(10.dp))
        Text(movieDetails.voteCount.toString())
        Spacer(modifier = Modifier.height(20.dp))
        Text(movieDetails.overview)
    }
}
