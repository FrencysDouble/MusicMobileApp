package com.example.musicmobileapp.network.api

import com.example.musicmobileapp.models.dto.UserModel
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.http.Body
import retrofit2.http.POST

class AuthApi(private val retrofit: Retrofit) {

    private val service :AuthApiPoints= retrofit.create(AuthApiPoints::class.java)
    suspend fun auth(user: UserModel): Response<ResponseBody> = service.auth(user)
    suspend fun reg(user: UserModel): Response<ResponseBody> = service.reg(user)
}


interface AuthApiPoints
{
    @POST(ApiRoutes.AUTH)
    suspend fun auth(
        @Body user : UserModel
    ): Response<ResponseBody>

    @POST(ApiRoutes.REGISTR)
    suspend fun reg(
        @Body user : UserModel
    ): Response<ResponseBody>
}
