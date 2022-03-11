package com.example.moviesapp

data class Movie(
    val movieId: Int,
    val overview: String?,
    val posterPath: String?,
    val title: String?,
    val rating: String?,
    val releaseDate: String?,
)

val testMovies = listOf(
    Movie(
        movieId = 0,
        overview = "Peter Parker is unmasked and no longer able to separate his normal life from the high-stakes of being a super-hero. When he asks for help from Doctor Strange the stakes become even more dangerous, forcing him to discover what it truly means to be Spider-Man.",
        posterPath = "/1g0dhYtq4irTY1GPXvft6k4YLjm.jpg",
        title = "Spider-Man: No Way Home",
        rating = "8.3",
        releaseDate = "2021-12-15"
    ),
    Movie(
        movieId = 1,
        overview = "In his second year of fighting crime, Batman uncovers corruption in Gotham City that connects to his own family while facing a serial killer known as the Riddler.",
        posterPath = "/74xTEgt7R36Fpooo50r9T25onhq.jpg",
        title = "The Batman",
        rating = "8.1",
        releaseDate = "2022-03-01"
    )
)