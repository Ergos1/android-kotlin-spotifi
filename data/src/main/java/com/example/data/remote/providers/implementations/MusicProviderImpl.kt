package com.example.data.remote.providers.implementations

import android.content.Context
import com.example.data.remote.helpers.RetrofitFactory
import com.example.data.remote.models.MusicApi
import com.example.data.remote.models.forms.MusicLikeForm
import com.example.data.remote.providers.interfaces.MusicProvider
import retrofit2.Response

class MusicProviderImpl(val context: Context): MusicProvider {
    override suspend fun getMusics(): Response<List<MusicApi>> {
        return RetrofitFactory.getMusicService(context).getMusics()
    }

    override suspend fun getMusicById(id: Int): Response<MusicApi> {
        return RetrofitFactory.getMusicService(context).getMusicById(id)
    }

    override suspend fun likeMusic(musicLikeForm: MusicLikeForm): Response<Void> {
        return RetrofitFactory.getMusicService(context).likeMusic(musicLikeForm)
    }

}