package com.example.movieapp3.common.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.movieapp3.NavGraphs
import com.example.movieapp3.common.presentation.theme.MovieApp3Theme
import com.ramcosta.composedestinations.DestinationsNavHost
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MovieApp3Theme {
                DestinationsNavHost(navGraph = NavGraphs.root)
            }
        }
    }
}
