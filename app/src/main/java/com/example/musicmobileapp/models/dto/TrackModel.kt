package com.example.musicmobileapp.models.dto

import com.squareup.moshi.Json

data class TrackModel(
    val id: Int,
    val name: String,
    val artistName : String,
    val audioPath: String,
    @Json(name = "image_path")
    val imagePath : String
)
