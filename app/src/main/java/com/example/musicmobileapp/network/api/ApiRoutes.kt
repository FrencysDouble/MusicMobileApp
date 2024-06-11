package com.example.musicmobileapp.network.api

object ApiRoutes {

    //BASE URL
    const val BASE_URL = "http://10.0.2.2:8080/api/v1/"

    const val BASE_FILE_URL = "http://10.0.2.2:8080/api/v1/file"

    // Auth API

    const val AUTH = "user/auth"
    const val REGISTR = "user/reg"

    // Music API

    const val STREAM = "file/stream/{id}"
    const val TRACKGETNAME = "track/getByName"
    const val TRACKGETID = "track/get/{id}"

    // Artist API

    const val ARTISTGETNAME = "artist/getByName"
    const val ARTISTGETBYID = "artist/getById/{id}"

    // Album API

    const val ALBUMGETNAME = "album/getByName"
    const val ALBUMGETBYID = "album/getById/{id}"

    // Playlist API

    const val PLAYLISTPOST = "playlist/create"
    const val PLAYLISTGETALL = "playlist/getAll/{id}"
    const val PLAYLISTGETBYID = "playlist/get/{id}"
    const val PLAYLISTADDTRACK = "playlist/addTrack"
    const val PLAYLISTADDTRACKS = "playlist/addTracks"

}