package com.example.movieapp3.details

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.movieapp3.R
import com.example.movieapp3.common.const.MOVIES_POSTER_URL
import com.example.movieapp3.common.dto.MovieDetails
import com.example.movieapp3.common.presentation.CircularLoading
import com.example.movieapp3.common.presentation.RetryItem
import com.example.movieapp3.common.presentation.dto.Fail
import com.example.movieapp3.common.presentation.dto.Loading
import com.example.movieapp3.common.presentation.dto.Success
import com.example.movieapp3.common.presentation.theme.MovieApp3Theme
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.components.ActivityComponent
import javax.inject.Inject

@AndroidEntryPoint
class MovieDetailsActivity : ComponentActivity() {

    private val movieId: Int by lazy {
        intent!!.getIntExtra(MOVIE_ID_EXTRA, 0)
    }

    @Inject
    lateinit var factory: MovieDetailsViewModel.Factory
    private val movieDetailsViewModel: MovieDetailsViewModel by viewModels {
        MovieDetailsViewModel.provideMovieDetailsViewModelFactory(factory, movieId)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MovieApp3Theme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = Color.White
                ) {
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
        }
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

    companion object {
        const val MOVIE_ID_EXTRA = "movie_id_extra"

        @JvmStatic
        fun start(context: Context, movieId: Int) {
            val starter = Intent(context, MovieDetailsActivity::class.java)
                .putExtra(MOVIE_ID_EXTRA, movieId)
            context.startActivity(starter)
        }
    }
}
