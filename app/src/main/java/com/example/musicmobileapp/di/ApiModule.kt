package com.example.musicmobileapp.di

import com.example.musicmobileapp.network.api.AuthApi
import dagger.Component
import retrofit2.Retrofit
import javax.inject.Singleton

@Component(modules = [CoreModule::class])
interface ApiModule {

    fun provideRetrofit(): Retrofit

    @Singleton
    fun provideAuthApi(): AuthApi

}