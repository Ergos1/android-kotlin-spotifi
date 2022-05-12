package com.example.domain.view_models

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.domain.repositories.interfaces.MusicRepository

class MusicPlayerViewModelFactory(private val musicRepository: MusicRepository): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MusicPlayerViewModel(musicRepository) as T
    }
}