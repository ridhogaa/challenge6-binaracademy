package com.ergea.challengetopsix.ui.login

import android.content.ContentValues
import android.util.Log
import androidx.lifecycle.*
import com.ergea.challengetopsix.data.local.datastore.UserDataStoreManager
import com.ergea.challengetopsix.data.network.model.user.GetUserResponse
import com.ergea.challengetopsix.data.network.service.ApiServiceUser
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val client: ApiServiceUser,
    private val pref: UserDataStoreManager
) : ViewModel() {
    private val _user: MutableLiveData<GetUserResponse?> = MutableLiveData()
    val user: LiveData<GetUserResponse?> get() = _user

//    private val _email: MutableLiveData<String?> = MutableLiveData()
//    val email: LiveData<String?> get() = _email
//
//    private val _password: MutableLiveData<String?> = MutableLiveData()
//    val password: LiveData<String?> get() = _password
//
//    private val _isExist: MutableLiveData<Boolean?> = MutableLiveData()
//    val isExist: LiveData<Boolean?> get() = _isExist
//
//    init {
//        auth()
//    }
//
//    fun setEmail(email: String){
//        _email.postValue(email)
//    }
//
//    fun setPassword(password: String){
//        _password.postValue(password)
//    }

    fun auth(email: String, password: String) {
        client.getAllUsers()
            .enqueue(object : Callback<List<GetUserResponse>> {
                override fun onResponse(
                    call: Call<List<GetUserResponse>>,
                    response: Response<List<GetUserResponse>>
                ) {
                    if (response.isSuccessful) {
                        val responseBody = response.body()
                        if (responseBody != null) {
                            //Log.d(ContentValues.TAG, "onResponse: ${responseBody.toString()}")
                            for (i in responseBody.indices) {
                                if (responseBody[i].email.equals(
                                        email.toString(),
                                        ignoreCase = false
                                    ) && responseBody[i].password.equals(
                                        password.toString(), ignoreCase = false
                                    )
                                ) {
                                    _user.postValue(responseBody[i])
                                } else {
                                    _user.postValue(null)
                                }
                            }
                        }
                    }
                }

                override fun onFailure(call: Call<List<GetUserResponse>>, t: Throwable) {
                }
            })
    }

    fun saveIsLoginStatus(status: Boolean) {
        viewModelScope.launch {
            pref.saveIsLoginStatus(status)
        }
    }


    fun saveUsername(username: String) {
        viewModelScope.launch {
            pref.saveUsername(username)
        }
    }

    fun saveId(id: Int){
        viewModelScope.launch {
            pref.saveId(id)
        }
    }

    fun getDataStoreIsLogin(): LiveData<Boolean> {
        return pref.getIsLogin.asLiveData()
    }


}