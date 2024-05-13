package com.example.musicmobileapp.main_ui

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.example.musicmobileapp.main_ui.navigation.BottomNavigationBar
import com.example.musicmobileapp.main_ui.navigation.Routes


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(navController: NavHostController)
{

    Scaffold(modifier = Modifier.fillMaxSize(), bottomBar = { BottomNavigationBar(navController = navController) }) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text(text = "MainScreen")
            Button(onClick = { navController.navigate(Routes.MusicPlayerScreen.route)}) {
                
            }
        }
    }

}