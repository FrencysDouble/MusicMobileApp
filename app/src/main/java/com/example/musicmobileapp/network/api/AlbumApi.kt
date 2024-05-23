package com.example.musicmobileapp.network.api

import com.example.musicmobileapp.models.AlbumModel
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.http.GET
import retrofit2.http.Query

class AlbumApi(private val retrofit: Retrofit) {

    private val service :AlbumApiPoints= retrofit.create(AlbumApiPoints::class.java)

    suspend fun getByName(name: String): Response<List<AlbumModel>> = service.findByName(name)
}


interface AlbumApiPoints {

    @GET(ApiRoutes.ALBUMGETNAME)
    suspend fun findByName(
        @Query("name") name : String
    ): Response<List<AlbumModel>>
}