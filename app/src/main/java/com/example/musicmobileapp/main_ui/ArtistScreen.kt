package com.example.musicmobileapp.main_ui

import androidx.compose.animation.core.Animatable
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.musicmobileapp.R
import com.example.musicmobileapp.controllers.ArtistController
import com.example.musicmobileapp.models.dto.AlbumModel
import com.example.musicmobileapp.models.dto.TrackModel
import com.example.musicmobileapp.models.screens.ArtistScreenModel
import com.example.musicmobileapp.ui.theme.mainBackground
import com.example.musicmobileapp.ui.theme.mainPrimary
import com.example.musicmobileapp.ui.theme.textPrimary
import com.example.musicmobileapp.ui.theme.textSecondary


@Composable
fun ArtistScreen(
    navController: NavHostController,
    artistId: String,
    controller: ArtistController
) {
    val scrollState = rememberLazyListState()
    val expandedHeight = 250.dp
    val collapsedHeight = 75.dp
    val imageHeight = remember { Animatable(expandedHeight.value) }

    val artistData by controller.liveArtistData.observeAsState()


    LaunchedEffect(artistId)
    {
        controller.dataLoad(artistId.toLong())
    }

    Column(modifier = Modifier.fillMaxSize()) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(imageHeight.value.dp)
        ) {
            artistData?.let { ArtistImage(modifier = Modifier.fillMaxSize().height(imageHeight.value.dp), it) }
        }
        LazyColumn(
            modifier = Modifier
                .weight(1f)
                .fillMaxSize(),
            state = scrollState,
            contentPadding = PaddingValues(horizontal = 16.dp)
        ) {
            item { upBar() }
            item { PopularList() }
            artistData?.tracks?.take(5)?.let { tracks ->
                items(tracks) { track ->
                    PopularItemList(track)
                }
            }
            item { ReleaseList() }
            artistData?.albums?.take(5)?.let { albums ->
                items(albums) { album ->
                    ReleaseItem(album)
                }
            }
        }

        LaunchedEffect(scrollState) {
            snapshotFlow { scrollState.firstVisibleItemIndex }
                .collect { index ->
                    if (index > 0) {
                        imageHeight.animateTo(collapsedHeight.value)
                    } else {
                        imageHeight.animateTo(expandedHeight.value)
                    }
                }
        }
    }
}





@Composable
fun ArtistImage(modifier: Modifier, artistData: ArtistScreenModel)
{
    Box(modifier = Modifier
        .wrapContentSize()
        .height(250.dp), contentAlignment = Alignment.BottomStart)
    {
        ImageLoading.loadImage(
            url = artistData.arImagePath,
            modifier, LocalContext.current
        )
        Box(modifier = Modifier
            .wrapContentSize()
            .padding(start = 8.dp))
        {
            Text(text = artistData.name, fontSize = 42.sp, color = mainBackground)
        }
    }
}


@Composable
fun upBar()
{
    Column(Modifier.padding(top = 8.dp), verticalArrangement = Arrangement.Center) {
        Text(text = "Общее количество слушателей : 65", color = textSecondary)
    }
    Row (Modifier.padding(end = 16.dp),verticalAlignment = Alignment.CenterVertically){
        subscribeButton()
        BtnMenu()
    }

}



@Preview
@Composable
fun subscribeButton()
{
    Button(onClick = { /*TODO*/ },colors = ButtonColors(
        containerColor = mainBackground,
        contentColor = mainBackground,
        disabledContainerColor = mainPrimary,
        disabledContentColor = mainPrimary
    ), border = BorderStroke(2.dp, mainPrimary)
    ) {
        Text(stringResource(id = R.string.artist_screen_sbs_btn),color = textPrimary)
        
    }

}


@Composable
fun BtnMenu()
{
    Row(
        Modifier
            .fillMaxWidth()
            .padding(top = 8.dp), horizontalArrangement = Arrangement.End){
        IconButton(onClick = { /*TODO*/ },Modifier.size(45.dp)) {
            Image(painter = painterResource(id = R.drawable.al_pause), contentDescription ="" )
        }
        IconButton(onClick = { /*TODO*/ },
            Modifier
                .padding(start = 8.dp)
                .size(45.dp)) {
            Image(painter = painterResource(id = R.drawable.al_shuffle), contentDescription ="" )
        }

    }
}



@Composable
fun PopularList() {
    Column(Modifier.padding(top = 12.dp)) {
        Text(
            text = stringResource(id = R.string.artist_screen_popular),
            color = textPrimary,
            fontSize = 24.sp
        )

        Spacer(modifier = Modifier.height(12.dp))

    }

}



@Composable
fun PopularItemList(trackData: TrackModel)
{
    Row(
        Modifier
            .fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
        Text(text = "1",Modifier.padding(end = 12.dp))
        loadImage(url = trackData.imageUrl,
            Modifier
                .size(44.dp)
                .clip(
                    RoundedCornerShape(8.dp)
                ))
        Text(text = trackData.name, Modifier.padding(start = 12.dp))
        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
            IconButton(onClick = { /*TODO*/ }) {
                Image(painter = painterResource(id = R.drawable.al_more), contentDescription = "")
            }

        }
    }
    Spacer(modifier = Modifier.height(12.dp))
}


@Composable
fun ReleaseList()
{
    Column(Modifier.padding(top = 12.dp)) {
        Text(text = stringResource(id = R.string.artist_screen_release),color = textPrimary, fontSize = 24.sp)
        Spacer(modifier = Modifier.height(12.dp))

    }
}

@Composable
fun ReleaseItem(albumData: AlbumModel)
{
    Row(verticalAlignment = Alignment.CenterVertically) {
        loadImage(url = albumData.imageUrl,
            Modifier.size(80.dp))
        Column(Modifier.padding(start = 16.dp)) {
            Text(text = albumData.name, fontSize = 18.sp,color = textPrimary)
            Text(text = "Альбом", fontSize = 12.sp,color = textSecondary)
        }

    }
    Spacer(modifier = Modifier.height(12.dp))

}


