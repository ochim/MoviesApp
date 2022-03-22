package com.example.moviesapp.domain

data class Movie(
    val movieId: Int,
    val overview: String?,
    val posterPath: String?,
    val title: String?,
    val rating: String?,
    val releaseDate: String?,
    val movieVideosEntity: MovieVideosEntity?
)

val testMovies = listOf(
    Movie(
        movieId = 0,
        overview = "Peter Parker is unmasked and no longer able to separate his normal life from the high-stakes of being a super-hero. When he asks for help from Doctor Strange the stakes become even more dangerous, forcing him to discover what it truly means to be Spider-Man.",
        posterPath = "/1g0dhYtq4irTY1GPXvft6k4YLjm.jpg",
        title = "Spider-Man: No Way Home",
        rating = "8.3",
        releaseDate = "2021-12-15",
        movieVideosEntity = MovieVideosEntity(
            2,
            listOf(
                VideoEntity(1, "https://media.rawg.io/media/movies/d8a/d8a61a3a12e52114afdbc28f2c813f5c.jpg", "GTA Online: Smuggler's Run Trailer", "https://steamcdn-a.akamaihd.net/steam/apps/256693661/movie_max.mp4"),
                VideoEntity(2, "https://media.rawg.io/media/movies/80c/80c2eeb2478d31291dfb5a5fd5a45f2e.jpg", "GTA Online: Gunrunning Trailer", "https://steamcdn-a.akamaihd.net/steam/apps/256686767/movie_max.mp4")
            )
        )
    ),
    Movie(
        movieId = 1,
        overview = "In his second year of fighting crime, Batman uncovers corruption in Gotham City that connects to his own family while facing a serial killer known as the Riddler.",
        posterPath = "/74xTEgt7R36Fpooo50r9T25onhq.jpg",
        title = "The Batman",
        rating = "8.1",
        releaseDate = "2022-03-01",
        movieVideosEntity = MovieVideosEntity(
            2,
            listOf(
                VideoEntity(3, "https://media.rawg.io/media/movies/7c9/7c9f84f3ec31106944a04128287fdd6a.jpg", "GTA Online: Tiny Racers Trailer", "https://steamcdn-a.akamaihd.net/steam/apps/256683844/movie_max.mp4"),
                VideoEntity(4, "https://media.rawg.io/media/movies/d6e/d6e1deb16c4275e8f5769528780e03ac.jpg", "GTA Online Cunning Stunts: Special Vehicle Circuit Trailer", "https://steamcdn-a.akamaihd.net/steam/apps/256681415/movie_max.mp4")
            )
        )
    )
)