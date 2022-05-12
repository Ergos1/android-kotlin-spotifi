package com.example.data.remote.services

import com.example.data.remote.models.MusicApi
import com.example.data.remote.models.forms.MusicLikeForm
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface MusicService {
    @GET("musics/")
    suspend fun getMusics(): Response<List<MusicApi>>

    @GET("musics/{id}/")
    suspend fun getMusicById(@Path("id") id: Int): Response<MusicApi>

    @POST("music/like/")
    suspend fun likeMusic(@Body musicLikeForm: MusicLikeForm): Response<Void>
}