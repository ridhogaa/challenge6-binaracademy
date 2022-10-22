package com.ergea.challengetopsix.ui.profile

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.viewModelScope
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.ergea.challengetopsix.R
import com.ergea.challengetopsix.databinding.FragmentHomeBinding
import com.ergea.challengetopsix.databinding.FragmentProfileBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ProfileFragment : Fragment() {

    private lateinit var viewModel: ProfileViewModel
    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this)[ProfileViewModel::class.java]
        binding.btnLogout.setOnClickListener {
            viewModel.removeIsLoginStatus()
            viewModel.removeUsername()
            viewModel.removeId()
            viewModel.getDataStoreIsLogin().observe(viewLifecycleOwner) {
                findNavController().navigate(R.id.action_profileFragment_to_loginFragment)
            }
        }
        viewModel.getId().observe(viewLifecycleOwner) {
            Log.i("INFOID", it.toString())
            update(it)
            viewModel.getUserById(it)
            setField()

        }

    }

    private fun setField() {
        viewModel.user.observe(viewLifecycleOwner) { data ->
            binding.apply {
                if (data != null) {
                    etUsername.setText(data.username.toString())
                    etNamaLengkap.setText(data.namaLengkap.toString())
                    etTanggalLahir.setText(data.tanggalLahir.toString())
                    etAlamat.setText(data.alamat.toString())
                }
            }
        }
    }

    private fun update(id: Int){
        binding.btnUpdate.setOnClickListener {
            val username = binding.etUsername.text.toString().trim()
            val namaLengkap = binding.etNamaLengkap.text.toString().trim()
            val tanggalLahir = binding.etTanggalLahir.text.toString().trim()
            val alamat = binding.etAlamat.text.toString().trim()
            viewModel.saveUsername(username)
            viewModel.updateUser(id, username, namaLengkap, tanggalLahir, alamat)
            Toast.makeText(requireContext(), "Update Success", Toast.LENGTH_SHORT).show()
            it.findNavController().navigate(R.id.action_profileFragment_to_homeFragment)
        }
    }

    private fun takePict(){
        binding.myPic.setOnClickListener {

        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}