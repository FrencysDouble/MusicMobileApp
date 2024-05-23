package com.example.musicmobileapp.models

import android.util.Log
import retrofit2.Response

data class SearchModel(

    val id : Int,
    val name : String,
    val title : String,
    val artistName : String,
    val imagePath : String

)
{
    companion object
    {
        fun map(arl: Response<List<ArtistModel>>, all: Response<List<AlbumModel>>): List<SearchModel>
        {
            val artistList = arl.body() ?: emptyList()
            val albumList = all.body() ?: emptyList()
            val mappedData = emptyList<SearchModel>().toMutableList()

            for (am in artistList) {
                mappedData.add(
                    SearchModel(
                        id = am.id,
                        name = am.name,
                        title = "Исполнитель",
                        artistName = am.name,
                        imagePath = am.imagePath
                    )
                )

            }

            for (al in albumList)
            {
                mappedData.add(
                    SearchModel(
                        id = al.id,
                        name = al.name,
                        title = "Альбом",
                        artistName = al.artistName,
                        imagePath = al.imagePath
                    )
                )
            }



            for (al in albumList)
            {
                for (t in al.tracks)
                {
                    mappedData.add(
                        SearchModel(
                            id = t.id,
                            name = t.name,
                            title = "Трек",
                            artistName = t.artistName,
                            imagePath = t.imagePath
                        )
                    )
                }
            }

            return mappedData
        }
    }
}

