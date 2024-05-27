package com.example.musicmobileapp.di

import android.app.Activity
import android.app.Application
import android.content.Context
import android.media.MediaCodec
import android.media.MediaFormat
import androidx.media3.common.C
import androidx.media3.common.util.UnstableApi
import androidx.media3.exoplayer.DefaultLoadControl
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.exoplayer.audio.MediaCodecAudioRenderer
import androidx.media3.exoplayer.mediacodec.MediaCodecSelector
import androidx.media3.exoplayer.upstream.DefaultAllocator
import androidx.media3.extractor.ts.TsExtractor.Mode
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import com.example.musicmobileapp.controllers.AuthController
import com.example.musicmobileapp.controllers.MusicPlayerController
import com.example.musicmobileapp.network.AuthApiInterface
import com.example.musicmobileapp.network.MainAPIController
import com.example.musicmobileapp.network.MusicApiInterface
import com.example.musicmobileapp.network.api.ApiRoutes
import com.example.musicmobileapp.network.api.AuthApi
import com.example.musicmobileapp.network.api.MusicApi
import com.example.musicmobileapp.network.service.MusicPlayerService
import com.example.musicmobileapp.security.UserSecurityManager
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

    @UnstableApi
    @Provide
    open fun provideExoPlayer(context: Context) : ExoPlayer
    {
        val customAllocator = DefaultAllocator(true, C.DEFAULT_BUFFER_SEGMENT_SIZE * 2)
        val loadControl = DefaultLoadControl.Builder()
            .setAllocator(customAllocator)
            .setBufferDurationsMs(5000, 25000, 5000, 5000)
            .setTargetBufferBytes(1024 * 1024 * 4)
            .build()

        return ExoPlayer.Builder(context)
            .setLoadControl(loadControl).build()
    }

    @Provide
    open fun provideUserSecurityManager(context: Context) : UserSecurityManager
    {
        val masterKey = MasterKey.Builder(context)
            .setKeyScheme(MasterKey.KeyScheme.AES256_GCM).build()

        val sharedPreferences = EncryptedSharedPreferences.create(
            context,
            "secure_prefs",
            masterKey,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )
        return UserSecurityManager(sharedPreferences)
    }

}