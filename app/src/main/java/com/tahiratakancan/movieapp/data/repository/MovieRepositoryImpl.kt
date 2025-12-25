package com.tahiratakancan.movieapp.data.repository

import com.tahiratakancan.movieapp.data.apiinterface.MovieApi
import com.tahiratakancan.movieapp.data.model.MovieResponse
import retrofit2.Response
import javax.inject.Inject

class MovieRepositoryImpl @Inject constructor(private val api: MovieApi) : MovieRepository {
    override suspend fun getPopularMovies(): Response<MovieResponse> {
        return api.getPopularMovies()
    }
}