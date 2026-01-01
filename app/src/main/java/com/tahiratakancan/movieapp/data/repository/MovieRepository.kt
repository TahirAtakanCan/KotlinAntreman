package com.tahiratakancan.movieapp.data.repository

import com.tahiratakancan.movieapp.data.model.Movie
import com.tahiratakancan.movieapp.data.model.MovieResponse
import kotlinx.coroutines.flow.Flow // Flow importu önemli
import retrofit2.Response

interface MovieRepository {
    // Eski fonksiyonumuz
    suspend fun getPopularMovies(): Response<MovieResponse>

    // YENİ EKLEYECEKLERİMİZ:
    suspend fun upsertMovie(movie: Movie) // Ekle/Güncelle
    suspend fun deleteMovie(movie: Movie) // Sil
    fun getFavoriteMovies(): Flow<List<Movie>> // Tüm favorileri canlı izle
    suspend fun isMovieFavorite(id: Int): Boolean // Favoride mi kontrol et
}