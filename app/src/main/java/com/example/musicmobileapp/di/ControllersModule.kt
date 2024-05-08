package com.example.musicmobileapp.di

import com.example.musicmobileapp.controllers.AuthController
import com.example.musicmobileapp.controllers.MusicPlayerController

import dagger.Component


@Component(modules = [CoreModule::class])
interface ControllersModule {
    fun provideAuthController() : AuthController

    fun provideMusicPlayerController() : MusicPlayerController

}



