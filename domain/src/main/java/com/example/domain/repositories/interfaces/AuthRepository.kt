package com.example.domain.repositories.interfaces

import com.example.data.db.models.TokenEntity
import com.example.data.remote.models.forms.AuthForm
import com.example.data.remote.models.TokensApi
import retrofit2.Response

interface AuthRepository {
    suspend fun register(authForm: AuthForm): Response<Void>
    suspend fun login(authForm: AuthForm): Response<TokensApi>
    suspend fun setToken(tokenEntity: TokenEntity)
}