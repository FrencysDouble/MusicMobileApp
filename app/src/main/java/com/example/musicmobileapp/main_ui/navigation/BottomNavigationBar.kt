package com.example.musicmobileapp.main_ui.navigation

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemColors
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.example.musicmobileapp.R
import com.example.musicmobileapp.ui.theme.mainBackground
import com.example.musicmobileapp.ui.theme.mainPrimary

@Composable
fun BottomNavigationBar(navController: NavHostController)
{
    NavigationBar(containerColor = mainBackground) {
        val items = listOf(
            Routes.NavBar.Main,
            Routes.NavBar.Search,
            Routes.NavBar.Home
        )
        val currentRoute = navController.currentBackStackEntry?.destination?.route
        items.forEach { item ->
            NavigationBarItem(selected = currentRoute == item.route,
                onClick = { navController.navigate(item.route)},
                icon = { Icon(painter = painterResource(id = item.icon), contentDescription ="" ) },
                label = { Text(text = item.title)},
                colors = NavigationBarItemColors(
                    selectedIconColor = mainPrimary,
                    selectedTextColor = mainPrimary,
                    selectedIndicatorColor = mainBackground,
                    unselectedIconColor = Color.Gray,
                    unselectedTextColor = Color.Gray,
                    disabledIconColor = Color.Gray,
                    disabledTextColor = Color.Gray)
            )
        }
    }
}