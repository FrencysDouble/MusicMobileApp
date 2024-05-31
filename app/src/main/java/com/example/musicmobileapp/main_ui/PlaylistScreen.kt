package com.example.musicmobileapp.main_ui

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.musicmobileapp.R
import com.example.musicmobileapp.controllers.PlaylistController
import com.example.musicmobileapp.models.dto.TrackModel
import com.example.musicmobileapp.models.screens.PlaylistScreenModel
import com.example.musicmobileapp.ui.theme.mainBackground

@Composable
fun PlaylistScreen(
    navController: NavHostController,
    playlistId: String?,
    controller: PlaylistController
) {

    val playlist by controller.livePlaylistData.observeAsState()

    LaunchedEffect(playlistId) {
        controller.oneDataLoad(playlistId)
    }



    Column(
        Modifier
            .fillMaxSize()
            .background(mainBackground)
    ) {
        Column(
            Modifier
                .fillMaxSize()
                .padding(start = 24.dp, end = 24.dp, top = 16.dp)
        ) {
            playlist?.let { PlaylistImage(it) }
            playlist?.let { PlaylistTitle(it) }
            PlaylistPlayer()
            playlist?.let { PlaylistList(it.tracks) }

        }
    }

}

@Composable
fun PlaylistImage(playlist: PlaylistScreenModel)
{
    loadImage(url =playlist.imageUrl,
        Modifier
            .fillMaxWidth()
            .height(327.dp))
}


@Composable
fun PlaylistTitle(playlist: PlaylistScreenModel)
{
    Column(
        Modifier
            .wrapContentSize()
            .padding(top = 16.dp)) {
        Text(text =playlist.name, fontSize = 28.sp)
        Row(
            Modifier
                .fillMaxWidth()
                .padding(top = 16.dp), verticalAlignment = Alignment.CenterVertically) {
            Text(text = playlist.creatorName, Modifier)
            Text(text = "  •  ")
            Text(text = "23 треков")
            Text(text = "55 мин", Modifier.padding(start = 4.dp))
        }
    }
}



@Composable
fun PlaylistPlayer()
{
    Row(
        Modifier
            .fillMaxWidth()
            .padding(top = 16.dp), horizontalArrangement = Arrangement.Center){
        IconButton(onClick = { /*TODO*/ },
            Modifier
                .padding(end = 32.dp)
                .size(56.dp)) {
            Image(painter = painterResource(id = R.drawable.al_adder), contentDescription ="" )
        }
        IconButton(onClick = { /*TODO*/ }, Modifier.size(56.dp)) {
            Image(painter = painterResource(id = R.drawable.al_pause), contentDescription ="" )
        }
        IconButton(onClick = { /*TODO*/ },
            Modifier
                .padding(start = 32.dp)
                .size(56.dp)) {
            Image(painter = painterResource(id = R.drawable.al_shuffle), contentDescription ="" )
        }

    }

}

@Composable
fun PlaylistList(tracks: List<TrackModel>)
{

    LazyColumn(modifier = Modifier
        .fillMaxSize()
        .padding(top = 16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp))
    {
        tracks.let { tracks ->
           items(tracks){track ->
               AlbumItemList(track)
           }
        }


    }

}


@Composable
fun AlbumItemList(track : TrackModel)
{
    Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
        Text(text = track.id.toString(),Modifier.padding(end = 12.dp))
        Log.d("AlbumList",track.imagePath)
        loadImage(url = track.imagePath,
        Modifier
            .size(44.dp)
            .clip(
                RoundedCornerShape(8.dp)
            ))
        Text(text = track.name, Modifier.padding(start = 12.dp))
        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
            IconButton(onClick = { /*TODO*/ }) {
                Image(painter = painterResource(id = R.drawable.al_more), contentDescription = "")
            }

        }
    }
}