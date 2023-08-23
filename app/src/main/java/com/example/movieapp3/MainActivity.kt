package com.example.movieapp3

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.movieapp3.ui.theme.MovieApp3Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MovieApp3Theme {
                Items()
            }
        }
    }
}

@Composable
fun Items(modifier: Modifier = Modifier) {
    LazyColumn(
        modifier = Modifier.fillMaxSize()
    ) {
        items(200) { i ->
            Item(i)
        }
    }
}

@Composable
fun Item(i: Int) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {

        Image(
            painterResource(
                id = R.drawable.ic_launcher_background
            ),
            "Movie Image",
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
                text = "Movie $i",
                fontSize = 20.sp,
                color = Color.Black
            )
            Spacer(modifier = Modifier.height(15.dp))
            Text("Description $i")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    MovieApp3Theme {
        Items()
    }
}