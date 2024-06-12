package com.example.musicmobileapp.network.api

import androidx.media3.exoplayer.upstream.CmcdData.StreamType
import com.example.musicmobileapp.models.dto.TrackModel
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.http.Streaming
import java.io.InputStream

class MusicApi(private val retrofit: Retrofit) {

    private val service: MusicApiPoints = retrofit.create(MusicApiPoints::class.java)

    suspend fun streamMusic(id: Long): InputStream = service.streamMusic(id).byteStream()

    suspend fun getByName(name: String): ApiResponse<List<TrackModel>> = handleApiResponse (call = {service.findByName(name) })

    suspend fun getById(id: Long): Response<TrackModel> = service.findById(id)

    suspend fun getByArtistId(id: Long): ApiResponse<List<TrackModel>> = handleApiResponse (call = {service.findByArtistId(id) })

}

interface MusicApiPoints
{
    @GET(ApiRoutes.STREAM)
    @Streaming
    suspend fun streamMusic(
        @Path("id") id:Long
    ): ResponseBody


    @GET(ApiRoutes.TRACKGETNAME)
    suspend fun findByName(
        @Query("name") name : String
    ): Response<List<TrackModel>>

    @GET(ApiRoutes.TRACKGETBYARTISTID)
    suspend fun findByArtistId(
        @Path("id") id : Long
    ): Response<List<TrackModel>>


    @GET(ApiRoutes.TRACKGETID)
    suspend fun findById(
        @Path("id") id : Long
    ): Response<TrackModel>


}