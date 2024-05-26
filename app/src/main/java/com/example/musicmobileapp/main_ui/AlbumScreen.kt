package com.example.musicmobileapp.main_ui

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
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.musicmobileapp.R
import com.example.musicmobileapp.controllers.AlbumController
import com.example.musicmobileapp.models.dto.TrackModel
import com.example.musicmobileapp.models.screens.AlbumScreenModel
import com.example.musicmobileapp.ui.theme.mainBackground


@Composable
fun AlbumScreen(
    navController: NavHostController,
    albumId: String,
    controller: AlbumController
)
{
    val albumData by controller.liveAlbumData.observeAsState()

    controller.dataLoad(albumId.toLong())
    Column(
        Modifier
            .fillMaxSize()
            .background(mainBackground)) {
        Column(
            Modifier
                .fillMaxSize()
                .padding(start = 24.dp, end = 24.dp, top = 16.dp)) {
            when {
                albumData == null -> {
                    LoadingScreen.Loading()
                }

                albumData!!.tracks.isEmpty() -> {
                    Text("Нет треков", Modifier.padding(16.dp))
                }

                else -> {
                    AlbumImage(albumData!!.albumImageUrl)
                    AlbumTitle(albumData!!)
                    AlbumPlayer()
                    AlbumList(albumData!!.tracks)
                }
            }
        }

    }
}

@Composable
fun AlbumImage(albumImageUrl: String)
{
    ImageLoading.loadImage(url = albumImageUrl,
        Modifier
            .fillMaxWidth()
            .height(327.dp),
        context = LocalContext.current)
}


@Composable
fun AlbumTitle(albumData: AlbumScreenModel)
{
    val tracksAmount = albumData.tracks.size
    Column(
        Modifier
            .wrapContentSize()
            .padding(top = 16.dp)) {
        Text(text = albumData.name , fontSize = 28.sp)
        Row(
            Modifier
                .fillMaxWidth()
                .padding(top = 16.dp), verticalAlignment = Alignment.CenterVertically) {
            ImageLoading.loadImage(albumData.artistImageUrl,
                Modifier
                    .size(24.dp)
                    .clip(RoundedCornerShape(128.dp)),
                context = LocalContext.current
            )
            Text(text = albumData.artistName,Modifier.padding(start = 12.dp))
            Text(text = "  •  ")
            Text(text = "2018")
            Text(text = "  •  ")
            Text(text = "$tracksAmount треков")
            Text(text = "55 мин",Modifier.padding(start = 4.dp))
        }
    }
}



@Composable
fun AlbumPlayer()
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
        IconButton(onClick = { /*TODO*/ },Modifier.size(56.dp)) {
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
fun AlbumList(tracks: List<TrackModel>)
{

    LazyColumn(modifier = Modifier
        .fillMaxSize()
        .padding(top = 16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp))
    {
        itemsIndexed(tracks){ index,item ->
            AlbumItemList(index = index,track = item)
        }

    }

}


@Composable
fun AlbumItemList( index: Int, track: TrackModel)
{
    Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
        Text(text = (index+1).toString())
        Text(text = track.name, Modifier.padding(start = 8.dp))
        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
            IconButton(onClick = { /*TODO*/ }) {
                Image(painter = painterResource(id = R.drawable.al_more), contentDescription = "")
            }

        }
    }
}

