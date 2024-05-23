package com.example.musicmobileapp.network

import com.example.musicmobileapp.di.ApiModule
import com.example.musicmobileapp.models.SearchModel
import com.example.musicmobileapp.models.UserModel
import com.example.musicmobileapp.network.api.AlbumApi
import com.example.musicmobileapp.network.api.ArtistApi
import com.example.musicmobileapp.network.api.AuthApi
import com.example.musicmobileapp.network.api.MusicApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

import okhttp3.ResponseBody
import retrofit2.Response
import java.io.InputStream

class MainAPIController(private val apiModule: ApiModule) : AuthApiInterface,MusicApiInterface,SearchApiInterface{

    private val authApi : AuthApi
        get() = apiModule.provideAuthApi()

    private val musicApi : MusicApi
        get() = apiModule.provideMusicApi()

    private val artistApi : ArtistApi
        get() = apiModule.provideArtistApi()

    private val albumApi : AlbumApi
        get() = apiModule.provideAlbumApi()


    override suspend fun auth(userModel: UserModel): Response<ResponseBody> {
        return authApi.auth(userModel)
    }

    override suspend fun reg(userModel: UserModel): Response<ResponseBody> {
        return authApi.reg(userModel)
    }

    override suspend fun streamMusic(id: Long): InputStream {
        return musicApi.streamMusic(id)
    }

    override suspend fun searchData(name: String): Flow<List<SearchModel>> =
        flow {
            val artistList = artistApi.getByName(name)
            val albumList = albumApi.getByName(name)
            emit(SearchModel.map(artistList, albumList))
        }


}


interface AuthApiInterface
{
    suspend fun auth(userModel: UserModel): Response<ResponseBody>

    suspend fun reg(userModel: UserModel): Response<ResponseBody>
}

interface MusicApiInterface
{
    suspend fun streamMusic(id : Long) : InputStream
}

interface SearchApiInterface
{
    suspend fun searchData(name : String): Flow<List<SearchModel>>
}