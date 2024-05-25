package com.example.musicmobileapp.main_ui

import android.annotation.SuppressLint
import android.widget.ImageView
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.NavHostController
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.musicmobileapp.R
import com.example.musicmobileapp.controllers.SearchScreenController
import com.example.musicmobileapp.main_ui.navigation.BottomNavigationBar
import com.example.musicmobileapp.main_ui.navigation.Routes
import com.example.musicmobileapp.models.screens.SearchScreenModel
import com.example.musicmobileapp.models.Title
import com.example.musicmobileapp.ui.theme.mainBackground
import com.example.musicmobileapp.ui.theme.mainBackgroundAccent
import com.example.musicmobileapp.ui.theme.textSecondary


@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable


fun SearchScreen(navController: NavHostController, controller: SearchScreenController)
{
    val searchList by controller.liveSearchData.observeAsState()



    Scaffold(modifier = Modifier.fillMaxSize(), bottomBar = { BottomNavigationBar(navController = navController) }) {
        Screen(searchList,controller,navController)
    }
}




@Composable
fun Screen(
    searchList: List<SearchScreenModel>?,
    controller: SearchScreenController,
    navController: NavHostController
)
{
    val isSearchBarActive by controller.isNotActive
    Column(
        Modifier
            .fillMaxSize()
            .background(mainBackground)
            .padding(start = 24.dp, end = 24.dp)) {


        SearchField(controller)
        when(isSearchBarActive)
        {
            true -> MainList(searchList = searchList,navController)
            false -> HistoryList()
        }
    }
}

@Composable
fun SearchField(controller: SearchScreenController)
{
    val searchQuery = remember { mutableStateOf(TextFieldValue()) }
    if (!searchQuery.value.text.isEmpty()) {
        controller.dataLoad(searchQuery.value.text)
        controller.searchBarActive()
    }
    else{
        controller.searchBarDisable()
    }


    Box(modifier = Modifier
        .wrapContentSize()
        .padding(top = 24.dp, start = 24.dp, end = 24.dp), contentAlignment = Alignment.Center)
    {
        BasicTextField(
            value = searchQuery.value,
            onValueChange = { searchQuery.value = it },
            modifier = Modifier
                .fillMaxWidth()
                .border(1.dp, mainBackgroundAccent, RoundedCornerShape(8.dp))
                .height(42.dp)
                .padding(horizontal = 16.dp),
            decorationBox = { innerTextField ->
                Box(modifier = Modifier
                    .fillMaxWidth(),
                    contentAlignment = Alignment.CenterStart)
                {
                if (searchQuery.value.text.isEmpty()) {
                        Text(
                            text = stringResource(id = R.string.screen_search_hint_search),
                            color = textSecondary,
                            fontSize = 16.sp,
                        )
                    }
                    innerTextField()
                }
            }
        )
    }
}


@Composable
fun HistoryList()
{
    val historyText = stringResource(id = R.string.screen_search_history)
    Column(modifier = Modifier
        .fillMaxWidth()
        .padding(top = 32.dp)) {

        Text(text = historyText, fontSize = 16.sp)

        LazyRow(Modifier.padding(top = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp))
        {
            items(10)
            {
                HistoryListItem()
            }
        }
    }
}


@Composable
fun HistoryListItem()
{

    val imageId = R.drawable.photo
    val artistName = "Drake"

    Column(
        Modifier
            .width(100.dp)
            .height(144.dp)
            .background(mainBackground)) {
        Image(painter = painterResource(id = imageId), contentDescription = "",
            modifier = Modifier
                .width(100.dp)
                .height(100.dp)
                .clip(RoundedCornerShape(128.dp)))
        Column(
            Modifier
                .padding(top = 8.dp)
                .fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
            Text(text = artistName)
            Text(text = stringResource(id = R.string.screen_search_artist), fontSize = 12.sp, color = textSecondary)
        }
    }
}


@Composable
fun MainList(searchList: List<SearchScreenModel>?, navController: NavHostController)
{
    LazyColumn(modifier = Modifier
        .fillMaxSize()
        .padding(top = 16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp))
    {
        items(searchList ?: emptyList()){item ->
            MainListItem(item,navController)
        }

    }

}


@Composable
fun MainListItem(item: SearchScreenModel, navController: NavHostController)
{

    Row(
        Modifier
            .fillMaxWidth()
            .height(50.dp)
            .clickable {
                when (item.titleID) {
                    Title.ARTISTIDTITLE -> TODO("Not yet implemented")
                    Title.ALBUMIDTITLE -> navController.navigate("${Routes.AlbumScreen.route}/${item.id}")
                    Title.TRACKIDTITLE -> TODO("Not yet implemented")
                }
            }
    )
                {
        loadImage(
            url = item.imageUrl,
            modifier = Modifier
                .size(50.dp)
                .clip(RoundedCornerShape(128.dp))
        )
        Column(
            Modifier
                .wrapContentSize()
                .padding(start = 16.dp)) {
            Text(text = item.name)
            Text(text = item.title,color = textSecondary, fontSize = 12.sp)

        }

    }

}
@Composable
fun loadImage(url: String, modifier: Modifier = Modifier) {
    val context = LocalContext.current
    AndroidView(
        modifier = modifier,
        factory = { ImageView(context) },
        update = { imageView ->
            Glide.with(context)
                .load(url)
                .apply(RequestOptions().placeholder(R.drawable.spinner).error(R.drawable.baseline_error_outline_24))
                .into(imageView)
        }
    )
}
