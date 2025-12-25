package com.tahiratakancan.movieapp.data.repository

import com.tahiratakancan.movieapp.data.model.MovieResponse
import retrofit2.Response

interface MovieRepository {
    suspend fun getPopularMovies(): Response<MovieResponse>
}