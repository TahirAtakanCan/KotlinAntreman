package com.tahiratakancan.movieapp.data.model

data class MovieResponse(
    val page: Int,
    val results: List<Movie>,
    val total_page: Int
)

//API 'den dönen ana cevap