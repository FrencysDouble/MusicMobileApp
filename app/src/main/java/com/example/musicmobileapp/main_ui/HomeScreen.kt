package com.example.musicmobileapp.main_ui

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
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
fun screen()
{
    Column(
        Modifier
            .fillMaxSize()
            .background(mainBackground)
            .padding(start = 24.dp, end = 24.dp)) {
        UpText()

    }

}

@Composable
@Preview
fun previewScreen()
{
    screen()
}

@Composable
fun UpText()
{
    Column(
        Modifier
            .fillMaxWidth()
            .padding(top = 50.dp)) {
        Text(text = "Альбомы", fontSize = 24.sp)

        Box(
            Modifier
                .fillMaxWidth()
                .padding(top = 20.dp))
        {
            Text(text = "Плейлисты", fontSize = 16.sp)
        }

    }
}


@Composable
fun ItemRow()
{
    Column(
        Modifier
            .wrapContentSize()
            .background(mainBackground)) {
        Image(painter = painterResource(id = R.drawable.ic_home), contentDescription = "",
            Modifier
                .width(125.dp)
                .height(200.dp))

        Column(
            Modifier
                .wrapContentSize()
                .padding(top = 8.dp)) {
            Text(text = "Заголовок", fontSize = 12.sp)
            Text(text = "Подзаголовок", color = textSecondary, fontSize = 8.sp)

        }

    }
}


@Composable
@Preview
fun ItemPreview()
{
    ItemRow()
}