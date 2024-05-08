package com.example.musicmobileapp.di

import com.example.musicmobileapp.controllers.AuthController
import com.example.musicmobileapp.controllers.MusicPlayerController
import com.example.musicmobileapp.network.AuthApiInterface
import com.example.musicmobileapp.network.MainAPIController
import com.example.musicmobileapp.network.MusicApiInterface
import com.example.musicmobileapp.network.api.ApiRoutes
import com.example.musicmobileapp.network.api.AuthApi
import com.example.musicmobileapp.network.api.MusicApi
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory



@Module
@InstallIn(SingletonComponent::class)
class CoreModule {

    @Provides
    fun provideMoshi(): Moshi {
        return Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()
    }

    @Provides
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(ApiRoutes.BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create(provideMoshi()))
            .build()
    }


    //Main API Controller
    @Provides
    fun provideAuthApiInterface(mainAPIController: MainAPIController): AuthApiInterface {
        return mainAPIController
    }

    @Provides
    fun provideMusicApiInterface(mainAPIController: MainAPIController) : MusicApiInterface {
        return mainAPIController
    }

    @Provides
    fun provideMainAPIController(authApi: AuthApi, musicApi: MusicApi): MainAPIController {
        return MainAPIController(authApi,musicApi)
    }




    //API
    @Provides
    fun provideAuthApi(retrofit: Retrofit) : AuthApi {
        return AuthApi(retrofit)
    }

    @Provides
    fun provideMusicApi(retrofit: Retrofit) : MusicApi {
        return MusicApi(retrofit)
    }


    //Controllers
    @Provides
    fun provideAuthController(authApiInterface: AuthApiInterface): AuthController {
        return AuthController(authApiInterface)
    }

    @Provides
    fun provideMusicController(musicApiInterface: MusicApiInterface): MusicPlayerController {
       return MusicPlayerController(musicApiInterface)
    }

}