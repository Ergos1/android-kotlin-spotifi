package com.example.data.remote.models

import kotlinx.serialization.*

@Serializable
data class TokensApi(val access: String, val refresh: String) {
}