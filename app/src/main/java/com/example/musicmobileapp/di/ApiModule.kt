package com.example.musicmobileapp.di

import com.example.musicmobileapp.network.AuthApiInterface
import com.example.musicmobileapp.network.MainAPIController
import com.example.musicmobileapp.network.MusicApiInterface
import com.example.musicmobileapp.network.api.AuthApi
import com.example.musicmobileapp.network.api.MusicApi
import com.github.klee0kai.stone.annotations.module.BindInstance
import com.github.klee0kai.stone.annotations.module.Module
import com.github.klee0kai.stone.annotations.module.Provide
import retrofit2.Retrofit
import javax.inject.Inject

@Module
interface ApiModule {


    @BindInstance(cache = BindInstance.CacheType.Strong)
    fun retrofit(r: Retrofit? = null): Retrofit

    @Provide(cache = Provide.CacheType.Soft)
    fun provideAuthApi(retrofit: Retrofit = this.retrofit() ) : AuthApi

    @Provide(cache = Provide.CacheType.Soft)
    fun provideMusicApi(retrofit: Retrofit = this.retrofit()) : MusicApi

}

