package com.example.musicmobileapp.di

import android.content.Context
import androidx.media3.exoplayer.ExoPlayer
import com.example.musicmobileapp.controllers.AlbumController
import com.example.musicmobileapp.controllers.AuthController
import com.example.musicmobileapp.controllers.MusicPlayerController
import com.example.musicmobileapp.controllers.SearchScreenController
import com.example.musicmobileapp.network.AlbumApiInterface
import com.example.musicmobileapp.network.AuthApiInterface
import com.example.musicmobileapp.network.MainAPIController
import com.example.musicmobileapp.network.MusicApiInterface
import com.example.musicmobileapp.network.SearchApiInterface
import com.example.musicmobileapp.network.service.MusicPlayerService
import com.github.klee0kai.stone.annotations.module.BindInstance
import com.github.klee0kai.stone.annotations.module.Module
import com.github.klee0kai.stone.annotations.module.Provide
import javax.inject.Inject


@Module()
interface ControllersModule {


    @BindInstance
    fun mApiController(ma : MainAPIController? = null) : MainAPIController
    @BindInstance
    fun provideExoPLayer( exoPlayer: ExoPlayer? = null): ExoPlayer

    @Provide(cache = Provide.CacheType.Soft)
    fun provideAuthController(authApiInterface : AuthApiInterface = mApiController()): AuthController

    @Provide(cache = Provide.CacheType.Soft)
    fun provideMusicPlayerController(musicApiInterface: MusicApiInterface = mApiController(), musicPlayerService: MusicPlayerService = this.provideMusicPlayerService()): MusicPlayerController

    @Provide(cache = Provide.CacheType.Soft)
    fun provideMusicPlayerService(exoPlayer: ExoPlayer = provideExoPLayer()): MusicPlayerService

    @Provide(cache = Provide.CacheType.Soft)
    fun provideSearchController(searchApiInterface: SearchApiInterface = mApiController()) : SearchScreenController

    @Provide(cache = Provide.CacheType.Soft)
    fun provideAlbumController(albumApiInterface: AlbumApiInterface = mApiController()) : AlbumController



}



