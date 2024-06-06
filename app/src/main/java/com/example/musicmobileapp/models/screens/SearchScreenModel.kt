package com.example.musicmobileapp.models.screens

import com.example.musicmobileapp.models.dto.ArtistModel
import com.example.musicmobileapp.models.Title
import com.example.musicmobileapp.models.dto.TrackModel
import com.example.musicmobileapp.models.dto.AlbumModel
import com.example.musicmobileapp.network.api.ApiRoutes.BASE_FILE_URL

data class SearchScreenModel(

    val id: Int,
    val name: String,
    val title: String,
    val titleID: Long,
    val artistName: String,
    val imagePath: String

)

{
    val imageUrl: String
        get() = "$BASE_FILE_URL/getIMG?path=$imagePath"

    companion object
    {
        fun map(
            arl: List<ArtistModel>,
            all: List<AlbumModel>,
            tll: List<TrackModel>
        ): List<SearchScreenModel>
        {
            val mappedData = emptyList<SearchScreenModel>().toMutableList()

            for (am in arl) {
                mappedData.add(
                    SearchScreenModel(
                        id = am.id,
                        name = am.name,
                        title = Title.ARTIST,
                        titleID = Title.ARTISTIDTITLE,
                        artistName = am.name,
                        imagePath = am.imagePath.replace("\\", "/")
                    )
                )

            }

            for (al in all)
            {
                mappedData.add(
                    SearchScreenModel(
                        id = al.id,
                        name = al.name,
                        title = Title.ALBUM,
                        titleID = Title.ALBUMIDTITLE,
                        artistName = al.artistName,
                        imagePath = al.imagePath.replace("\\", "/")
                    )
                )
            }

            for (t in tll ) {
                mappedData.add(
                    SearchScreenModel(
                        id = t.id,
                        name = t.name,
                        title = Title.TRACK,
                        titleID = Title.TRACKIDTITLE,
                        artistName = t.artistName,
                        imagePath = t.imagePath.replace("\\", "/")
                    )
                )
            }

            return mappedData
        }
    }
}

