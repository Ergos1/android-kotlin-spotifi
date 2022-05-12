package com.example.data.remote.services

import com.example.data.remote.models.forms.AuthForm
import com.example.data.remote.models.TokensApi
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthService {
    @POST("sign-up/")
    suspend fun register(
        @Body authForm: AuthForm
    ): Response<Void>

    @POST("sign-in/")
    suspend fun login(
        @Body authForm: AuthForm
    ): Response<TokensApi>
}