package com.example.musicmobileapp.models.dto

import com.example.musicmobileapp.network.api.ApiRoutes
import retrofit2.Response

data class AlbumModel(
    val id: Int,
    val name: String,
    val artistId: Int,
    val artistName : String,
    val imagePath: String,
    val trackNames: List<String>,
    val tracks: List<TrackModel>
)
{

    val imageUrl: String
        get() = "${ApiRoutes.BASE_FILE_URL}/getIMG?path=${imagePath.replace("\\", "/")}"

}



