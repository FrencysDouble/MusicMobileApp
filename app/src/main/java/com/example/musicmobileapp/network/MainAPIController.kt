package com.example.musicmobileapp.network

import android.util.Log
import com.example.musicmobileapp.di.ApiModule
import com.example.musicmobileapp.models.dto.PlaylistDTO
import com.example.musicmobileapp.models.dto.TrackModel
import com.example.musicmobileapp.models.dto.UserDTO
import com.example.musicmobileapp.models.screens.SearchScreenModel
import com.example.musicmobileapp.models.dto.UserModel
import com.example.musicmobileapp.models.screens.AlbumScreenModel
import com.example.musicmobileapp.models.screens.PlaylistScreenModel
import com.example.musicmobileapp.network.api.AlbumApi
import com.example.musicmobileapp.network.api.ApiResponse
import com.example.musicmobileapp.network.api.ArtistApi
import com.example.musicmobileapp.network.api.AuthApi
import com.example.musicmobileapp.network.api.MusicApi
import com.example.musicmobileapp.network.api.PlaylistApi
import com.example.musicmobileapp.network.api.handleApiResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

import okhttp3.ResponseBody
import java.io.InputStream

class MainAPIController(private val apiModule: ApiModule) : AuthApiInterface,MusicApiInterface,SearchApiInterface,AlbumApiInterface,PlaylistApiInterface {

    private val authApi: AuthApi
        get() = apiModule.provideAuthApi()

    private val musicApi: MusicApi
        get() = apiModule.provideMusicApi()

    private val artistApi: ArtistApi
        get() = apiModule.provideArtistApi()

    private val albumApi: AlbumApi
        get() = apiModule.provideAlbumApi()

    private val playlistApi: PlaylistApi
        get() = apiModule.providePlaylistApi()


    private suspend fun <T> responseCheck(
        call: suspend () -> ApiResponse<T>
    ): T {
        return when (val response = call()) {
            is ApiResponse.Success -> response.data
            is ApiResponse.Error -> {
                Log.d("MainApiController", response.errorMessage)
                throw Exception(response.errorMessage)
            }
            is ApiResponse.Loading -> {
                Log.d("MainApiController", "Loading...")
                throw Exception("Loading state encountered")
            }
        }
    }



    override suspend fun auth(userModel: UserModel): Flow<ApiResponse<UserDTO>> =
        flow {
            val response = handleApiResponse(
                call = { authApi.auth(userModel) }
            )
            emit(response)
        }

    override suspend fun reg(userModel: UserModel): Flow<ApiResponse<UserDTO>> =
        flow {
            val response = handleApiResponse(
                call = { authApi.reg(userModel) }
            )
            emit(response)
        }

    override suspend fun streamMusic(id: Long): InputStream {
        return musicApi.streamMusic(id)
    }

    override suspend fun getById(id: Long): Flow<ApiResponse<TrackModel>> =
        flow {
            val response = handleApiResponse(
                call = {musicApi.getById(id)}
            )
            emit(response)
        }


    override suspend fun searchData(name: String): Flow<ApiResponse<List<SearchScreenModel>>> =
        flow {
            try {
                val artist = responseCheck(call = { artistApi.getByName(name) })
                val album = responseCheck(call = { albumApi.getByName(name) })
                val music = responseCheck(call = { musicApi.getByName(name) })
                emit(ApiResponse.Success(SearchScreenModel.map(artist,album, music)))
            }
            catch (e: NullPointerException)
            {
                e.printStackTrace()
                emit(ApiResponse.Error(e.message ?: "Null Pointer"))
            }
        }

    override suspend fun getAlbum(id: Long): Flow<ApiResponse<AlbumScreenModel>> =
        flow {
            try {

                val album = responseCheck(call = { albumApi.getById(id)})
                val artist = responseCheck(call = { artistApi.getById(album.artistId.toLong())})
                emit(ApiResponse.Success(AlbumScreenModel.map(album, artist)))
            }
            catch (e: NullPointerException)
            {
                e.printStackTrace()
                emit(ApiResponse.Error(e.message ?: "Album"))
            }
        }

    override suspend fun createPlaylist(playlistDTO: PlaylistDTO): Flow<ApiResponse<ResponseBody>> =
        flow {
            val response = handleApiResponse (
                call = {playlistApi.createPlaylist(playlistDTO)}
            )
            emit(response)
        }

    override suspend fun addTrackPlaylist(id: Long, trackId: Long) : Flow<ApiResponse<ResponseBody>> =
        flow {
            val response = handleApiResponse(
                call = {playlistApi.addTrackPlaylist(id,trackId)}
            )
            emit(response)
        }

    override suspend fun addTrackPlaylist(idList: List<Long>, trackId: Long) : Flow<ApiResponse<ResponseBody>> =
        flow {
            val response = handleApiResponse(
                call = {playlistApi.addTrackPlaylists(idList,trackId)}
            )
            emit(response)
        }

    override suspend fun getAllByCreator(id: Long): Flow<ApiResponse<List<PlaylistScreenModel>>> =
        flow {
            val response = handleApiResponse (
                call = {playlistApi.getAllCreatorId(id)},
                map = {PlaylistScreenModel.map(it)}
            )
            emit(response)
        }

    override suspend fun getByPlaylistId(id: Long): Flow<ApiResponse<PlaylistScreenModel>> =
    flow {
        val response = handleApiResponse (
            call = {playlistApi.getByPlaylistId(id)},
            map = {PlaylistScreenModel.mapOne(it)}
        )
        emit(response)
    }
}


interface AuthApiInterface
{
    suspend fun auth(userModel: UserModel): Flow<ApiResponse<UserDTO>>

    suspend fun reg(userModel: UserModel): Flow<ApiResponse<UserDTO>>
}

interface MusicApiInterface
{
    suspend fun streamMusic(id : Long) : InputStream

    suspend fun getById(id: Long) : Flow<ApiResponse<TrackModel>>
}

interface SearchApiInterface
{
    suspend fun searchData(name : String): Flow<ApiResponse<List<SearchScreenModel>>>
}

interface AlbumApiInterface
{
    suspend fun getAlbum(id: Long) : Flow<ApiResponse<AlbumScreenModel>>
}

interface PlaylistApiInterface
{
    suspend fun createPlaylist(playlistDTO: PlaylistDTO) : Flow<ApiResponse<ResponseBody>>

    suspend fun addTrackPlaylist(id: Long,trackId : Long) : Flow<ApiResponse<ResponseBody>>

    suspend fun addTrackPlaylist(idList: List<Long>,trackId : Long) : Flow<ApiResponse<ResponseBody>>

    suspend fun getAllByCreator(id: Long) : Flow<ApiResponse<List<PlaylistScreenModel>>>

    suspend fun getByPlaylistId(id : Long) : Flow<ApiResponse<PlaylistScreenModel>>
}