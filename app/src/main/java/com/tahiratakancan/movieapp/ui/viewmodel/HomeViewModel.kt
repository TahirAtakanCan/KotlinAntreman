package com.tahiratakancan.movieapp.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tahiratakancan.movieapp.data.model.Movie
import com.tahiratakancan.movieapp.data.repository.MovieRepository
import com.tahiratakancan.movieapp.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: MovieRepository
) : ViewModel() {

    // _movies dışarıdan değiştirilemez (Mutable), movies ise sadece okunabilir (Immutable)
    private val _movies = MutableStateFlow<Resource<List<Movie>>>(Resource.Loading())
    val movies: StateFlow<Resource<List<Movie>>> = _movies

    init {
        getPopularMovies()
    }

    fun getPopularMovies() {
        viewModelScope.launch {
            _movies.value = Resource.Loading()
            try {
                val response = repository.getPopularMovies()
                if (response.isSuccessful) {
                    response.body()?.let { resultResponse ->
                        _movies.value = Resource.Success(resultResponse.results)
                    }
                } else {
                    _movies.value = Resource.Error(response.message())
                }
            } catch (e: Exception) {
                _movies.value = Resource.Error(e.message ?: "Bir hata oluştu")
            }
        }
    }
}