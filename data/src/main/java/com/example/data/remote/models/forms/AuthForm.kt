package com.example.data.remote.models.forms

import com.google.gson.annotations.SerializedName

class AuthForm (
    @SerializedName("username")
    var username: String,
    @SerializedName("password")
    var password: String
) {}