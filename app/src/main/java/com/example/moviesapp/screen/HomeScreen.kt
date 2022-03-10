package com.example.moviesapp.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.moviesapp.Movie
import com.example.moviesapp.R
import com.example.moviesapp.ui.theme.AppContentColor
import com.example.moviesapp.ui.theme.AppThemeColor
import com.example.moviesapp.ui.theme.ItemBackgroundColor

@Composable
fun HomeScreen(
    movie: Movie = Movie(
        overview = "Peter Parker is unmasked and no longer able to separate his normal life from the high-stakes of being a super-hero. When he asks for help from Doctor Strange the stakes become even more dangerous, forcing him to discover what it truly means to be Spider-Man.",
        posterPath = "/1g0dhYtq4irTY1GPXvft6k4YLjm.jpg",
        title = "Spider-Man: No Way Home",
        rating = "8.3",
        releaseDate = "2021-12-15"
    )
) {
    Scaffold(
        topBar = {
            HomeTopBar()
        },
        content = {
            MovieListContent(movie)
        }
    )
}

@Composable
fun HomeTopBar(
) {
    TopAppBar(
        backgroundColor = MaterialTheme.colors.AppThemeColor,
        contentColor = MaterialTheme.colors.AppContentColor,
        title = {
            Text(
                text = stringResource(id = R.string.app_name),
                fontWeight = FontWeight.Bold,
                modifier = Modifier.fillMaxWidth(),
                style = MaterialTheme.typography.h5
            )
        },
        elevation = 0.dp,
        actions = {

            IconButton(onClick = {}) {
                Icon(
                    imageVector = Icons.Default.Favorite,
                    contentDescription = "Favourite Icon",
                    tint = Color.Red
                )
            }
        }
    )
}

@Composable
fun MovieListContent(movie: Movie) {
    LazyColumn(
        contentPadding = PaddingValues(horizontal = 8.dp, vertical = 4.dp)
    ) {
        items(
            1
        ) {
            MovieListItem(movie = movie)
        }
    }
}

@Composable
fun MovieListItem(movie: Movie) {
    Card(
        modifier = Modifier
            .padding(top = 8.dp)
            .height(180.dp)
            .fillMaxWidth(),
        elevation = 4.dp,
        backgroundColor = MaterialTheme.colors.ItemBackgroundColor
    ) {
        Row(
            modifier = Modifier
                .height(IntrinsicSize.Max)
                .fillMaxWidth()
                .clickable {
                },
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                Modifier
                    .height(IntrinsicSize.Max)
                    .padding(
                        end = 2.dp,
                    )
            ) {
                movie.title?.let { Text(text = it, style = MaterialTheme.typography.body1) }
                Spacer(modifier = Modifier.height(4.dp))
                movie.overview?.let {
                    Text(
                        text = it,
                        style = MaterialTheme.typography.body2,
                        maxLines = 4,
                        overflow = TextOverflow.Ellipsis
                    )
                }
                Spacer(modifier = Modifier.height(8.dp))
                movie.rating?.let {
                    RatingComponent(rating = it)
                }
            }
        }
    }
}

@Composable
fun RatingComponent(rating: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = painterResource(id = R.drawable.ic_baseline_star_rate),
            contentDescription = null,
            modifier = Modifier
                .padding(
                    end = 2.dp,
                )
        )
        Text(text = rating, style = MaterialTheme.typography.body2)
    }
}

@Preview("Light Theme", widthDp = 360, heightDp = 640)
@Composable
fun HomePreview() {
    HomeScreen()
}