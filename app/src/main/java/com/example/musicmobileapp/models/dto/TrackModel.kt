package com.example.musicmobileapp.models.dto

import com.example.musicmobileapp.network.api.ApiRoutes
import com.squareup.moshi.Json

data class TrackModel(
    val id: Int,
    val name: String,
    val artistName : String,
    val audioPath: String,
    @Json(name = "image_path")
    val imagePath : String,
    val album_id : Int,
    val artist_id : Int
) {
    val imageUrl: String
        get() = "${ApiRoutes.BASE_FILE_URL}/getIMG?path=${imagePath.replace("\\", "/")}"
}
