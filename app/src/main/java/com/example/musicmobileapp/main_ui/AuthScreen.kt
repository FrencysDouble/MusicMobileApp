package com.example.musicmobileapp.main_ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.example.musicmobileapp.main_ui.navigation.Routes


@Composable
fun AuthScreen(navController: NavHostController)
{
    Surface(modifier = Modifier.fillMaxSize()) {
        Column(modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
            Text(text = "AuthScreen")
            Button(onClick = { navController.navigate(Routes.NavBar.Main.route) })
            {
                Text(text = "Submit")
            }
            Button(onClick = { navController.navigate(Routes.Identification.Reg.route) }) {
                Text(text = "To Registration")
            }
        }
    }
}