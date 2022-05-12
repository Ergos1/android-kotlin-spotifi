package com.example.data.remote.helpers

import android.content.Context
import com.example.data.remote.interceptors.AuthInterceptor
import com.example.data.remote.services.AuthService
import com.example.data.remote.services.MusicService
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitFactory {

    companion object {
        private const val BASE_URL = "http://192.168.68.125:3000/api/";

        private fun getInitialClient(context: Context): OkHttpClient {
            return OkHttpClient.Builder().addInterceptor(AuthInterceptor(context)).build()
        }

        private fun getRetrofitClientPublic(): Retrofit {
            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }

        private fun getRetrofitClientPrivate(context: Context): Retrofit {
            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(getInitialClient(context))
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }

        fun getAuthService(): AuthService =
            getRetrofitClientPublic().create(AuthService::class.java)

        fun getMusicService(context: Context): MusicService =
            getRetrofitClientPrivate(context).create(MusicService::class.java)
    }
}