package com.example.musicmobileapp.network.api

import com.example.musicmobileapp.models.dto.ArtistModel
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

class ArtistApi(private val retrofit: Retrofit) {

    private val service :ArtistApiPoints= retrofit.create(ArtistApiPoints::class.java)

    suspend fun getByName(name: String): Response<List<ArtistModel>> = service.findByName(name)

    suspend fun getById(id: Long): Response<ArtistModel> = service.findById(id)
}


interface ArtistApiPoints {

    @GET(ApiRoutes.ARTISTGETNAME)
    suspend fun findByName(
        @Query("name") name : String
    ): Response<List<ArtistModel>>

    @GET(ApiRoutes.ARTISTGETBYID)
    suspend fun findById(
        @Path("id") id : Long
    ): Response<ArtistModel>
}