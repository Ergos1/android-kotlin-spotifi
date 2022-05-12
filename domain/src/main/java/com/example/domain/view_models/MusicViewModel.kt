package com.example.domain.view_models

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.remote.models.MusicApi
import com.example.domain.repositories.interfaces.MusicRepository
import kotlinx.coroutines.launch

class MusicViewModel(private val musicRepository: MusicRepository) : ViewModel() {
    val musicList: MutableLiveData<List<MusicApi>> = MutableLiveData()
    val errorMessage: MutableLiveData<String> = MutableLiveData()

    fun getMusicList() {
        viewModelScope.launch {
            val response = musicRepository.getMusics()
            if (response.isSuccessful) {
                musicList.value = response.body()
            } else {
                errorMessage.value = response.message()
            }
        }
    }
}