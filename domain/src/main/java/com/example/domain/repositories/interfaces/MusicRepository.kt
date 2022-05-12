package com.example.domain.repositories.interfaces

import com.example.data.remote.models.MusicApi
import com.example.data.remote.models.forms.MusicLikeForm
import retrofit2.Response

interface MusicRepository {
    suspend fun getMusics(): Response<List<MusicApi>>
    suspend fun getMusicById(id: Int): Response<MusicApi>
    suspend fun likeMusic(musicLikeForm: MusicLikeForm): Response<Void>
}