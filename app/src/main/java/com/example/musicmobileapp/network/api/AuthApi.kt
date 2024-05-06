package com.example.musicmobileapp.network.api

import com.example.musicmobileapp.models.UserModel
import okhttp3.Response
import retrofit2.Retrofit
import retrofit2.http.Body
import retrofit2.http.POST

class AuthApi(retrofit: Retrofit?) {

    private val service :AuthApiConnections = retrofit!!.create(AuthApiConnections::class.java)
    suspend fun auth(user:UserModel): Response = service.auth(user)
    interface AuthApiConnections
    {
        @POST(ApiRoutes.AUTH)
        suspend fun auth(
            @Body user : UserModel
        ): Response

    }
}