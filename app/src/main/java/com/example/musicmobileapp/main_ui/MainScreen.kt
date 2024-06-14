package com.example.musicmobileapp.main_ui

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.musicmobileapp.R
import com.example.musicmobileapp.controllers.ArtistController
import com.example.musicmobileapp.controllers.PlaylistController
import com.example.musicmobileapp.main_ui.navigation.BottomNavigationBar
import com.example.musicmobileapp.models.dto.AlbumModel
import com.example.musicmobileapp.models.screens.ArtistScreenModel
import com.example.musicmobileapp.models.screens.PlaylistScreenModel
import com.example.musicmobileapp.ui.theme.mainBackground
import com.example.musicmobileapp.ui.theme.textPrimary
import com.example.musicmobileapp.ui.theme.textSecondary


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    navController: NavHostController,
    controller: PlaylistController,
    artistController: ArtistController
)
{

    val playlistData by controller.livePlaylistDataList.observeAsState()

    val artistData by artistController.liveArtistData.observeAsState()

    Scaffold(modifier = Modifier.fillMaxSize(), bottomBar = { BottomNavigationBar(navController = navController) }) {
       Screen(data = playlistData,artistData)
    }

}

@Composable
fun Screen(data: List<PlaylistScreenModel>?, artistData: ArtistScreenModel?)
{
    Column(
        Modifier
            .fillMaxSize()
            .background(mainBackground)) {
        Column(
            Modifier
                .fillMaxSize()
                .padding(start = 24.dp, end = 24.dp, top = 42.dp)) {


            Text(
                text = stringResource(id = R.string.home_screen_title),
                color = textPrimary,
                fontSize = 24.sp
            )

            data?.let { PlaylistLazyList(it) }
            artistData?.let { AlbumLazyList(it) }
        }

    }

}


@Composable
fun PlaylistLazyList(data: List<PlaylistScreenModel>)
{
    Column(
        Modifier
            .fillMaxWidth()
            .padding(top = 24.dp)) {
        Text(text = stringResource(id = R.string.home_screen_playlist), color = textPrimary, fontSize = 16.sp)

        LazyRow(horizontalArrangement = Arrangement.spacedBy(24.dp),
            modifier = Modifier.padding(top = 24.dp))
        {
            data.let { data ->
                items(data) {data ->
                    HomeScreenListItem(data)
                }
            }
        }

    }

}


@Composable
fun HomeScreenListItem(data: PlaylistScreenModel)
{
    Column(
        Modifier
            .wrapContentSize()
            .background(mainBackground),) {

        loadImage(url = data.imageUrl,
            Modifier
                .width(125.dp)
                .height(150.dp)
                .clip(RoundedCornerShape(16.dp)))

        Column(Modifier.padding(top = 12.dp)) {
            Text(text = data.name, color = textPrimary, fontSize = 16.sp)
            Text(text = "Плейлист", color = textSecondary, fontSize = 12.sp)
        }
    }

}

@Composable
fun AlbumLazyList(artistData: ArtistScreenModel)
{
    Column(
        Modifier
            .fillMaxWidth()
            .padding(top = 24.dp)) {
        Text(text = stringResource(id = R.string.home_screen_albums), color = textPrimary, fontSize = 16.sp)
        LazyRow(horizontalArrangement = Arrangement.spacedBy(24.dp),
            modifier = Modifier.padding(top = 24.dp))
        {
            artistData.let { data ->
                items(data.albums) {dataAlbum ->
                    HomeScreenAlbumListItem(dataAlbum)
                }
            }
        }

    }

}

@Composable
fun HomeScreenAlbumListItem(data: AlbumModel)
{
    Column(
        Modifier
            .wrapContentSize()
            .background(mainBackground),) {

        loadImage(url = data.imageUrl,
            Modifier
                .width(125.dp)
                .height(150.dp)
                .clip(RoundedCornerShape(16.dp)))

        Column(Modifier.padding(top = 12.dp)) {
            Text(text = data.name, color = textPrimary, fontSize = 16.sp)
            Text(text = "Альбом", color = textSecondary, fontSize = 12.sp)
        }
    }

}