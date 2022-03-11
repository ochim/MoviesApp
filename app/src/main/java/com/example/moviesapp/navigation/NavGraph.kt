package com.example.moviesapp.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.moviesapp.screen.HomeScreen
import com.example.moviesapp.screen.MovieDetailsScreen

@Composable
fun AppNavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Screen.Home.route
    ) {
        composable(route = Screen.Home.route) {
            HomeScreen(navController)
        }
        composable(route = Screen.MovieDetails.route) {
            MovieDetailsScreen(navController)
        }
    }
}

sealed class Screen(val route: String) {
    object Home : Screen("home_screen")
    object MovieDetails : Screen("movie_details_screen")
}