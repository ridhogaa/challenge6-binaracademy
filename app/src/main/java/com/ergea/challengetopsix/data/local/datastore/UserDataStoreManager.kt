package com.ergea.challengetopsix.data.local.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class UserDataStoreManager(@ApplicationContext val context: Context) {

    val getUsername : Flow<String> = context.dataStore.data.map {
        it[USERNAME_KEY] ?: ""
    }

    val getIsLogin : Flow<Boolean> = context.dataStore.data.map {
        it[IS_LOGIN_KEY] ?: false
    }

    val getId : Flow<Int> = context.dataStore.data.map {
        it[ID_USER_KEY] ?: 0
    }

    suspend fun saveUsername(username : String){
        context.dataStore.edit {
            it[USERNAME_KEY] = username
        }
    }

    suspend fun saveIsLoginStatus(paramIsLogin : Boolean){
        context.dataStore.edit {
            it[IS_LOGIN_KEY] = paramIsLogin
        }
    }

    suspend fun saveId(id : Int){
        context.dataStore.edit {
            it[ID_USER_KEY] = id
        }
    }

    suspend fun removeIsLoginStatus(){
        context.dataStore.edit {
            it.remove(IS_LOGIN_KEY)
        }
    }

    suspend fun removeUsername(){
        context.dataStore.edit {
            it.remove(USERNAME_KEY)
        }
    }

    suspend fun removeId(){
        context.dataStore.edit {
            it.remove(ID_USER_KEY)
        }
    }

    companion object {
        private const val DATASTORE_NAME = "user_preferences"
        private val USERNAME_KEY = stringPreferencesKey("username_key")
        private val ID_USER_KEY = intPreferencesKey("id_user_key")
        private val IS_LOGIN_KEY = booleanPreferencesKey("is_login_key")
        private val Context.dataStore by preferencesDataStore(
            name = DATASTORE_NAME
        )
    }
}