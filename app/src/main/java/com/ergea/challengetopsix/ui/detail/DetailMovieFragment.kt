package com.ergea.challengetopsix.ui.detail

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import com.bumptech.glide.Glide
import com.ergea.challengetopsix.R
import com.ergea.challengetopsix.databinding.FragmentDetailMovieBinding
import com.rich.movieupdate.data.local.FavoriteMovie
import dagger.hilt.android.AndroidEntryPoint
import kotlin.properties.Delegates

@AndroidEntryPoint
class DetailMovieFragment : Fragment() {

    private lateinit var viewModel: DetailMovieViewModel
    private var _binding: FragmentDetailMovieBinding? = null
    private val binding get() = _binding!!
    private lateinit var selectedMovie: FavoriteMovie
    private var isFavorite by Delegates.notNull<Boolean>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDetailMovieBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this)[DetailMovieViewModel::class.java]
        val id = arguments?.getInt("ID")
        if (id != null) {
            viewModel.getMovieById(id)
            observeDetailMovie()
            setFavoriteListener()
            checkFavoriteMovie(id)
        }
    }

    private fun observeDetailMovie() {
        viewModel.movie.observe(viewLifecycleOwner) {
            binding.apply {
                if (it != null) {
                    titleMovie.text = it.title.toString()
                    releaseDataMovie.text = it.releaseDate.toString()
                    Glide.with(requireContext())
                        .load("https://image.tmdb.org/t/p/w500/" + it.backdropPath)
                        .into(binding.movieImage)
                    descMovie.text = it.overview.toString()
                    selectedMovie = FavoriteMovie(
                        it.id!!,
                        it.title!!,
                        it.releaseDate!!,
                        it.voteAverage!!,
                        it.posterPath!!
                    )
                }
            }
        }
    }

    private fun setFavoriteListener() {
        binding.fabFav.apply {
            setOnClickListener {
                if (!isFavorite) {
                    addToFavorite(selectedMovie)
                    binding.fabFav.setImageResource(R.drawable.ic_love)
                    isFavorite = true
                } else {
                    deleteFromFavorite(selectedMovie)
                    binding.fabFav.setImageResource(R.drawable.ic_not_love)
                    isFavorite = false
                }
            }
        }
    }

    private fun addToFavorite(movie: FavoriteMovie) {
        viewModel.addFavMovie(movie)
        viewModel.favMovie.observe(viewLifecycleOwner) {
            if (it != null) {
                Toast.makeText(requireContext(), "Sukses tambah favorit", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(requireContext(), "Failed menambah favorit", Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }

    private fun deleteFromFavorite(movie: FavoriteMovie) {
        viewModel.deleteFavMovie(movie)
        viewModel.deleteFavMovie.observe(viewLifecycleOwner) {
            if (it != null) {
                Toast.makeText(requireContext(), "Sukses menghapus favorit", Toast.LENGTH_SHORT)
                    .show()
            } else {
                Toast.makeText(requireContext(), "Failed menghapus favorit", Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }

    private fun checkFavoriteMovie(movieId: Int) {
        viewModel.isFavoriteMovie(movieId)
        viewModel.isFav.observe(viewLifecycleOwner) {
            if (it != null) {
                if (it) {
                    isFavorite = true
                    binding.fabFav.setImageResource(R.drawable.ic_love)
                } else {
                    isFavorite = false
                    binding.fabFav.setImageResource(R.drawable.ic_not_love)
                }
            } else {
                Log.d("CHECK_FAV", "checkFavoriteMovie: ${it}")
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}