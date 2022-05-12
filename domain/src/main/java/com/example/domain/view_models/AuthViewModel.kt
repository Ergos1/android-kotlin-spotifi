package com.example.domain.view_models

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.db.models.TokenEntity
import com.example.data.remote.models.forms.AuthForm
import com.example.data.remote.models.TokensApi
import com.example.domain.repositories.interfaces.AuthRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AuthViewModel(private val authRepository: AuthRepository) : ViewModel() {
    val loginResponse: MutableLiveData<TokensApi> = MutableLiveData()
    val registerResponse: MutableLiveData<Void> = MutableLiveData()
    val errorMessage: MutableLiveData<String> = MutableLiveData()

    fun register(authForm: AuthForm) {
        viewModelScope.launch {
            val response = authRepository.register(authForm)
            if (response.isSuccessful) {
                registerResponse.value = response.body()
            }
            else errorMessage.value = response.message()
        }
    }

    fun login(authForm: AuthForm) {
        viewModelScope.launch {
            val response = authRepository.login(authForm)
            if (response.isSuccessful) {
                loginResponse.value = response.body()
                val access = response.body()!!.access
                Log.i("Login api", access.toString())
                viewModelScope.launch(Dispatchers.IO) {
                    authRepository.setToken(tokenEntity = TokenEntity(id= 1, accessToken = access))
                }
                return@launch
            }
            errorMessage.value = response.message()
        }
    }

}