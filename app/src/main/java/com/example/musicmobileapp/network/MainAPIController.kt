package com.example.musicmobileapp.network

import com.example.musicmobileapp.models.UserModel
import com.example.musicmobileapp.network.api.AuthApi
import com.example.musicmobileapp.network.api.MusicApi

import okhttp3.ResponseBody
import retrofit2.Response
import javax.inject.Inject

class MainAPIController(private val authApi: AuthApi, private  val musicApi: MusicApi) : AuthApiInterface, MusicApiInterface{


    override suspend fun auth(userModel: UserModel): Response<ResponseBody> {
        return authApi.auth(userModel)
    }

    override suspend fun reg(userModel: UserModel): Response<ResponseBody> {
        return authApi.reg(userModel)
    }

    override suspend fun streamMusic(id: Long): Response<ResponseBody> {
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
    suspend fun streamMusic(id : Long) : Response<ResponseBody>
}