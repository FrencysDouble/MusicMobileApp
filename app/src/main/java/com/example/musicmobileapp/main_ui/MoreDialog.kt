package com.example.musicmobileapp.main_ui

import androidx.compose.foundation.Image
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.musicmobileapp.R
import com.example.musicmobileapp.ui.theme.mainBackground
import com.example.musicmobileapp.ui.theme.textSecondary

object MoreDialog {

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun BottomSheet(onDismissRequest: () -> Unit) {
        ModalBottomSheet(onDismissRequest = { onDismissRequest.invoke()}, containerColor = mainBackground,) {
            dialog()
        }
    }

    @Composable
    fun dialog() {
        Column(
            Modifier
                .fillMaxWidth()
                .background(mainBackground)
                .padding(8.dp), verticalArrangement = Arrangement.Center) {
            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(bottom = 12.dp)) {
                upperDialog()
            }
            HorizontalDivider(
                Modifier
                    .height(1.dp)
                    .fillMaxWidth())
            ButtonList()
        }
    }
}

@Composable
fun upperDialog()
{
    Image(painter = painterResource(id = R.drawable.photo), contentDescription ="",
        Modifier
            .size(50.dp)
            .clip(
                RoundedCornerShape(8.dp)
            ))
    Column(Modifier.padding(start = 8.dp)) {
        Text(text = "Trance .....", fontSize = 24.sp)
        Row(Modifier.fillMaxWidth()) {
            Text(text = "Metro Boomin",color = textSecondary, fontSize = 16.sp)
            Text(text = "  •  ", color = textSecondary, fontSize = 16.sp)
            Text(text = "Album Name", color = textSecondary, fontSize = 16.sp)
        }
    }
}

@Composable
fun ButtonList() {
    Box(modifier = Modifier.wrapContentSize().padding(top = 12.dp))
    {
        LazyColumn(verticalArrangement = Arrangement.spacedBy(24.dp)) {
            item {
                ItemButtons(
                    imgId = R.drawable.dialog_add,
                    textId = R.string.dialog_add_playlist,
                    onClick = {}
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
                    onClick = {}
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



@Composable
@Preview
fun preview()
{
    MoreDialog.dialog()
}