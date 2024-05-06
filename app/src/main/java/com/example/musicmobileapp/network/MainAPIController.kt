package com.example.musicmobileapp.network

import com.example.musicmobileapp.di.ApiModule
import com.example.musicmobileapp.models.UserModel
import com.example.musicmobileapp.network.api.AuthApi
import retrofit2.Retrofit
import javax.inject.Inject

class MainAPIController(authApi: AuthApi) : AuthApiInterface{
    @Inject lateinit var apiModule: ApiModule

    private val retrofit: Retrofit
        get() = apiModule.provideRetrofit()

    private val authApi : AuthApi
        get() = apiModule.provideAuthApi()

    override suspend fun auth(userModel: UserModel) {
        authApi.auth(userModel)
    }

}


interface AuthApiInterface
{
    suspend fun auth(userModel: UserModel)
}