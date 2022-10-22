package com.ergea.challengetopsix.ui.favorite

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.ergea.challengetopsix.R
import com.ergea.challengetopsix.adapter.FavoriteMovieAdapter
import com.ergea.challengetopsix.databinding.FragmentDetailMovieBinding
import com.ergea.challengetopsix.databinding.FragmentFavoriteBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavoriteFragment : Fragment() {

    private lateinit var viewModel: FavoriteViewModel
    private var _binding: FragmentFavoriteBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFavoriteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this)[FavoriteViewModel::class.java]
        getAllFavMovies()
    }

    private fun getAllFavMovies(){
        viewModel.getAllFavoriteMovie()
        viewModel.listMovie.observe(viewLifecycleOwner){
            if(it != null){
                binding.favoriteReyclerView.layoutManager = LinearLayoutManager(requireContext())
                binding.favoriteReyclerView.setHasFixedSize(false)
                binding.favoriteReyclerView.adapter = FavoriteMovieAdapter(it)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}