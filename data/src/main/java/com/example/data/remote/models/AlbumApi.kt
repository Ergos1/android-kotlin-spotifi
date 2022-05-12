package com.example.data.remote.models

import kotlinx.serialization.Serializable

@Serializable
data class AlbumApi(
    val title: String,
    val preview_url: String
) {}