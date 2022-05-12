package com.example.data.remote.models.forms

import com.google.gson.annotations.SerializedName

class MusicLikeForm(
    @SerializedName("music_id")
    val musicId: Int
) {
}