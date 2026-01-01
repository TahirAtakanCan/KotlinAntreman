package com.tahiratakancan.movieapp.ui.fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.tahiratakancan.movieapp.R
import com.tahiratakancan.movieapp.databinding.FragmentFavoritesBinding
import com.tahiratakancan.movieapp.ui.adapter.MoviesAdapter
import com.tahiratakancan.movieapp.ui.viewmodel.FavoritesViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class FavoritesFragment : Fragment(R.layout.fragment_favorites) {

    private var _binding: FragmentFavoritesBinding? = null
    private val binding get() = _binding!!

    private val viewModel: FavoritesViewModel by viewModels()
    private lateinit var moviesAdapter: MoviesAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentFavoritesBinding.bind(view)

        setupRecyclerView()
        observeData()
    }

    private fun setupRecyclerView() {
        // Ana sayfadaki ile aynı Adapter'ı kullanıyoruz!
        moviesAdapter = MoviesAdapter { movie ->
            val bundle = Bundle().apply {
                putParcelable("movie", movie)
            }
            // Detay sayfasına git (Favorilerden de detaya gidilebilmeli)
            findNavController().navigate(R.id.action_favorites_to_detail, bundle)
        }

        binding.rvFavorites.apply {
            adapter = moviesAdapter
            layoutManager = LinearLayoutManager(context)
        }
    }

    private fun observeData() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.favorites.collectLatest { movies ->
                if (movies.isEmpty()) {
                    binding.tvEmpty.visibility = View.VISIBLE
                    binding.rvFavorites.visibility = View.GONE
                } else {
                    binding.tvEmpty.visibility = View.GONE
                    binding.rvFavorites.visibility = View.VISIBLE
                    moviesAdapter.submitList(movies)
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}