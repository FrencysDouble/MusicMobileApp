package com.example.musicmobileapp.network

import com.example.musicmobileapp.di.ApiModule
import com.example.musicmobileapp.models.UserModel
import com.example.musicmobileapp.network.api.AuthApi
import com.example.musicmobileapp.network.api.MusicApi

import okhttp3.ResponseBody
import retrofit2.Response
import java.io.InputStream

class MainAPIController(private val apiModule: ApiModule) : AuthApiInterface,MusicApiInterface{

    private val authApi : AuthApi
        get() = apiModule.provideAuthApi()

    private val musicApi : MusicApi
        get() = apiModule.provideMusicApi()


    override suspend fun auth(userModel: UserModel): Response<ResponseBody> {
        return authApi.auth(userModel)
    }

    override suspend fun reg(userModel: UserModel): Response<ResponseBody> {
        return authApi.reg(userModel)
    }

    override suspend fun streamMusic(id: Long): InputStream {
        return musicApi.streamMusic(id)
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