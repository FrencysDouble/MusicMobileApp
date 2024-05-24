package com.example.musicmobileapp.main_ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.musicmobileapp.R
import com.example.musicmobileapp.ui.theme.mainBackground


@Composable
@Preview
fun AlbumScreen()
{
    Column(
        Modifier
            .fillMaxSize()
            .background(mainBackground)) {
        Column(
            Modifier
                .fillMaxSize()
                .padding(start = 24.dp, end = 24.dp,top = 16.dp)) {
            AlbumImage()
            AlbumTitle()
            AlbumPlayer()
            AlbumList()
        }

    }
}

@Composable
fun AlbumImage()
{
    Image(painter = painterResource(id = R.drawable.photo), contentDescription ="" ,
        Modifier
            .fillMaxWidth()
            .height(327.dp))
}


@Composable
fun AlbumTitle()
{
    Column(
        Modifier
            .wrapContentSize()
            .padding(top = 16.dp)) {
        Text(text = "Scorpion", fontSize = 28.sp)
        Row(
            Modifier
                .fillMaxWidth()
                .padding(top = 16.dp), verticalAlignment = Alignment.CenterVertically) {
            Image(painter = painterResource(id = R.drawable.photo), contentDescription ="",
                Modifier
                    .size(24.dp)
                    .clip(RoundedCornerShape(128.dp)))
            Text(text = "Drake",Modifier.padding(start = 12.dp))
            Text(text = "  •  ")
            Text(text = "2018")
            Text(text = "  •  ")
            Text(text = "15 треков")
            Text(text = "55 мин, 45 сек",Modifier.padding(start = 4.dp))
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
fun AlbumList()
{

    LazyColumn(modifier = Modifier
        .fillMaxSize()
        .padding(top = 16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp))
    {
        items(10)
        {
            AlbumItemList()
        }

    }

}


@Preview
@Composable
fun AlbumItemList()
{
    Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
        Text(text = "1")
        Text(text = "Nonstop", Modifier.padding(start = 8.dp))
        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
            IconButton(onClick = { /*TODO*/ }) {
                Image(painter = painterResource(id = R.drawable.al_more), contentDescription = "")
            }

        }
    }
}

