package com.example.data.remote.interceptors

import android.content.Context
import android.util.Log
import com.example.data.db.RoomAppDatabase
import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor(private val context: Context): Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val builder = chain.request().newBuilder()
        val token = RoomAppDatabase.buildDataSource(context).tokenDao().fetchToken().accessToken
        builder.addHeader("Authorization", "Bearer $token")
        val request = builder.build()
        return chain.proceed(request)
    }
}