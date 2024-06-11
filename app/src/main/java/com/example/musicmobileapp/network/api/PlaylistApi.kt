package com.example.musicmobileapp.network.api

import com.example.musicmobileapp.models.dto.PlaylistDTO
import com.example.musicmobileapp.models.screens.PlaylistScreenModel
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

class PlaylistApi(private val retrofit: Retrofit) {

    private val service :PlaylistApiPoints= retrofit.create(PlaylistApiPoints::class.java)

    suspend fun createPlaylist(playlistDTO: PlaylistDTO): Response<ResponseBody> = service.createPlaylist(playlistDTO)

    suspend fun addTrackPlaylist(id: Long, trackId: Long) : Response<ResponseBody> = service.addTrackPlaylist(id,trackId)

    suspend fun getAllCreatorId(id:Long) : Response<List<PlaylistScreenModel>> = service.getAllByCreatorId(id)

    suspend fun getByPlaylistId(playlistId : Long) : Response<PlaylistScreenModel> = service.getById(playlistId)

    suspend fun addTrackPlaylists(idList: List<Long>,trackId: Long) : Response<ResponseBody> = service.addTrackPlaylists(idList,trackId)

}

interface PlaylistApiPoints
{

    @POST(ApiRoutes.PLAYLISTPOST)
    suspend fun createPlaylist(
        @Body playlistDTO: PlaylistDTO
    ): Response<ResponseBody>

    @POST(ApiRoutes.PLAYLISTADDTRACK)
    suspend fun addTrackPlaylist(
        @Query("playlistId") id : Long,
        @Query("trackId") trackId : Long
    ): Response<ResponseBody>

    @POST(ApiRoutes.PLAYLISTADDTRACKS)
    suspend fun addTrackPlaylists(
        @Query("playlistId") id : List<Long>,
        @Query("trackId") trackId : Long
    ): Response<ResponseBody>

    @GET(ApiRoutes.PLAYLISTGETALL)
    suspend fun getAllByCreatorId(
        @Path("id") creatorId : Long
    ): Response<List<PlaylistScreenModel>>

    @GET(ApiRoutes.PLAYLISTGETBYID)
    suspend fun getById(
        @Path("id") playlistId : Long
    ): Response<PlaylistScreenModel>

}