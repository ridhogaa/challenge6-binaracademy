package com.ergea.challengetopsix.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.ergea.challengetopsix.data.local.datastore.UserDataStoreManager
import com.ergea.challengetopsix.data.network.model.movie.GetMoviePopularResponse
import com.ergea.challengetopsix.data.network.model.movie.ResultMovies
import com.ergea.challengetopsix.data.network.service.ApiServiceMovie
import com.ergea.challengetopsix.data.network.service.ApiServiceUser
import dagger.hilt.android.lifecycle.HiltViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val movieClient: ApiServiceMovie,
    private val pref: UserDataStoreManager
) : ViewModel() {
    private val _movie: MutableLiveData<List<ResultMovies>> = MutableLiveData()
    val movie: LiveData<List<ResultMovies>> get() = _movie

    fun setMoviesList() {
        movieClient.getMovieList()
            .enqueue(object : Callback<GetMoviePopularResponse> {
                override fun onResponse(
                    call: Call<GetMoviePopularResponse>,
                    response: Response<GetMoviePopularResponse>
                ) {
                    if (response.isSuccessful) {
                        val data = response.body()
                        if (data != null) {
                            _movie.postValue(data.results as List<ResultMovies>?)
                        }
                    }
                }

                override fun onFailure(call: Call<GetMoviePopularResponse>, t: Throwable) {

                }

            })
    }


    fun getDataStoreUsername(): LiveData<String> {
        return pref.getUsername.asLiveData()
    }
}