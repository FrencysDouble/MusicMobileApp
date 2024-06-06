package com.example.musicmobileapp.models.screens

import com.example.musicmobileapp.models.dto.TrackModel
import com.example.musicmobileapp.network.api.ApiRoutes

data class PlaylistScreenModel(
    val id : Int,
    val name: String,
    val creatorName: String,
    val image_path : String,
    val creatorId : Long,
    val tracks: List<TrackModel>
)
{
    val imageUrl: String
        get() = "${ApiRoutes.BASE_FILE_URL}/getIMG?path=$image_path"
    companion object
    {
        fun map(pll: List<PlaylistScreenModel>) : List<PlaylistScreenModel>
        {
            val mappedData = emptyList<PlaylistScreenModel>().toMutableList()
                for(pl in pll) {
                    mappedData.add(
                        PlaylistScreenModel(
                            id = pl.id,
                            name = pl.name,
                            creatorName = pl.creatorName,
                            creatorId = pl.creatorId,
                            image_path = pl.image_path.replace("\\", "/"),
                            tracks = pl.tracks
                        )
                    )

                }
            return mappedData

        }

        fun mapOne(pl: PlaylistScreenModel): PlaylistScreenModel {
            return pl.let {
                PlaylistScreenModel(
                    id = it.id,
                    name = it.name,
                    creatorName = it.creatorName,
                    image_path = it.image_path.replace("\\", "/"),
                    creatorId = it.creatorId,
                    tracks = it.tracks
                )
            }
        }

    }
}
