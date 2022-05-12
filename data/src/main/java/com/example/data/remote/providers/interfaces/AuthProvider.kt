package com.example.data.remote.providers.interfaces

import com.example.data.remote.models.forms.AuthForm
import com.example.data.remote.models.TokensApi
import retrofit2.Response

interface AuthProvider {
    suspend fun register(authForm: AuthForm): Response<Void>
    suspend fun login(authForm: AuthForm): Response<TokensApi>
}