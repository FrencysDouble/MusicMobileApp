package com.example.musicmobileapp.network.api

object ApiRoutes {

    //BASE URL
    const val BASE_URL = "http://10.0.2.2:8080/api/v1/"

    const val BASE_FILE_URL = "http://10.0.2.2:8080/api/v1/file"

    // Auth API

    const val AUTH = "user/auth"
    const val REGISTR = "user/reg"

    // Music API

    const val STREAM = "music/stream/{id}"
    const val TRACKGETNAME = "track/getByName"

    // Artist API

    const val ARTISTGETNAME = "artist/getByName"
    const val ARTISTGETBYID = "artist/getById/{id}"

    // Album API

    const val ALBUMGETNAME = "album/getByName"
    const val ALBUMGETBYID = "album/getById/{id}"

}