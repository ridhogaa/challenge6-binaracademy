package com.ergea.challengetopsix.ui.profile

import androidx.lifecycle.*
import com.ergea.challengetopsix.data.local.datastore.UserDataStoreManager
import com.ergea.challengetopsix.data.network.model.user.GetUserResponse
import com.ergea.challengetopsix.data.network.model.user.PostUserResponse
import com.ergea.challengetopsix.data.network.model.user.User
import com.ergea.challengetopsix.data.network.model.user.UserProfile
import com.ergea.challengetopsix.data.network.service.ApiServiceUser
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val pref: UserDataStoreManager,
    private val client: ApiServiceUser
) : ViewModel() {
    private val _user: MutableLiveData<GetUserResponse?> = MutableLiveData()
    val user: LiveData<GetUserResponse?> get() = _user


    fun getUserById(id: Int) {
        client.getUserById(id)
            .enqueue(object : Callback<GetUserResponse> {
                override fun onResponse(
                    call: Call<GetUserResponse>,
                    response: Response<GetUserResponse>
                ) {
                    if (response.isSuccessful) {
                        val responseBody = response.body()
                        if (responseBody != null) {
                            _user.postValue(responseBody)
                        }
                    }
                }

                override fun onFailure(call: Call<GetUserResponse>, t: Throwable) {
                }
            })
    }

    fun updateUser(id: Int, username: String, namaLengkap: String, tanggalLahir: String, alamat: String){
        client.updateUser(id, UserProfile(username, namaLengkap, tanggalLahir, alamat)).enqueue(object : Callback<PostUserResponse> {
            override fun onResponse(
                call: Call<PostUserResponse>,
                response: Response<PostUserResponse>
            ) {
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {

                    }
                }
            }

            override fun onFailure(call: Call<PostUserResponse>, t: Throwable) {
            }
        })
    }


    fun removeIsLoginStatus() {
        viewModelScope.launch {
            pref.removeIsLoginStatus()
        }
    }

    fun removeUsername() {
        viewModelScope.launch {
            pref.removeUsername()
        }
    }

    fun removeId() {
        viewModelScope.launch {
            pref.removeId()
        }
    }

    fun getDataStoreIsLogin(): LiveData<Boolean> {
        return pref.getIsLogin.asLiveData()
    }

    fun getId(): LiveData<Int> {
        return pref.getId.asLiveData()
    }

    fun saveUsername(username: String) {
        viewModelScope.launch {
            pref.saveUsername(username)
        }
    }


}