package com.tahiratakancan.movieapp.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.tahiratakancan.movieapp.databinding.ItemMovieXmlBinding // DÜZELTİLDİ
import com.tahiratakancan.movieapp.data.model.Movie // DÜZELTİLDİ

class MoviesAdapter(private val onMovieClick: (Movie) -> Unit) : RecyclerView.Adapter<MoviesAdapter.MovieViewHolder>() {

    private val diffCallback = object : DiffUtil.ItemCallback<Movie>() {
        override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
            return oldItem == newItem
        }
    }

    private val differ = AsyncListDiffer(this, diffCallback)

    fun submitList(list: List<Movie>) {
        differ.submitList(list)
    }

    inner class MovieViewHolder(val binding: ItemMovieXmlBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val binding = ItemMovieXmlBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MovieViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val movie = differ.currentList[position]
        holder.binding.apply {
            tvMovieTitle.text = movie.title
            tvMovieRating.text = movie.voteAverage.toString()
            tvReleaseDate.text = movie.releaseDate

            val fullPosterUrl = "https://image.tmdb.org/t/p/w500" + movie.posterPath
            Glide.with(root.context)
                .load(fullPosterUrl)
                .into(ivMoviePoster)

            root.setOnClickListener {
                onMovieClick(movie)
            }
        }
    }

    override fun getItemCount() = differ.currentList.size
}