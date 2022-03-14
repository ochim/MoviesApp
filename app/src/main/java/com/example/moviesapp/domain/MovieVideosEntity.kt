package com.example.moviesapp.domain

data class MovieVideosEntity(
    val count: Int,
    val entities: List<VideoEntity>
) {
    constructor() : this(0, arrayListOf())
}
