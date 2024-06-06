package com.example.musicmobileapp.models.screens

import com.example.musicmobileapp.models.dto.AlbumModel
import com.example.musicmobileapp.models.dto.ArtistModel
import com.example.musicmobileapp.models.dto.TrackModel
import com.example.musicmobileapp.network.api.ApiRoutes

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
        fun map(am: AlbumModel, ar: ArtistModel): AlbumScreenModel {
            return AlbumScreenModel(
                id = am.id,
                name = am.name,
                artistId = am.artistId,
                artistName = am.artistName,
                albumImagePath = am.imagePath,
                artistImagePath = ar.imagePath,
                tracks = am.tracks

            )

        }

    }
}
