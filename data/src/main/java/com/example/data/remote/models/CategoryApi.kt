package com.example.data.remote.models

import kotlinx.serialization.Serializable

@Serializable
data class CategoryApi(
    val title: String,
    val preview_url: String
) {}