package com.example.musicmobileapp.di

import com.example.musicmobileapp.network.MainAPIController
import com.example.musicmobileapp.network.api.AuthApi
import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
open class CoreModule {

    @Provides
    fun provideMoshi(): Moshi {
        return Moshi.Builder().build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(Companion.BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create(provideMoshi()))
            .build()
    }

    @Provides
    fun provideMainAPIController(authApi: AuthApi): MainAPIController {
        return MainAPIController(authApi)
    }

    companion object {
        private const val BASE_URL = "https://localhost/"
    }
}