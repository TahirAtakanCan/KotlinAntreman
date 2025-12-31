package com.tahiratakancan.movieapp.ui.fragment

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.tahiratakancan.movieapp.ui.viewmodel.HomeViewModel
import com.tahiratakancan.movieapp.R // DÜZELTİLDİ: Senin R dosyan
import com.tahiratakancan.movieapp.databinding.FragmentHomeViewBinding // DÜZELTİLDİ
import com.tahiratakancan.movieapp.ui.adapter.MoviesAdapter // DÜZELTİLDİ
import com.tahiratakancan.movieapp.util.Resource // DÜZELTİLDİ
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeViewFragment : Fragment(R.layout.fragment_home_view) {

    private var _binding: FragmentHomeViewBinding? = null
    private val binding get() = _binding!!

    private val viewModel: HomeViewModel by viewModels()
    private lateinit var moviesAdapter: MoviesAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentHomeViewBinding.bind(view)

        setupRecyclerView()
        observeData()
    }

    private fun setupRecyclerView() {
        moviesAdapter = MoviesAdapter { movie ->
            Toast.makeText(context, "${movie.title} tıklandı", Toast.LENGTH_SHORT).show()
        }

        binding.rvMovies.apply {
            adapter = moviesAdapter
            layoutManager = LinearLayoutManager(context)
        }
    }

    private fun observeData() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.movies.collectLatest { resource ->
                when (resource) {
                    is Resource.Loading -> {
                        binding.progressBar.visibility = View.VISIBLE
                        binding.tvError.visibility = View.GONE
                    }
                    is Resource.Success -> {
                        binding.progressBar.visibility = View.GONE
                        binding.tvError.visibility = View.GONE
                        resource.data?.let { movies ->
                            moviesAdapter.submitList(movies)
                        }
                    }
                    is Resource.Error -> {
                        binding.progressBar.visibility = View.GONE
                        binding.tvError.visibility = View.VISIBLE
                        binding.tvError.text = resource.message
                    }
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}