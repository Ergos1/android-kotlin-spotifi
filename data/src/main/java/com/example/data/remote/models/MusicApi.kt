package com.example.data.remote.models

import kotlinx.serialization.Serializable

@Serializable
data class MusicApi(
    val id: Int,
    val title: String,
    val url: String,
    val preview_url: String,
    val category: CategoryApi,
    val artist: ArtistApi,
    val album: AlbumApi,
    val likes: Int,
    val is_liked: Boolean
) {
    override fun equals(other: Any?): Boolean {
        return other is MusicApi && other.id == id
    }
}