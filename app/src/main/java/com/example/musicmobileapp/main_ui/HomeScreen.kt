package com.example.musicmobileapp.main_ui

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.musicmobileapp.R
import com.example.musicmobileapp.controllers.PlaylistController
import com.example.musicmobileapp.main_ui.navigation.BottomNavigationBar
import com.example.musicmobileapp.main_ui.navigation.Routes
import com.example.musicmobileapp.models.screens.PlaylistScreenModel
import com.example.musicmobileapp.ui.theme.mainBackground
import com.example.musicmobileapp.ui.theme.mainPrimary


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun HomeScreen(navController: NavHostController, controller: PlaylistController) {

    val playlistList by controller.livePlaylistDataList.observeAsState()


    Scaffold(modifier = Modifier.fillMaxSize(), bottomBar = { BottomNavigationBar(navController = navController) }) {
        screen(navController,playlistList)

    }
}




@Composable
fun screen(navController: NavHostController, playlistList: List<PlaylistScreenModel>?) {
    Column(
        Modifier
            .fillMaxSize()
            .background(mainBackground)
            .padding(start = 24.dp, end = 24.dp, top = 16.dp)
    ) {
        UpBar()
        MainList(navController,playlistList)

    }
}

@Composable
fun UpBar()
{
    Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
        IconButton(onClick = { TODO() }) {

            Icon(painter = painterResource(id = R.drawable.pl_back), contentDescription = "")
        }
            Box(modifier = Modifier
                .fillMaxWidth()
                .padding(end = 30.dp), contentAlignment  = Alignment.Center)
            {
                Text(text = stringResource(id = R.string.playlist_playlists), fontSize = 20.sp)
            }

    }
}

@Composable
fun MainList(navController: NavHostController,playlistList: List<PlaylistScreenModel>?) {
    Box(
        modifier = Modifier
            .wrapContentSize()
            .padding(top = 24.dp)
    )
    {
        LazyColumn(verticalArrangement = Arrangement.spacedBy(24.dp))
        {
            item {
                NewPlaylistItem(navController)
            }
            playlistList?.let { playlist ->
                items(playlist) { playlists ->
                    PlaylistItem(playlists,navController)
                }
            }
        }
    }
}

@Composable
fun NewPlaylistItem(navController: NavHostController)
{
    Row(
        Modifier
            .fillMaxWidth()
            .clickable { navController.navigate(Routes.PlaylistCreationScreen.route) },
        verticalAlignment = Alignment.CenterVertically) {
        NewItemBox()
        Text(text = stringResource(id = R.string.playlist_new),Modifier.padding(start = 12.dp))

    }

}

@Composable
fun PlaylistItem(playlists: PlaylistScreenModel, navController: NavHostController)
{
    Row(
        Modifier
            .fillMaxWidth()
            .clickable { navController.navigate("${Routes.PlaylistScreen.route}/${playlists.id}") },
        verticalAlignment = Alignment.CenterVertically) {
        loadImage(url = playlists.imageUrl,
        Modifier
            .size(60.dp)
            .clip(
                RoundedCornerShape(8.dp)
            )
        )
        Text(text = playlists.name,Modifier.padding(start = 12.dp))
        Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.CenterEnd)
        {
            Icon(
                painter = painterResource(id = R.drawable.pl_right_overline),
                contentDescription = ""
            )
        }

    }

}


@Composable
fun NewItemBox()
{
    Box(modifier = Modifier
        .size(60.dp)
        .clip(RoundedCornerShape(8.dp))
        .background(mainPrimary), contentAlignment = Alignment.Center)
    {
        Image(painter = painterResource(id = R.drawable.add_vk), contentDescription = "")

    }
}


