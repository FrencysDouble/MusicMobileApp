package com.example.musicmobileapp.network.api

import com.example.musicmobileapp.models.dto.AlbumModel
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

class AlbumApi(private val retrofit: Retrofit) {

    private val service :AlbumApiPoints= retrofit.create(AlbumApiPoints::class.java)

    suspend fun getByName(name: String): Response<List<AlbumModel>> = service.findByName(name)

    suspend fun getById(id: Long): Response<AlbumModel> = service.findById(id)

}


interface AlbumApiPoints {

    @GET(ApiRoutes.ALBUMGETNAME)
    suspend fun findByName(
        @Query("name") name : String
    ): Response<List<AlbumModel>>


    @GET(ApiRoutes.ALBUMGETBYID)
    suspend fun findById(
        @Path("id") id : Long
    ): Response<AlbumModel>
}