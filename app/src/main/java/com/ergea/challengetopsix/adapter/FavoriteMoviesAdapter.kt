package com.ergea.challengetopsix.adapter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ergea.challengetopsix.R
import com.ergea.challengetopsix.databinding.MovieListFavBinding
import com.rich.movieupdate.data.local.FavoriteMovie

class FavoriteMovieAdapter(private val listMovie: List<FavoriteMovie>) :
    RecyclerView.Adapter<FavoriteMovieAdapter.FavMovieViewHolder>() {


    class FavMovieViewHolder(var binding: MovieListFavBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(movie: FavoriteMovie) {
            with(itemView) {
                binding.apply {
                    titleMovie.text = movie.title
                    releaseMovie.text = movie.releaseDate
                    Glide.with(itemView.context)
                        .load("https://image.tmdb.org/t/p/w400${movie.posterPath}")
                        .into(binding.imgMovie)
                    cardView.setOnClickListener{
                        val bundle = Bundle().apply {
                            putInt("ID", movie.id.toString().toInt())
                        }
                        it.findNavController().navigate(R.id.action_favoriteFragment_to_detailMovieFragment, bundle)
                    }
                }
            }

        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): FavoriteMovieAdapter.FavMovieViewHolder =
        FavMovieViewHolder(
            MovieListFavBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: FavMovieViewHolder, position: Int) =
        holder.bind(listMovie[position])


    override fun getItemCount(): Int = listMovie.size
}