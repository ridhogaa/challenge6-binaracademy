package com.ergea.challengetopsix.ui.detail

import android.content.ContentValues
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bumptech.glide.Glide
import com.ergea.challengetopsix.data.local.datastore.UserDataStoreManager
import com.ergea.challengetopsix.data.network.model.movie.GetMovieDetailResponse
import com.ergea.challengetopsix.data.network.model.movie.GetMoviePopularResponse
import com.ergea.challengetopsix.data.network.model.movie.ResultMovies
import com.ergea.challengetopsix.data.network.service.ApiServiceMovie
import com.rich.movieupdate.data.local.FavoriteDAO
import com.rich.movieupdate.data.local.FavoriteMovie
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class DetailMovieViewModel @Inject constructor(
    private val movieClient: ApiServiceMovie, val db: FavoriteDAO
) : ViewModel() {
    private val _movie: MutableLiveData<GetMovieDetailResponse?> = MutableLiveData()
    val movie: LiveData<GetMovieDetailResponse?> get() = _movie

    private val _FavMovie: MutableLiveData<FavoriteMovie> = MutableLiveData()
    val favMovie: LiveData<FavoriteMovie> get() = _FavMovie

    private val _DeleteFavMovie: MutableLiveData<FavoriteMovie> = MutableLiveData()
    val deleteFavMovie: LiveData<FavoriteMovie> get() = _DeleteFavMovie

    private val _IsFav: MutableLiveData<Boolean> = MutableLiveData()
    val isFav: LiveData<Boolean> get() = _IsFav

    fun getMovieById(id: Int) {
        movieClient.getMovieDetail(id)
            .enqueue(object : Callback<GetMovieDetailResponse> {
                override fun onResponse(
                    call: Call<GetMovieDetailResponse>,
                    response: Response<GetMovieDetailResponse>
                ) {
                    if (response.isSuccessful) {
                        val responseBody = response.body()
                        if (responseBody != null) {
                            _movie.postValue(responseBody)
                        }
                    }
                }

                override fun onFailure(call: Call<GetMovieDetailResponse>, t: Throwable) {

                }

            })
    }

    fun isFavoriteMovie(id: Int) {
        GlobalScope.launch {
            _IsFav.postValue(db.isFavoriteMovie(id))
        }
    }

    fun addFavMovie(favMovie: FavoriteMovie) {
        GlobalScope.launch {
            db.addFavorite(favMovie)
            _FavMovie.postValue(favMovie)
        }
    }

    fun deleteFavMovie(favMovie: FavoriteMovie) {
        GlobalScope.launch {
            db.deleteFavorite(favMovie)
            _DeleteFavMovie.postValue(favMovie)
        }
    }
}