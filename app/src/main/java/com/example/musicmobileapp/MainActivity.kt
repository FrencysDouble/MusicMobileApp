package com.example.musicmobileapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.musicmobileapp.di.ControllersModule
import com.example.musicmobileapp.di.CoreInterface
import com.example.musicmobileapp.di.CoreSupervisor
import com.example.musicmobileapp.di.MyApp
import com.example.musicmobileapp.main_ui.navigation.MainNavigation
import com.example.musicmobileapp.ui.theme.MusicMobileAppTheme
import com.github.klee0kai.stone.Stone


class MainActivity : ComponentActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val coreSupervisor = (application as MyApp).cs

            val controllers = with(coreSupervisor.ApplicationMain())
            {
                getApplicationControllers()
            }

            MusicMobileAppTheme {
                MainNavigation(controllers)
            }
        }
    }
}