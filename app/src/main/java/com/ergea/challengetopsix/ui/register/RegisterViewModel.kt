package com.ergea.challengetopsix.ui.register

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ergea.challengetopsix.data.network.model.user.PostUserResponse
import com.ergea.challengetopsix.data.network.model.user.User
import com.ergea.challengetopsix.data.network.service.ApiServiceUser
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(private val client : ApiServiceUser): ViewModel() {
    private val _user: MutableLiveData<PostUserResponse?> = MutableLiveData()

    fun insertUser(username: String, email: String, password: String) {
        client.insertUser(
            User(username, email, password, "", "", "")
        )
            .enqueue(object : retrofit2.Callback<PostUserResponse> {
                override fun onResponse(
                    call: retrofit2.Call<PostUserResponse>,
                    response: retrofit2.Response<PostUserResponse>
                ) {
                    if (response.isSuccessful) {
                        _user.postValue(response.body())
                    } else {
                        Log.d("Error", response.message())
                        _user.postValue(null)
                    }
                }

                override fun onFailure(call: retrofit2.Call<PostUserResponse>, t: Throwable) {
                    Log.d("Error", t.message.toString())
                    _user.postValue(null)
                }

            })
    }
}