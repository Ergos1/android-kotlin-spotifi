package com.example.domain.repositories.implementations

import com.example.data.remote.models.MusicApi
import com.example.data.remote.models.forms.MusicLikeForm
import com.example.data.remote.providers.interfaces.AuthProvider
import com.example.data.remote.providers.interfaces.MusicProvider
import com.example.domain.repositories.interfaces.MusicRepository
import retrofit2.Response

class MusicRepositoryImpl(private val provider: MusicProvider): MusicRepository {
    override suspend fun getMusics(): Response<List<MusicApi>> {
        return provider.getMusics()
    }

    override suspend fun getMusicById(id: Int): Response<MusicApi> {
        return provider.getMusicById(id)
    }

    override suspend fun likeMusic(musicLikeForm: MusicLikeForm): Response<Void> {
        return provider.likeMusic(musicLikeForm)
    }


}