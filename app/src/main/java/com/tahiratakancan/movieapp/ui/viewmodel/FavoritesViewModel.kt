package com.tahiratakancan.movieapp.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tahiratakancan.movieapp.data.model.Movie
import com.tahiratakancan.movieapp.data.repository.MovieRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoritesViewModel @Inject constructor(
    private val repository: MovieRepository
) : ViewModel() {

    private val _favorites = MutableStateFlow<List<Movie>>(emptyList())
    val favorites: StateFlow<List<Movie>> = _favorites

    init {
        getFavorites()
    }

    private fun getFavorites() {
        viewModelScope.launch {
            // Veritabanını dinliyoruz (Canlı Veri)
            repository.getFavoriteMovies().collectLatest { movieList ->
                _favorites.value = movieList
            }
        }
    }
}