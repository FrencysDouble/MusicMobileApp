package com.example.musicmobileapp.models

data class AlbumModel(
    val id: Int,
    val name: String,
    val artistId: Int,
    val artistName : String,
    val imagePath: String,
    val trackNames: List<String>,
    val tracks: List<TrackModel>
)


