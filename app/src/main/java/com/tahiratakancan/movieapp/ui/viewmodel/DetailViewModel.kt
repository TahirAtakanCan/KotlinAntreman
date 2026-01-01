package com.tahiratakancan.movieapp.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tahiratakancan.movieapp.data.model.Movie
import com.tahiratakancan.movieapp.data.repository.MovieRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val repository: MovieRepository
) : ViewModel() {

    // Film favoride mi? (UI bunu dinleyecek)
    private val _isFavorite = MutableStateFlow(false)
    val isFavorite: StateFlow<Boolean> = _isFavorite

    // Sayfa açılınca favori mi diye kontrol et
    fun checkFavoriteStatus(id: Int) {
        viewModelScope.launch {
            _isFavorite.value = repository.isMovieFavorite(id)
        }
    }

    // Butona basınca çalışacak fonksiyon
    fun toggleFavorite(movie: Movie) {
        viewModelScope.launch {
            if (_isFavorite.value) {
                repository.deleteMovie(movie) // Varsa sil
                _isFavorite.value = false
            } else {
                repository.upsertMovie(movie) // Yoksa ekle
                _isFavorite.value = true
            }
        }
    }
}