package com.example.musicmobileapp.main_ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.musicmobileapp.di.ControllersModule
import com.example.musicmobileapp.main_ui.AlbumScreen
import com.example.musicmobileapp.main_ui.ArtistScreen
import com.example.musicmobileapp.main_ui.AuthScreen
import com.example.musicmobileapp.main_ui.ChooseScreen
import com.example.musicmobileapp.main_ui.HomeScreen
import com.example.musicmobileapp.main_ui.LoadingScreen
import com.example.musicmobileapp.main_ui.MainScreen
import com.example.musicmobileapp.main_ui.MusicPlayerScreen
import com.example.musicmobileapp.main_ui.PlaylistCreationScreen
import com.example.musicmobileapp.main_ui.PlaylistScreen
import com.example.musicmobileapp.main_ui.RegScreen
import com.example.musicmobileapp.main_ui.SearchScreen
import kotlinx.coroutines.delay

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
            LoadingScreen.Loading()
        }
        composable(Routes.Identification.Reg.route) {
            RegScreen(navController,controllersModule.provideAuthController())
        }
        composable(Routes.Identification.Auth.route) {
            AuthScreen(navController,controllersModule.provideAuthController())
        }
        composable(Routes.NavBar.Main.route) {
            MainScreen(navController,controllersModule.providePlaylistController(),controllersModule.provideArtistController() )
        }
        composable(Routes.NavBar.Search.route) {
            SearchScreen(navController,controllersModule.provideSearchController(),controllersModule.provideMoreDialogController(),controllersModule.provideMusicPlayerService(),controllersModule.provideSelectedTrackItem(),controllersModule.provideMusicPlayerController())
        }
        composable(Routes.NavBar.Home.route) {
            HomeScreen(navController,controllersModule.providePlaylistController())
        }
        composable(route = Routes.MusicPlayerScreen.route + "/{trackId}",
            arguments = listOf(navArgument("trackId"){type = NavType.StringType})
        ) { backStackEntry ->
            val trackId = backStackEntry.arguments?.getString("trackId")
            MusicPlayerScreen(navController,controllersModule.provideMusicPlayerController(),controllersModule.provideExoPLayer(),trackId?: "",controllersModule.provideMusicPlayerService(),controllersModule.provideMoreDialogController())
        }
        composable(route = Routes.AlbumScreen.route + "/{albumId}",
            arguments = listOf(navArgument("albumId") { type = NavType.StringType })
        ) { backStackEntry ->
            val albumId = backStackEntry.arguments?.getString("albumId")
            AlbumScreen(navController, albumId ?: "",controllersModule.provideAlbumController())
        }
        composable(route = Routes.PlaylistCreationScreen.route)
        {
            PlaylistCreationScreen(navController,controllersModule.providePlaylistController())
        }
        composable(
            route = Routes.PlaylistScreen.route + "/{playlistId}",
            arguments = listOf(navArgument("playlistId") { type = NavType.StringType }))
        { backStackEntry ->
            val playlistId = backStackEntry.arguments?.getString("playlistId")
            PlaylistScreen(navController,playlistId,controllersModule.providePlaylistController())
        }

        composable(route = Routes.PlaylistChooseScreen.route + "/{trackId}",
            arguments = listOf(navArgument("trackId") { type = NavType.StringType })
        ) { backStackEntry ->
            val trackId = backStackEntry.arguments?.getString("trackId")
            ChooseScreen(navController, trackId ?: "",controllersModule.providePlaylistController())
        }
        composable(route = Routes.ArtistScreen.route + "/{artistId}",
            arguments = listOf(navArgument("artistId") { type = NavType.StringType })
        ) { backStackEntry ->
            val artistId = backStackEntry.arguments?.getString("artistId")
            ArtistScreen(navController, artistId ?: "",controllersModule.provideArtistController())
        }

    }
}


