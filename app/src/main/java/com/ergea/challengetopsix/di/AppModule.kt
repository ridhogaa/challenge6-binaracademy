package com.ergea.challengetopsix.di

import android.content.Context
import androidx.room.Room
import com.ergea.challengetopsix.data.local.datastore.UserDataStoreManager
import com.ergea.challengetopsix.data.network.service.ApiServiceMovie
import com.ergea.challengetopsix.data.network.service.ApiServiceUser
import com.rich.movieupdate.data.local.FavoriteDAO
import com.rich.movieupdate.data.local.FavoriteDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Singleton
    @Provides
    fun getMovieService() : ApiServiceMovie {
        val loggingInterceptor = HttpLoggingInterceptor()
            .setLevel(HttpLoggingInterceptor.Level.BODY)
        val client = OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .build()
        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.themoviedb.org")
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
        return retrofit.create(ApiServiceMovie::class.java)
    }

    @Singleton
    @Provides
    fun getUserService() : ApiServiceUser {
        val loggingInterceptor = HttpLoggingInterceptor()
            .setLevel(HttpLoggingInterceptor.Level.BODY)
        val client = OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .build()
        val retrofit = Retrofit.Builder()
            .baseUrl("https://633da1207e19b1782913017b.mockapi.io/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
        return retrofit.create(ApiServiceUser::class.java)
    }

    @Provides
    fun getUserManager(@ApplicationContext context: Context) : UserDataStoreManager = UserDataStoreManager(context)

    @Provides
    fun provideFavoriteDAO(db : FavoriteDatabase) : FavoriteDAO = db.favoriteDao()

    @Singleton
    @Provides
    fun provideDB(@ApplicationContext context: Context) : FavoriteDatabase {
        return Room.databaseBuilder(context, FavoriteDatabase::class.java, "favorite.db").build()
    }
}