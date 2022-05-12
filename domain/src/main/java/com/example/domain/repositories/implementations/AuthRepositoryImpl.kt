package com.example.domain.repositories.implementations

import androidx.lifecycle.LiveData
import com.example.data.db.RoomAppDatabase
import com.example.data.db.models.TokenEntity
import com.example.data.remote.models.forms.AuthForm
import com.example.data.remote.models.TokensApi
import com.example.data.remote.providers.interfaces.AuthProvider
import com.example.domain.repositories.interfaces.AuthRepository
import retrofit2.Response

class AuthRepositoryImpl(
    private val provider: AuthProvider,
    private val roomAppDatabase: RoomAppDatabase
) : AuthRepository {

    override suspend fun register(authForm: AuthForm): Response<Void> {
        return provider.register(authForm);
    }

    override suspend fun login(authForm: AuthForm): Response<TokensApi> {
        return provider.login(authForm)
    }

    override suspend fun setToken(tokenEntity: TokenEntity) {
        roomAppDatabase.tokenDao().insertToken(tokenEntity)
    }

}