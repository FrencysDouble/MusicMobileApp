package com.example.musicmobileapp.models.dto

import com.squareup.moshi.Json

data class UserModel(
    @Json(name = "email")
    val email : String,
    @Json(name = "userName")
    val userName : String,
    @Json(name = "password")
    val password : String
)
