package com.example.musicmobileapp.main_ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.musicmobileapp.controllers.AuthController
import com.example.musicmobileapp.controllers.MusicPlayerController
import com.example.musicmobileapp.di.ControllersModule
import com.example.musicmobileapp.main_ui.AuthScreen
import com.example.musicmobileapp.main_ui.HomeScreen
import com.example.musicmobileapp.main_ui.LoadingScreen
import com.example.musicmobileapp.main_ui.MainScreen
import com.example.musicmobileapp.main_ui.MusicPlayerScreen
import com.example.musicmobileapp.main_ui.RegScreen
import com.example.musicmobileapp.main_ui.SearchScreen
import kotlinx.coroutines.delay
import javax.inject.Inject

@Composable
fun MainNavigation(controllersModule: ControllersModule) {


    val navController = rememberNavController()

    val isUserLoggedIn = false


    LaunchedEffect(Unit) {
        delay(1000)
        if (isUserLoggedIn) {
            navController.navigate(Routes.NavBar.Main.route) {
                popUpTo(navController.graph.startDestinationId)
                launchSingleTop = true
            }
        } else {
            navController.navigate(Routes.Identification.Auth.route) {
                popUpTo(navController.graph.startDestinationId)
                launchSingleTop = true
            }
        }
    }


    NavHost(navController, startDestination = Routes.Loading.route) {
        composable(Routes.Loading.route) {
            LoadingScreen(navController)
        }
        composable(Routes.Identification.Reg.route) {
            RegScreen(navController,controllersModule.provideAuthController())
        }
        composable(Routes.Identification.Auth.route) {
            AuthScreen(navController,controllersModule.provideAuthController())
        }
        composable(Routes.NavBar.Main.route) {
            MainScreen(navController)
        }
        composable(Routes.NavBar.Search.route) {
            SearchScreen(navController)
        }
        composable(Routes.NavBar.Home.route) {
            HomeScreen(navController)
        }
        composable(Routes.MusicPlayerScreen.route)
        {
            MusicPlayerScreen(navController,controllersModule.provideMusicPlayerController())
        }
    }
}


