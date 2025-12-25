package com.tahiratakancan.movieapp.data.apiinterface

import com.tahiratakancan.movieapp.data.model.MovieResponse
import retrofit2.http.GET
import retrofit2.http.Query
import com.tahiratakancan.movieapp.util.Constants.API_KEY
import retrofit2.Response

interface MovieApi {

    @GET("movie/popular")
    suspend fun getPopularMovies(
        @Query("api_key") apiKey: String = API_KEY,
        @Query("language") language: String = "tr-TR",
        @Query("page") page: Int = 1
    ): Response<MovieResponse>

}