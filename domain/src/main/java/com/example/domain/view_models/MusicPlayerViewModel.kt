package com.example.domain.view_models

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.remote.models.MusicApi
import com.example.data.remote.models.forms.MusicLikeForm
import com.example.domain.repositories.interfaces.MusicRepository
import kotlinx.coroutines.launch

class MusicPlayerViewModel(private val musicRepository: MusicRepository): ViewModel() {
    val selectedMusic: MutableLiveData<MusicApi> = MutableLiveData()
    val likedMusic: MutableLiveData<Void> = MutableLiveData()
    val errorMessage: MutableLiveData<String> = MutableLiveData()

    fun getMusicById(id: Int) {
        viewModelScope.launch {
            val response = musicRepository.getMusicById(id)
            if (response.isSuccessful) {
                selectedMusic.value = response.body()
            } else {
                errorMessage.value = response.message()
            }
        }
    }

    fun likeMusic(id: Int) {
        viewModelScope.launch {
            val response = musicRepository.likeMusic(MusicLikeForm(id))
            if (response.isSuccessful) {
                likedMusic.value = response.body()
            } else {
                Log.d("Like music request", "FAILED")
                errorMessage.value = response.message()
            }
        }
    }
}