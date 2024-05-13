package com.example.musicmobileapp.network.api

import com.example.musicmobileapp.models.UserModel
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Streaming
import java.io.InputStream

class MusicApi(private val retrofit: Retrofit) {

    private val service: MusicApiPoints = retrofit.create(MusicApiPoints::class.java)

    suspend fun streamMusic(id: Long): InputStream = service.streamMusic(id).byteStream()

}

interface MusicApiPoints
{
    @GET(ApiRoutes.STREAM)
    @Streaming
    suspend fun streamMusic(
        @Path("id") id:Long
    ): ResponseBody
}