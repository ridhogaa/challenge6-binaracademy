package com.ergea.challengetopsix.ui.home

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.ergea.challengetopsix.R
import com.ergea.challengetopsix.adapter.MovieAdapter
import com.ergea.challengetopsix.databinding.FragmentHomeBinding
import com.ergea.challengetopsix.databinding.FragmentLoginBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private lateinit var viewModel: HomeViewModel
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this)[HomeViewModel::class.java]
        setUsername()
        observeMovie()
        binding.imgProfile.setOnClickListener {
            it.findNavController().navigate(R.id.action_homeFragment_to_profileFragment)
        }
        binding.imgLove.setOnClickListener {
            it.findNavController().navigate(R.id.action_homeFragment_to_favoriteFragment)
        }
    }

    private fun setUsername(){
        viewModel.getDataStoreUsername().observe(viewLifecycleOwner){
            binding.txtWelcomeUsername.text = "Hi, $it"
        }
    }

    private fun observeMovie(){
        binding.movieRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.movieRecyclerView.setHasFixedSize(false)
        viewModel.setMoviesList()
        viewModel.movie.observe(viewLifecycleOwner) {
            if (it != null) {
                binding.movieRecyclerView.adapter = MovieAdapter(it)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}