package com.example.musicmobileapp.main_ui

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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.musicmobileapp.R
import com.example.musicmobileapp.controllers.MusicPlayerController
import com.example.musicmobileapp.main_ui.navigation.Routes
import com.example.musicmobileapp.models.dto.TrackModel
import com.example.musicmobileapp.network.service.MusicInterface
import com.example.musicmobileapp.network.service.SelectedTrackItem
import com.example.musicmobileapp.ui.theme.mainBackground
import com.example.musicmobileapp.ui.theme.textPrimary
import com.example.musicmobileapp.ui.theme.textSecondary


object BottomMusicPlayer {

    @Composable
    fun Player(
        navController: NavHostController,
        musicService: MusicInterface,
        selectItem: SelectedTrackItem,
        controller: MusicPlayerController
    ) {
        val trackId by selectItem.selectedItemId.observeAsState()

        val musicData by controller.musicData.observeAsState()

        LaunchedEffect(trackId)
        {
            trackId?.let { controller.dataLoad(it.toLong()) }
        }
        trackId?.let {
            PlayerContent(navController, controller, musicData,selectItem.selectedItemId.value,musicService)
        }

    }


    @Composable
    fun PlayerContent(
        navController: NavHostController,
        controller: MusicPlayerController,
        musicData: TrackModel?,
        id: Int?,
        musicService: MusicInterface
    ) {


        val isPlaying by remember { mutableStateOf(controller.isPlaying) }

        Box(
            Modifier
                .fillMaxWidth()
                .height(68.dp)
                .background(mainBackground)
                .clickable { navController.navigate("${Routes.MusicPlayerScreen.route}/${id}") }
        ) {
            Row(
                Modifier
                    .fillMaxSize()
                    .background(mainBackground)
                    .padding(12.dp), verticalAlignment = Alignment.CenterVertically
            ) {
                musicData?.let {
                    ImageLoading.loadImage(url = it.imageUrl,Modifier.size(44.dp).clip(
                        RoundedCornerShape(12.dp)) ,context = LocalContext.current
                    )
                }

                Column(Modifier.padding(start = 12.dp)) {
                    musicData?.let { Text(text = it.name, fontSize = 16.sp, color = textPrimary) }
                    musicData?.let { Text(text = it.artistName, fontSize = 12.sp, color = textSecondary) }
                }

                Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
                    IconButton(onClick = { /*TODO*/ }) {
                        Icon(
                            painter = painterResource(id = R.drawable.btm_heart),
                            contentDescription = "", Modifier.size(24.dp)
                        )
                    }

                    IconButton(onClick = {
                        when (isPlaying.value) {
                            true -> {
                                musicService.onPause()
                            }

                            false -> {
                                musicService.onStart()
                            }
                        }
                    }) {
                        Icon(
                            painter = painterResource(when(isPlaying.value)
                            {
                                true -> R.drawable.ma_pause
                                false -> R.drawable.ma_launch

                            }),
                            contentDescription = "", Modifier.size(24.dp)
                        )

                    }
                }


            }
        }
    }
}