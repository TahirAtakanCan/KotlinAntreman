package com.tahiratakancan.movieapp.ui.fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.tahiratakancan.movieapp.R
import com.tahiratakancan.movieapp.data.model.Movie
import com.tahiratakancan.movieapp.databinding.FragmentDetailBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailFragment : Fragment(R.layout.fragment_detail) {

    private var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentDetailBinding.bind(view)

        // Argümanları al
        arguments?.let {
            // "movie" anahtarı nav_graph.xml dosyasındaki argument isminden gelir
            val movie = it.getParcelable<Movie>("movie")
            movie?.let { m ->
                bindData(m)
            }
        }
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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}