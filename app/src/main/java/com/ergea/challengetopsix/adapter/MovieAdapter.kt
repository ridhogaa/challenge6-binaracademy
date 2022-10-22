package com.ergea.challengetopsix.adapter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ergea.challengetopsix.R
import com.ergea.challengetopsix.data.network.model.movie.ResultMovies
import com.ergea.challengetopsix.databinding.MovieListBinding
import com.ergea.challengetopsix.databinding.MovieListBinding.inflate

class MovieAdapter(
    private val movies: List<ResultMovies>
) : RecyclerView.Adapter<MovieAdapter.MovieViewHolder>() {

    class MovieViewHolder(private val binding: MovieListBinding) :
        RecyclerView.ViewHolder(binding.root) {
        private val IMAGE_BASE = "https://image.tmdb.org/t/p/w500/"
        fun bindMovie(dataMovie: ResultMovies) {
            binding.titleMovie.text = dataMovie.title
            binding.releaseMovie.text = "Release at: " + dataMovie.releaseDate
            Glide.with(itemView).load(IMAGE_BASE + dataMovie.posterPath).into(binding.imgMovie)
            binding.cardView.setOnClickListener {
                val bundle = Bundle().apply {
                    putInt("ID", dataMovie.id.toString().toInt())
                }
                it.findNavController().navigate(R.id.action_homeFragment_to_detailMovieFragment, bundle)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder =
        MovieViewHolder(inflate(LayoutInflater.from(parent.context), parent, false))

    override fun getItemCount(): Int = movies.size

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        holder.bindMovie(movies[position])
    }
}