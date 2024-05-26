package com.example.musicmobileapp.main_ui.navigation

import com.example.musicmobileapp.R

object Routes {

    object NavBar {
        object Main :
            BottomNavItem(
                "Дом",
                R.drawable.nav_home,
                "main"
            )

        object Search :
            BottomNavItem(
                "Поиск",
                R.drawable.nav_search,
                "search"
            )

        object Home :
            BottomNavItem(
                "Любимое",
                R.drawable.nav_favorite,
                "home"
            )
    }

    object Identification
    {
        object Reg : Screen("registration")
        object Auth : Screen("authentication")
    }

    object Loading : Screen("loading")

    object AlbumScreen : Screen("album/{albumId}")
    object MusicPlayerScreen : Screen("musicPlayerScreen")
}


sealed class BottomNavItem(
    val title: String,
    val icon: Int,
    val route: String
)

sealed class Screen(val route : String)
