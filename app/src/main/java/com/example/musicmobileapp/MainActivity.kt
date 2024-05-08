package com.example.musicmobileapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.musicmobileapp.di.ApiModule
import com.example.musicmobileapp.di.ControllersModule
import com.example.musicmobileapp.di.DaggerApiModule
import com.example.musicmobileapp.di.DaggerControllersModule
import com.example.musicmobileapp.main_ui.navigation.MainNavigation
import com.example.musicmobileapp.network.MainAPIController
import com.example.musicmobileapp.ui.theme.MusicMobileAppTheme
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    lateinit var controllersModule: ControllersModule
    lateinit var apiModule: ApiModule
    private lateinit var mainAPIController: MainAPIController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        controllersModule = DaggerControllersModule.create()
        apiModule = DaggerApiModule.create()
        mainAPIController = MainAPIController(apiModule.provideAuthApi())

        setContent {
            MusicMobileAppTheme {
                MainNavigation(controllersModule)
            }
        }
    }
}