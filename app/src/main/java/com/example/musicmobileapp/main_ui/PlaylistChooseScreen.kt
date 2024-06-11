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
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
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
import com.example.musicmobileapp.main_ui.navigation.Routes
import com.example.musicmobileapp.models.screens.PlaylistScreenModel
import com.example.musicmobileapp.ui.theme.mainBackground
import com.example.musicmobileapp.ui.theme.mainPrimary
import com.example.musicmobileapp.ui.theme.textSecondary
import com.example.musicmobileapp.ui.theme.textTertiary

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun ChooseScreen(
    navController: NavHostController,
    trackId: String,
    controller: PlaylistController
) {

    val selectedPlaylists = remember { mutableStateListOf<Long>() }

    val playlistList by controller.livePlaylistDataList.observeAsState()

    Scaffold(modifier = Modifier.fillMaxSize(), bottomBar = { ConfirmButton(navController,controller,trackId,selectedPlaylists) }) {
        ChScreen(navController, playlistList,selectedPlaylists)
    }


}




@Composable
fun ChScreen(
    navController: NavHostController,
    playlistList: List<PlaylistScreenModel>?,
    selectedPlaylists: SnapshotStateList<Long>
) {
    Column(
        Modifier
            .fillMaxSize()
            .background(mainBackground)
            .padding(start = 24.dp, end = 24.dp, top = 16.dp)
    ) {
        UpBarCreation(navController)
        ChMainList(navController,playlistList,selectedPlaylists)

    }
}

@Composable
fun UpBarCreation(navController: NavHostController)
{
    Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
        IconButton(onClick = { navController.navigate(Routes.NavBar.Search.route) }) {

            Icon(painter = painterResource(id = R.drawable.pl_back), contentDescription = "")
        }
        Box(modifier = Modifier
            .fillMaxWidth()
            .padding(end = 30.dp), contentAlignment  = Alignment.Center)
        {
            Text(text = stringResource(id = R.string.pl_creation_title), fontSize = 20.sp)
        }

    }
}

@Composable
fun ChMainList(
    navController: NavHostController,
    playlistList: List<PlaylistScreenModel>?,
    selectedPlaylists: SnapshotStateList<Long>
) {
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
                    PlaylistItemCreation(playlists,navController,selectedPlaylists)
                }
            }
        }
    }
}

@Composable
fun PlaylistItemCreation(
    playlists: PlaylistScreenModel,
    navController: NavHostController,
    selectedPlaylists: SnapshotStateList<Long>
)
{
    var isClicked by remember { mutableStateOf(false) }
    Row(
        Modifier
            .fillMaxWidth()
            .clickable {
                isClicked = !isClicked
                if (isClicked)
                {
                    selectedPlaylists.add(playlists.id.toLong())
                }
                else
                {
                    selectedPlaylists.remove(playlists.id.toLong())
                }
                       },
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
            Image(
                painter = painterResource(id = when(isClicked)
                {
                    false -> R.drawable.pl_create_adder_disactive
                    true -> R.drawable.pl_create_adder_active
                }),
                contentDescription = "",Modifier.size(40.dp)
            )
        }

    }

}


@Composable
fun ConfirmButton(
    navController: NavHostController,
    controller: PlaylistController,
    trackId: String,
    selectedPlaylists: SnapshotStateList<Long>
)
{
    Column(
        Modifier
            .fillMaxWidth()
            .padding(bottom = 24.dp), horizontalAlignment = Alignment.CenterHorizontally) {

        Button(
            onClick = {
                controller.addTrackToPlaylist(selectedPlaylists.toList(),trackId.toLong())
                controller.dataLoad()
                navController.navigate(Routes.NavBar.Search.route) },
            colors = ButtonColors(
                containerColor = mainPrimary,
                contentColor = mainBackground,
                disabledContentColor = mainBackground,
                disabledContainerColor = textTertiary
            ), enabled = !selectedPlaylists.isEmpty(),
            modifier = Modifier
                .width(120.dp)
                .height(50.dp)
        ) {
            Text(text = "Готово")

        }
    }

}
