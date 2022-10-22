package com.ergea.challengetopsix.data.network.service

import com.ergea.challengetopsix.data.network.model.movie.GetMovieDetailResponse
import com.ergea.challengetopsix.data.network.model.movie.GetMoviePopularResponse
import com.ergea.challengetopsix.data.network.model.user.GetUserResponse
import com.ergea.challengetopsix.data.network.model.user.PostUserResponse
import com.ergea.challengetopsix.data.network.model.user.User
import retrofit2.Call
import retrofit2.http.*

interface ApiServiceMovie {
    @GET("/3/movie/popular?api_key=9428967aca5607f7a2bbcb7a46f0ecfe")
    fun getMovieList(): Call<GetMoviePopularResponse>

    @GET("/3/movie/{id}?api_key=9428967aca5607f7a2bbcb7a46f0ecfe")
    fun getMovieDetail(@Path("id") id: Int): Call<GetMovieDetailResponse>


}