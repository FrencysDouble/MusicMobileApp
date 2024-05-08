package com.example.musicmobileapp.network.api

import com.example.musicmobileapp.models.UserModel
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Streaming

class MusicApi(private val retrofit: Retrofit) {

    private val service: MusicApiPoints = retrofit.create(MusicApiPoints::class.java)

    suspend fun streamMusic(id: Long): Response<ResponseBody> = service.streamMusic(id)

}

interface MusicApiPoints
{
    @GET(ApiRoutes.AUTH)
    @Streaming
    suspend fun streamMusic(
        @Path("id") id:Long
    ): Response<ResponseBody>
}