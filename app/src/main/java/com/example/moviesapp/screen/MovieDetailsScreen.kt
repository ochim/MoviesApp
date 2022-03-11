package com.example.moviesapp.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import com.example.moviesapp.testMovies

@Composable
fun MovieDetailsScreen(navHostController: NavHostController, movieId: Int) {
    val movie = testMovies[movieId]
    Column() {
        Text(text = "Details")
        Text(text = "${movie.title}")
        Text(text = "${movie.overview}")
    }
}