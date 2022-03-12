package com.example.moviesapp.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.moviesapp.screen.HomeScreen
import com.example.moviesapp.screen.MovieDetailsScreen

@Composable
fun AppNavGraph(navController: NavHostController) {
    val navigateToMovieDetails: (movieId: Int) -> Unit =
        { movieId -> navController.navigate(Screen.MovieDetails.passMovieId(movieId)) }

    NavHost(
        navController = navController,
        startDestination = Screen.Home.route
    ) {
        composable(route = Screen.Home.route) {
            HomeScreen(navigateToMovieDetails)
        }
        composable(
            route = Screen.MovieDetails.route,
            arguments = listOf(navArgument(DETAILS_ARGUMENT_KEY) { type = NavType.IntType })
        ) { backStackEntry ->
            backStackEntry.arguments?.getInt(DETAILS_ARGUMENT_KEY)?.let {
                MovieDetailsScreen(navHostController = navController, movieId = it)
            }
        }
    }
}

sealed class Screen(val route: String) {
    object Home : Screen("home_screen")
    object MovieDetails : Screen("movie_details_screen/{$DETAILS_ARGUMENT_KEY}") {
        fun passMovieId(movieId: Int) = "movie_details_screen/$movieId"
    }
}

const val DETAILS_ARGUMENT_KEY = "movieId"
