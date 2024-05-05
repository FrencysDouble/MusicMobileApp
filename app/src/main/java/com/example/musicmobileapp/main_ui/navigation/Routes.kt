package com.example.musicmobileapp.main_ui.navigation

import com.example.musicmobileapp.R

object Routes {

    object NavBar {
        object Main :
            BottomNavItem(
                "Main",
                R.drawable.ic_home,
                "main"
            )

        object Search :
            BottomNavItem(
                "Search",
                R.drawable.ic_search_24,
                "search"
            )

        object Home :
            BottomNavItem(
                "Home",
                R.drawable.baseline_list_24,
                "home"
            )
    }

    object Identification
    {
        object Reg : Screen("registration")
        object Auth : Screen("authentication")
    }

    object Loading : Screen("loading")
}


sealed class BottomNavItem(
    val title: String,
    val icon: Int,
    val route: String
)

sealed class Screen(val route : String)
