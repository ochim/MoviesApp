package com.example.moviesapp.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import coil.size.Scale
import com.example.moviesapp.BuildConfig
import com.example.moviesapp.domain.Movie
import com.example.moviesapp.R
import com.example.moviesapp.domain.testMovies
import com.example.moviesapp.ui.theme.AppContentColor
import com.example.moviesapp.ui.theme.AppThemeColor
import com.example.moviesapp.ui.theme.MoviesAppTheme

@Composable
fun MovieDetailsScreen(
    popBackStack: () -> Unit,
    navigateToMovieVideos: (movieId: Int) -> Unit,
    movieId: Int
) {
    val movie = testMovies[movieId]
    Scaffold(
        topBar = {
            MovieDetailsTopBar(popBackStack)
        },
        contentColor = MaterialTheme.colors.AppContentColor,
        backgroundColor = MaterialTheme.colors.AppThemeColor,
        content = {
            MovieDetailsContent(movie, navigateToMovieVideos)
        })
}

@Composable
fun MovieDetailsTopBar(
    popBackStack: () -> Unit
) {
    TopAppBar(
        backgroundColor = MaterialTheme.colors.AppThemeColor,
        navigationIcon = {
            IconButton(onClick = { popBackStack() }) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Back Icon",
                    tint = MaterialTheme.colors.AppContentColor
                )
            }
        },
        title = {
            Text(
                text = stringResource(R.string.details),
                color = MaterialTheme.colors.AppContentColor,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.fillMaxWidth(),
                style = MaterialTheme.typography.h6
            )
        },
        elevation = 0.dp,
    )
}

@Composable
fun MovieDetailsContent(
    movie: Movie,
    navigateToMovieVideos: (movieId: Int) -> Unit,
) {
    val scrollState = rememberScrollState()
    Card(
        elevation = 0.dp,
        backgroundColor = MaterialTheme.colors.AppThemeColor
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .verticalScroll(scrollState)
        ) {
            Image(
                painter = rememberImagePainter(
                    data = BuildConfig.POSTER_URL + movie.posterPath, builder = {
                        crossfade(true)
                        scale(Scale.FIT)
                    }),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(350.dp)
                    .clickable { navigateToMovieVideos(movie.movieId) },
                contentScale = ContentScale.FillWidth
            )
            Column(modifier = Modifier.padding(8.dp)) {
                Spacer(modifier = Modifier.height(16.dp))
                movie.title?.let {
                    Text(
                        text = it,
                        style = MaterialTheme.typography.h5,
                        fontWeight = FontWeight.Bold
                    )
                }
                Spacer(modifier = Modifier.height(8.dp))
                movie.releaseDate?.let {
                    ReleaseDateComponent(releaseDate = it)
                }
                Spacer(modifier = Modifier.height(8.dp))
                movie.rating?.let { RatingComponent(rating = it) }
                Spacer(modifier = Modifier.height(16.dp))
                movie.overview?.let {
                    Text(
                        text = it,
                        style = MaterialTheme.typography.body2
                    )
                }
            }
        }
    }
}

@Composable
fun ReleaseDateComponent(releaseDate: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = painterResource(id = R.drawable.ic_baseline_date_range_24),
            contentDescription = null,
            modifier = Modifier.padding(
                end = 2.dp,
            )
        )
        Text(text = releaseDate, style = MaterialTheme.typography.body2)
    }
}

@Preview("Light Theme", widthDp = 360, heightDp = 640)
@Composable
fun MovieDetailsPreview() {
    MoviesAppTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colors.background
        ) {
            MovieDetailsScreen({}, {}, movieId = 0)
        }
    }
}

@Preview("Dark Theme", widthDp = 360, heightDp = 640)
@Composable
fun MovieDetailsDarkPreview() {
    MoviesAppTheme(darkTheme = true) {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colors.background
        ) {
            MovieDetailsScreen({}, {}, movieId = 0)
        }
    }
}