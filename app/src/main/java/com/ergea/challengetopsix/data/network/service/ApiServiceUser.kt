package com.ergea.challengetopsix.data.network.service

import com.ergea.challengetopsix.data.network.model.user.GetUserResponse
import com.ergea.challengetopsix.data.network.model.user.PostUserResponse
import com.ergea.challengetopsix.data.network.model.user.User
import com.ergea.challengetopsix.data.network.model.user.UserProfile
import retrofit2.Call
import retrofit2.http.*

interface ApiServiceUser {
    @GET("users")
    fun getAllUsers(): Call<List<GetUserResponse>>

    @POST("users")
    fun insertUser(@Body request: User): Call<PostUserResponse>

    @PUT("users/{id}")
    fun updateUser(@Path("id") id : Int, @Body request: UserProfile): Call<PostUserResponse>

    @GET("users/{id}")
    fun getUserById(@Path("id") id: Int): Call<GetUserResponse>
}