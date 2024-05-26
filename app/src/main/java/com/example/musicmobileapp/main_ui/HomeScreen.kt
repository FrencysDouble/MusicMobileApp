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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.musicmobileapp.R
import com.example.musicmobileapp.main_ui.navigation.BottomNavigationBar
import com.example.musicmobileapp.ui.theme.mainBackground
import com.example.musicmobileapp.ui.theme.mainPrimary
import com.example.musicmobileapp.ui.theme.textSecondary
import kotlin.math.round


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavHostController) {

    Scaffold(modifier = Modifier.fillMaxSize(), bottomBar = { BottomNavigationBar(navController = navController) }) {
        screen()

    }
}




@Composable
@Preview
fun screen() {
    Column(
        Modifier
            .fillMaxSize()
            .background(mainBackground)
            .padding(start = 24.dp, end = 24.dp, top = 16.dp)
    ) {
        UpBar()
        MainList()

    }
}

@Composable
fun UpBar()
{
    Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
        IconButton(onClick = { /*TODO*/ }) {

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
fun MainList()
{
    Box(modifier = Modifier
        .wrapContentSize()
        .padding(top = 24.dp))
    {
        LazyColumn(verticalArrangement = Arrangement.spacedBy(24.dp))
        {
            item{
                NewPlaylistItem()
            }
            items(10)
            {
                PlaylistItem()
            }
        }
    }

}

@Composable
fun NewPlaylistItem()
{
    Row(
        Modifier
            .fillMaxWidth()
            .clickable { TODO() },
        verticalAlignment = Alignment.CenterVertically) {
        NewItemBox()
        Text(text = stringResource(id = R.string.playlist_new),Modifier.padding(start = 12.dp))

    }

}

@Composable
@Preview
fun PlaylistItem()
{
    Row(
        Modifier
            .fillMaxWidth()
            .clickable { TODO() },
        verticalAlignment = Alignment.CenterVertically) {
        Image(painter = painterResource(id = R.drawable.photo), contentDescription = "",
            Modifier
                .size(60.dp)
                .clip(
                    RoundedCornerShape(8.dp)
                ))
        Text(text = "Название трека",Modifier.padding(start = 12.dp))
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


