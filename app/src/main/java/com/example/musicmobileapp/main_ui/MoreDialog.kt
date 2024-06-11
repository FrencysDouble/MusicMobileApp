package com.example.musicmobileapp.main_ui

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.musicmobileapp.R
import com.example.musicmobileapp.controllers.SearchScreenController
import com.example.musicmobileapp.main_ui.navigation.Routes
import com.example.musicmobileapp.models.Title
import com.example.musicmobileapp.models.screens.SearchScreenModel
import com.example.musicmobileapp.network.service.SelectedTrackItem
import com.example.musicmobileapp.ui.theme.mainBackground
import com.example.musicmobileapp.ui.theme.textSecondary

object MoreDialog {

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun BottomSheet(
        onDismissRequest: () -> Unit,
        controller: SearchScreenController,
        selectItem: SelectedTrackItem,
        navController: NavHostController
    ) {
        val searchList by controller.liveSearchData.observeAsState()


        val item = searchItem(searchList,selectItem)


        ModalBottomSheet(onDismissRequest = { onDismissRequest.invoke()}, containerColor = mainBackground) {

            dialog(item,navController)
        }
    }

    @Composable
    fun dialog(item: Pair<SearchScreenModel, SearchScreenModel>, navController: NavHostController) {
        Column(
            Modifier
                .fillMaxWidth()
                .background(mainBackground)
                .padding(8.dp), verticalArrangement = Arrangement.Center) {
            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(bottom = 12.dp)) {
                upperDialog(item.first,item.second)
            }
            HorizontalDivider(
                Modifier
                    .height(1.dp)
                    .fillMaxWidth())
            ButtonList(navController,item.second,item.first)
        }
    }
}

@Composable
fun upperDialog(trackItem: SearchScreenModel, albumItem: SearchScreenModel)
{
    loadImage(url = trackItem.imageUrl,
        Modifier
            .size(50.dp)
            .clip(
                RoundedCornerShape(8.dp)
            ))
    Column(Modifier.padding(start = 8.dp)) {
        Text(text = trackItem.name, fontSize = 24.sp)
        Row(Modifier.fillMaxWidth()) {
            Text(text = trackItem.artistName,color = textSecondary, fontSize = 16.sp)
            Text(text = "  •  ", color = textSecondary, fontSize = 16.sp)
            Text(text = albumItem.name, color = textSecondary, fontSize = 16.sp)
        }
    }
}

@Composable
fun ButtonList(
    navController: NavHostController,
    albumItem: SearchScreenModel,
    trackItem: SearchScreenModel
) {
    Box(modifier = Modifier.wrapContentSize().padding(top = 12.dp))
    {
        LazyColumn(verticalArrangement = Arrangement.spacedBy(24.dp)) {
            item {
                ItemButtons(
                    imgId = R.drawable.dialog_add,
                    textId = R.string.dialog_add_playlist,
                    onClick = {navController.navigate("${Routes.PlaylistChooseScreen.route}/${trackItem.id}")}
                )
            }
            item {
                ItemButtons(
                    imgId = R.drawable.dialog_queue,
                    textId = R.string.dialog_add_query,
                    onClick = {}
                )
            }
            item {
                ItemButtons(
                    imgId = R.drawable.dialog_album,
                    textId = R.string.dialog_go_album,
                    onClick = {navController.navigate("${Routes.AlbumScreen.route}/${albumItem.id}")}
                )
            }
            item {
                ItemButtons(
                    imgId = R.drawable.dialog_artist,
                    textId = R.string.dialog_go_artist,
                    onClick = {}
                )
            }
        }
    }
}

@Composable
fun ItemButtons(imgId: Int, textId: Int,onClick: () -> Unit)
{
    Row(Modifier.fillMaxWidth().clickable { onClick() }, verticalAlignment = Alignment.CenterVertically) {
        Icon(painter = painterResource(id = imgId), contentDescription ="" ,modifier = Modifier.size(40.dp))
        Text(text = stringResource(id = textId),Modifier.padding(start = 12.dp), fontSize = 20.sp)
    }
}


fun searchItem(searchList: List<SearchScreenModel>?, selectItem: SelectedTrackItem): Pair<SearchScreenModel, SearchScreenModel> {

    val itemTrack = searchTrackItem(searchList,selectItem)

    searchList?.forEach{ itemAlbum ->
        if (itemAlbum.titleID == Title.ALBUMIDTITLE)
        {
            if (itemAlbum.id == itemTrack.album_id)
            {
                return Pair(itemTrack,itemAlbum)
            }
        }

    }

    throw NoSuchElementException("No item found with id: ${selectItem.selectedDialogItemId.value}")

}

fun searchTrackItem(searchList: List<SearchScreenModel>?, selectItem: SelectedTrackItem) : SearchScreenModel {
    searchList?.forEach { item ->
        if (item.titleID == Title.TRACKIDTITLE) {
            if (item.id == selectItem.selectedDialogItemId.value) {
                Log.d("Track Item", "$item")
                return item
            }
        }
    }
    throw NoSuchElementException("No item found with id: ${selectItem.selectedDialogItemId.value}")
}


