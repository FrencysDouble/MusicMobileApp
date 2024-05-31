package com.example.musicmobileapp.main_ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.musicmobileapp.R
import com.example.musicmobileapp.controllers.PlaylistController
import com.example.musicmobileapp.main_ui.navigation.Routes
import com.example.musicmobileapp.ui.theme.mainBackground
import com.example.musicmobileapp.ui.theme.mainBackgroundAccent
import com.example.musicmobileapp.ui.theme.mainPrimary
import com.example.musicmobileapp.ui.theme.textPrimary
import com.example.musicmobileapp.ui.theme.textSecondary


@Composable
fun PlaylistCreationScreen(
    navController: NavHostController,
    controller: PlaylistController
)
{

    val searchQuery = remember { mutableStateOf(TextFieldValue()) }

    Column(
        Modifier
            .fillMaxSize()
            .padding()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(mainBackground, mainPrimary),
                    startY = 400f,
                    endY = Float.POSITIVE_INFINITY
                )
            ),
        horizontalAlignment = Alignment.CenterHorizontally) {
        Column(
            Modifier
                .padding(top = 60.dp, start = 24.dp, end = 24.dp)
                .fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
            Text(stringResource(id = R.string.playlist_creation_title1), fontSize = 24.sp)
            Text(stringResource(id = R.string.playlist_creation_title2), fontSize = 24.sp)

            PlaylistNameField(searchQuery)
            CreationButtons(controller,searchQuery,navController)
        }

    }

}


@Composable
fun PlaylistNameField(searchQuery: MutableState<TextFieldValue>)
{
    Box(modifier = Modifier
        .wrapContentSize()
        .padding(top = 24.dp),contentAlignment = Alignment.Center)
    {
        BasicTextField(
            value = searchQuery.value,
            onValueChange = { searchQuery.value = it },
            modifier = Modifier
                .fillMaxWidth()
                .border(1.dp, mainBackgroundAccent, RoundedCornerShape(8.dp))
                .height(60.dp)
                .padding(horizontal = 16.dp),
            decorationBox = { innerTextField ->
                Box(modifier = Modifier
                    .fillMaxWidth(),
                    contentAlignment = Alignment.CenterStart)
                {
                    if (searchQuery.value.text.isEmpty()) {
                        Text(
                            text = stringResource(id = R.string.playlist_creation_field_hint),
                            color = textSecondary,
                            fontSize = 20.sp,
                        )
                    }
                    innerTextField()
                }
            }
        )
    }
}

@Composable
fun CreationButtons(
    controller: PlaylistController,
    searchQuery: MutableState<TextFieldValue>,
    navController: NavHostController
)
{
    Row (
        Modifier
            .fillMaxWidth()
            .padding(top = 50.dp), horizontalArrangement = Arrangement.spacedBy(40.dp, Alignment.CenterHorizontally)){

        Button(onClick = { navController.navigate(Routes.NavBar.Home.route) }, colors = ButtonColors(
            containerColor = mainBackground,
            contentColor = textPrimary,
            disabledContentColor =textPrimary,
            disabledContainerColor = mainBackground),
            border = BorderStroke(1.dp, mainBackgroundAccent),
            modifier = Modifier.width(120.dp).height(50.dp)
        ) {
            Text(text = stringResource(id = R.string.playlist_creation_btn_cancel))
        }

        Button(onClick = {
            controller.createPlaylist(searchQuery.value.text)
            navController.navigate(Routes.NavBar.Home.route) },
            colors = ButtonColors(
            containerColor = mainPrimary,
            contentColor = mainBackground,
            disabledContentColor = mainBackground,
            disabledContainerColor = mainPrimary),
            modifier = Modifier.width(120.dp).height(50.dp)
        ) {
            Text(text = stringResource(id = R.string.playlist_creation_btn_submit))
        }
    }

}

