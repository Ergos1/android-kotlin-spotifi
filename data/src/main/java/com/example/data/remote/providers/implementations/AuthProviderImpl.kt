package com.example.data.remote.providers.implementations

import com.example.data.remote.models.forms.AuthForm
import com.example.data.remote.models.TokensApi
import com.example.data.remote.helpers.RetrofitFactory
import com.example.data.remote.providers.interfaces.AuthProvider
import retrofit2.Response

class AuthProviderImpl: AuthProvider {
    override suspend fun register(authForm: AuthForm): Response<Void> {
        return RetrofitFactory.getAuthService().register(authForm)
    }

    override suspend fun login(authForm: AuthForm): Response<TokensApi> {
        return RetrofitFactory.getAuthService().login(authForm)
    }
}