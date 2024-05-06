package com.example.musicmobileapp.di

import com.example.musicmobileapp.network.MainAPIController
import com.example.musicmobileapp.network.api.AuthApi
import dagger.Binds
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit


@Module
interface ApiModule {

    @Binds
    fun provideRetrofit(retrofit: Retrofit? = null): Retrofit

    @Binds
    fun provideAuthApi(retrofit: Retrofit? = null) : AuthApi{
        return AuthApi(retrofit)
    }


}