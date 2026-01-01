package com.tahiratakancan.movieapp.ui.fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.tahiratakancan.movieapp.R
import com.tahiratakancan.movieapp.data.model.Movie
import com.tahiratakancan.movieapp.databinding.FragmentDetailBinding
import com.tahiratakancan.movieapp.ui.viewmodel.DetailViewModel // ViewModel importu
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class DetailFragment : Fragment(R.layout.fragment_detail) {

    private var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding!!

    // ViewModel'i bağlıyoruz
    private val viewModel: DetailViewModel by viewModels()

    // O anki filmi global değişken yaptık ki her yerden erişelim
    private var currentMovie: Movie? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentDetailBinding.bind(view)

        // 1. Gelen filmi al ve ekrana bas
        arguments?.let {
            currentMovie = it.getParcelable("movie")
            currentMovie?.let { movie ->
                bindData(movie)
                // Sayfa açılınca "Bu film favoride mi?" diye sor
                viewModel.checkFavoriteStatus(movie.id)
            }
        }

        // 2. Butona tıklanma olayı
        binding.fabFavorite.setOnClickListener {
            currentMovie?.let { movie ->
                viewModel.toggleFavorite(movie)
            }
        }

        // 3. Favori durumunu dinle (Gözlemle)
        observeFavoriteState()
    }

    private fun bindData(movie: Movie) {
        binding.apply {
            tvDetailTitle.text = movie.title
            tvDetailRating.text = movie.voteAverage.toString()
            tvDetailOverview.text = movie.overview

            val fullPosterUrl = "https://image.tmdb.org/t/p/w500" + movie.posterPath
            Glide.with(this@DetailFragment)
                .load(fullPosterUrl)
                .into(ivDetailPoster)
        }
    }

    private fun observeFavoriteState() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.isFavorite.collectLatest { isFav ->
                // Favorideyse KIRMIZI KALP, değilse BOŞ KALP
                val icon = if (isFav) {
                    R.drawable.ic_favorite_filled
                } else {
                    R.drawable.ic_favorite_border
                }
                binding.fabFavorite.setImageResource(icon)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}