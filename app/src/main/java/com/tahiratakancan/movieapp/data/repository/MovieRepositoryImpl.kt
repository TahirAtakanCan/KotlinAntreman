package com.tahiratakancan.movieapp.data.repository

import com.tahiratakancan.movieapp.data.apiinterface.MovieApi
import com.tahiratakancan.movieapp.data.db.MovieDao // DAO Importu
import com.tahiratakancan.movieapp.data.model.Movie
import com.tahiratakancan.movieapp.data.model.MovieResponse
import kotlinx.coroutines.flow.Flow
import retrofit2.Response
import javax.inject.Inject

class MovieRepositoryImpl @Inject constructor(
    private val api: MovieApi,
    private val dao: MovieDao // ARTIK DAO DA İSTİYORUZ
) : MovieRepository {

    override suspend fun getPopularMovies(): Response<MovieResponse> {
        return api.getPopularMovies()
    }

    // YENİ FONKSİYONLARIN İÇİ:
    override suspend fun upsertMovie(movie: Movie) {
        dao.upsert(movie)
    }

    override suspend fun deleteMovie(movie: Movie) {
        dao.deleteMovie(movie)
    }

    override fun getFavoriteMovies(): Flow<List<Movie>> {
        return dao.getAllMovies()
    }

    override suspend fun isMovieFavorite(id: Int): Boolean {
        return dao.isMovieFavorite(id)
    }
}