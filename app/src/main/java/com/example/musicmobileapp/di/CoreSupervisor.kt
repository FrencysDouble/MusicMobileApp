package com.example.musicmobileapp.di

import android.content.Context
import androidx.annotation.OptIn
import androidx.media3.common.util.UnstableApi
import androidx.media3.exoplayer.ExoPlayer
import com.example.musicmobileapp.network.MainAPIController
import retrofit2.Retrofit

class CoreSupervisor(private val core: CoreInterface) {


    private val context: Context
        get() = core.context()


    private val controllersModule : ControllersModule
        get() = core.controllerModule()


    private val apiModule : ApiModule
        get() = core.apiModule()

    private val coreModule : CoreModule
        get() = core.coreModule()



    inner class ApplicationMain
    {
        @OptIn(UnstableApi::class)
        fun initArchitecture()
        {
            val mapi = MainAPIController(
                apiModule
            )
            controllersModule.mApiController(mapi)

            val exoplayer = coreModule.provideExoPlayer(context)
            controllersModule.provideExoPLayer(exoplayer)

            val securityManager = coreModule.provideUserSecurityManager(context)
            controllersModule.provideSecurityManager(securityManager)

            apiModule.retrofit(coreModule.provideRetrofit())
        }

        fun getApplicationControllers(): ControllersModule = core.controllerModule()
    }
}