package com.example.musicmobileapp.models.screens

import com.example.musicmobileapp.models.dto.AlbumModel
import com.example.musicmobileapp.models.dto.ArtistModel
import com.example.musicmobileapp.models.dto.TrackModel
import com.example.musicmobileapp.network.api.ApiRoutes
import retrofit2.Response

data class AlbumScreenModel(
    val id: Int,
    val name: String,
    val artistId: Int,
    val artistName : String,
    val artistImagePath : String,
    val albumImagePath: String,
    val tracks: List<TrackModel>
) {
    val artistImageUrl: String
        get() = "${ApiRoutes.BASE_FILE_URL}/getIMG?path=$artistImagePath"

    val albumImageUrl:String
        get() = "${ApiRoutes.BASE_FILE_URL}/getIMG?path=$albumImagePath"
    companion object {
        fun map(am: Response<AlbumModel>, ar: Response<ArtistModel>): AlbumScreenModel {
            val amData = am.body() ?: throw IllegalArgumentException("Response body is null")
            val arData = ar.body() ?: throw IllegalArgumentException("Response body is null")
            return AlbumScreenModel(
                id = amData.id,
                name = amData.name,
                artistId = amData.artistId,
                artistName = amData.artistName,
                albumImagePath = amData.imagePath,
                artistImagePath = arData.imagePath,
                tracks = amData.tracks

            )

        }

    }
}
