package com.example.musicmobileapp.di

import android.app.Activity
import android.app.Application
import android.content.Context
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.extractor.ts.TsExtractor.Mode
import com.example.musicmobileapp.controllers.AuthController
import com.example.musicmobileapp.controllers.MusicPlayerController
import com.example.musicmobileapp.network.AuthApiInterface
import com.example.musicmobileapp.network.MainAPIController
import com.example.musicmobileapp.network.MusicApiInterface
import com.example.musicmobileapp.network.api.ApiRoutes
import com.example.musicmobileapp.network.api.AuthApi
import com.example.musicmobileapp.network.api.MusicApi
import com.example.musicmobileapp.network.service.MusicPlayerService
import com.github.klee0kai.stone.annotations.component.Component
import com.github.klee0kai.stone.annotations.module.BindInstance
import com.github.klee0kai.stone.annotations.module.Module
import com.github.klee0kai.stone.annotations.module.Provide
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory



@Module
open class CoreModule () {


    @Provide(cache = Provide.CacheType.Strong)
    open fun provideMoshi(): Moshi {
        return Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()
    }

    @Provide(cache = Provide.CacheType.Factory)
    open fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(ApiRoutes.BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create(provideMoshi()))
            .build()
    }

    @Provide
    open fun provideExoPlayer(context: Context) : ExoPlayer
    {
        return ExoPlayer.Builder(context).build()
    }




}