package com.example.musicmobileapp.models.screens

import com.example.musicmobileapp.models.dto.AlbumModel
import com.example.musicmobileapp.models.dto.ArtistModel
import com.example.musicmobileapp.models.dto.TrackModel
import com.example.musicmobileapp.network.api.ApiRoutes

data class ArtistScreenModel(
    val id : Int,
    val name : String,
    val arImagePath : String,
    val albums : List<AlbumModel>,
    val tracks : List<TrackModel>

)
{
    companion object
    {
        fun map(art : ArtistModel,al: List<AlbumModel>, tr: List<TrackModel>) : ArtistScreenModel
        {
            return ArtistScreenModel(
                id = art.id,
                name = art.name,
                arImagePath = art.imageUrl,
                albums = al,
                tracks = tr
            )
        }
    }

}
