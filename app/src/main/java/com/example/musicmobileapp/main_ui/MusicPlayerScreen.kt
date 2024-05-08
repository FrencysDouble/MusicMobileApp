package com.example.musicmobileapp.main_ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.musicmobileapp.R
import com.example.musicmobileapp.ui.theme.mainBackground


@Composable
fun MusicPlayerScreen()
{

    Column (
        Modifier
            .fillMaxSize()
            .background(mainBackground))
    {
        Column(modifier = Modifier
            .padding(start = 24.dp, end = 24.dp, top = 50.dp, bottom = 50.dp)
            .fillMaxSize())
        {
            Image(painter = painterResource(id = R.drawable.photo), contentDescription ="" ,Modifier.size(350.dp))
            ProgressBarDemo()
            Column(
                Modifier
                    .fillMaxWidth()
                    .padding(top = 100.dp)) {
                Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                    Text(text = "Bohemian Rapsody", fontSize = 23.sp)
                    IconButton(onClick = { /*TODO*/ },
                        Modifier
                            .wrapContentSize()
                            .padding(start = 110.dp)) {
                        Image(painter = painterResource(id = R.drawable.ma_heartp), contentDescription ="" )
                        
                    }
                }
                Text(text = "Queen")
            }
            MainPlayer()

        }
    }

}
@Composable
@Preview
fun previw()
{
    MusicPlayerScreen()
}

@Composable
fun ProgressBarDemo() {
    var progress by remember { mutableStateOf(0.5f) }

    Column(
        modifier = Modifier.wrapContentSize().padding(top = 16.dp   )
    ) {
        LinearProgressIndicator(
            progress = progress,
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Composable()
fun MainPlayer()
{
    Row (
        Modifier
            .fillMaxWidth()
            .padding(top = 100.dp), horizontalArrangement = Arrangement.Center){
        IconButton(onClick = { /*TODO*/ },Modifier.padding(end = 30.dp)) {
            Image(painter = painterResource(id = R.drawable.ma_rotate), contentDescription = "")
        }
        IconButton(onClick = { /*TODO*/ },Modifier.padding(end = 20.dp)) {
            Image(painter = painterResource(id = R.drawable.ma_skip_previous), contentDescription ="")
        }
        IconButton(onClick = { /*TODO*/ }) {
            Image(painter = painterResource(id = R.drawable.ma_pause), contentDescription = "")
        }
        IconButton(onClick = { /*TODO*/ },Modifier.padding(start = 20.dp)) {
            Image(painter = painterResource(id = R.drawable.ma_skip_next), contentDescription = "")
        }
        IconButton(onClick = { /*TODO*/ },Modifier.padding(start = 30.dp)) {
            Image(painter = painterResource(id = R.drawable.ma_shuffle), contentDescription ="" )

        }

    }
}