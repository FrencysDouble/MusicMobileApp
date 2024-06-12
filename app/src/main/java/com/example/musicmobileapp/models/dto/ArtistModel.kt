package com.example.musicmobileapp.models.dto

import com.example.musicmobileapp.network.api.ApiRoutes

data class ArtistModel(
    val id: Int,
    val name: String,
    val imagePath : String
)
{
    val imageUrl: String
        get() = "${ApiRoutes.BASE_FILE_URL}/getIMG?path=${imagePath.replace("\\", "/")}"
}