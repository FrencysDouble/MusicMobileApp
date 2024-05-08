package com.example.musicmobileapp.network

import com.example.musicmobileapp.models.UserModel
import com.example.musicmobileapp.network.api.AuthApi

import okhttp3.ResponseBody
import retrofit2.Response
import javax.inject.Inject

class MainAPIController(private val authApi: AuthApi) : AuthApiInterface {


    override suspend fun auth(userModel: UserModel): Response<ResponseBody> {
        return authApi.auth(userModel)
    }

    override suspend fun reg(userModel: UserModel): Response<ResponseBody> {
        return authApi.reg(userModel)
    }
}


interface AuthApiInterface
{
    suspend fun auth(userModel: UserModel): Response<ResponseBody>

    suspend fun reg(userModel: UserModel): Response<ResponseBody>
}