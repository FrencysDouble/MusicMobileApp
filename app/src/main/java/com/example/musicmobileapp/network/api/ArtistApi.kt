package com.example.musicmobileapp.network.api

import com.example.musicmobileapp.models.ArtistModel
import com.example.musicmobileapp.models.UserModel
import kotlinx.coroutines.flow.Flow
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

class ArtistApi(private val retrofit: Retrofit) {

    private val service :ArtistApiPoints= retrofit.create(ArtistApiPoints::class.java)

    suspend fun getByName(name: String): Response<List<ArtistModel>> = service.findByName(name)
}


interface ArtistApiPoints {

    @GET(ApiRoutes.ARTISTGETNAME)
    suspend fun findByName(
        @Query("name") name : String
    ): Response<List<ArtistModel>>
}