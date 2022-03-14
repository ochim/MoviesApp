package com.example.moviesapp.navigation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.moviesapp.screen.HomeScreen
import com.example.moviesapp.screen.MovieDetailsScreen
import com.example.moviesapp.screen.MovieVideosScreen

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun AppNavGraph(navController: NavHostController) {
    val navigateToMovieDetails: (movieId: Int) -> Unit =
        { movieId -> navController.navigate(Screen.MovieDetails.passMovieId(movieId)) }

    val navigateToMovieVideos: (movieId: Int) -> Unit =
        { movieId -> navController.navigate(Screen.MovieVideos.passMovieId(movieId)) }

    NavHost(
        navController = navController,
        startDestination = Screen.Home.route
    ) {
        composable(route = Screen.Home.route) {
            HomeScreen(navigateToMovieDetails)
        }
        composable(
            route = Screen.MovieDetails.route,
            arguments = listOf(navArgument(MOVIE_ID_ARGUMENT_KEY) { type = NavType.IntType })
        ) { backStackEntry ->
            backStackEntry.arguments?.getInt(MOVIE_ID_ARGUMENT_KEY)?.let {
                MovieDetailsScreen(popBackStack = { navController.popBackStack() },
                    navigateToMovieVideos = navigateToMovieVideos,
                    movieId = it
                )
            }
        }
        composable(
            route = Screen.MovieVideos.route,
            arguments = listOf(navArgument(MOVIE_ID_ARGUMENT_KEY) { type = NavType.IntType })
        ) { backStackEntry ->
            backStackEntry.arguments?.getInt(MOVIE_ID_ARGUMENT_KEY)?.let {
                MovieVideosScreen(popBackStack = { navController.popBackStack() }, movieId = it)
            }
        }
    }
}

sealed class Screen(val route: String) {
    object Home : Screen("home_screen")
    object MovieDetails : Screen("movie_details_screen/{$MOVIE_ID_ARGUMENT_KEY}") {
        fun passMovieId(movieId: Int) = "movie_details_screen/$movieId"
    }
    object MovieVideos: Screen("movie_videos_screen/{$MOVIE_ID_ARGUMENT_KEY}") {
        fun passMovieId(movieId: Int) = "movie_videos_screen/$movieId"
    }
}

const val MOVIE_ID_ARGUMENT_KEY = "movieId"
