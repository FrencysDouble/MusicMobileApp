package com.example.musicmobileapp.main_ui

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.gestures.rememberDraggableState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.IconButton
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.layout
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.media3.exoplayer.ExoPlayer
import androidx.navigation.NavHostController
import com.example.musicmobileapp.R
import com.example.musicmobileapp.controllers.MusicPlayerController
import com.example.musicmobileapp.ui.theme.gestureButton
import com.example.musicmobileapp.ui.theme.mainBackground
import com.example.musicmobileapp.ui.theme.mainPrimary
import com.example.musicmobileapp.ui.theme.progressOne
import com.example.musicmobileapp.ui.theme.progressTwo
import com.example.musicmobileapp.ui.theme.textSecondary
import kotlinx.coroutines.delay




@Composable
fun MusicPlayerScreen(
    navController: NavHostController,
    controller: MusicPlayerController,
    player: ExoPlayer
)

{

    val isPlaying by remember { mutableStateOf(controller.isPlaying) }
    var currentPosition by remember { mutableStateOf(0) }


    LaunchedEffect(key1 = player.currentPosition, key2 = player.isPlaying) {
        while (isPlaying.value) {
            delay(1000)
            currentPosition = player.currentPosition.toInt()
            Log.d("msg", player.currentPosition.toInt().toString())
        }
    }

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
            ContainerView(player.duration.toInt(),currentPosition,player)
            Column(
                Modifier
                    .fillMaxWidth()
                    .padding(top = 25.dp)) {
                Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                    Text(text = "Bohemian Rhapsody", fontSize = 23.sp)
                    IconButton(onClick = { /*TODO*/ },
                        Modifier
                            .wrapContentSize()
                            .padding(start = 110.dp)) {
                        Image(painter = painterResource(id = R.drawable.ma_heartp), contentDescription ="" )

                    }
                }
                Text(text = "Queen")
            }
            MainPlayer(isPlaying.value,controller)

        }
    }

}
@Composable()
fun MainPlayer(isPlaying: Boolean, controller: MusicPlayerController)
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
        IconButton(onClick = {
            println(isPlaying)
            if (isPlaying) {
                controller.onPlayerPause()
            }
            else
            {
                controller.onPlayerStart()
            }
        }) {
            Image(painter = painterResource(
                id = when(isPlaying) {
                    true -> R.drawable.ma_pause
                    false -> R.drawable.ma_launch
                }),
                contentDescription = "")
        }
        IconButton(onClick = { /*TODO*/ },Modifier.padding(start = 20.dp)) {
            Image(painter = painterResource(id = R.drawable.ma_skip_next), contentDescription = "")
        }
        IconButton(onClick = { /*TODO*/ },Modifier.padding(start = 30.dp)) {
            Image(painter = painterResource(id = R.drawable.ma_shuffle), contentDescription ="" )

        }

    }
}

@Composable
fun ContainerView(totalDuration: Int, currentTimesPosition: Int,player: ExoPlayer) {

    var buttonPosition by remember { mutableStateOf(0.dp) }
    val maxWidth = 350.dp
    var trackDuration by remember { mutableStateOf(totalDuration) }
    var currentTime by remember { mutableStateOf(currentTimesPosition) }
    var timeLeft by remember { mutableStateOf(totalDuration - currentTimesPosition) }

    LaunchedEffect(totalDuration, currentTimesPosition) {
        trackDuration = totalDuration
        currentTime = currentTimesPosition
        updateProgressState(
            currentTimesPosition = currentTime,
            totalDuration = trackDuration,
            maxWidth = maxWidth,
            onUpdateButtonPosition = { newPosition -> buttonPosition = newPosition },
            onUpdateTimeLeft = { newTimeLeft -> timeLeft = newTimeLeft }
        )
    }

    Log.d("MutableDuration", trackDuration.toString())

    Box(
        modifier = Modifier
            .wrapContentSize()
            .background(mainBackground)
            .padding(top = 20.dp),
        contentAlignment = Alignment.Center
    ) {
        MusicPlayerProgressBar(
            modifier = Modifier.fillMaxWidth(),
            buttonPosition = buttonPosition,
            onProgressBarClick = { clickPosition ->
                updateTrackTime(
                    player = player,
                    clickPosition = clickPosition,
                    maxWidth = maxWidth,
                    totalDuration = trackDuration,
                    onUpdateCurrentTime = { newTime ->
                        currentTime = newTime
                        timeLeft = trackDuration - currentTime
                        buttonPosition = (newTime.toFloat() / trackDuration * maxWidth.value).dp
                    }
                )
            }
        )
        Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.TopStart) {
            DragGestureButton(
                buttonPosition = buttonPosition,
                maxWidth = maxWidth,
                onButtonPositionChange = { newPosition ->
                    buttonPosition = newPosition
                    currentTime = ((newPosition / maxWidth.value) * trackDuration).value.toInt()
                    timeLeft = trackDuration - currentTime
                },
                currentPosition = currentTime
            )
        }
        TextViewProgressBar(currentTime = currentTime, timeLeft = timeLeft)
    }
}

@Composable
fun MusicPlayerProgressBar(
    modifier: Modifier = Modifier,
    buttonPosition: Dp,
    onProgressBarClick: (Dp) -> Unit
) {
    val backgroundColor = progressTwo
    val progressColor = progressOne

    Box(
        modifier = modifier
            .height(4.dp)
            .background(backgroundColor, RoundedCornerShape(8.dp))
            .pointerInput(Unit) {
                detectTapGestures { offset ->
                    val clickPosition = offset.x.toDp()
                    onProgressBarClick(clickPosition)
                }
            },
        contentAlignment = Alignment.CenterStart
    ) {
        Spacer(
            modifier = Modifier
                .fillMaxHeight()
                .fillMaxWidth()
                .background(backgroundColor)
        )
        Spacer(
            modifier = Modifier
                .fillMaxHeight()
                .width(buttonPosition)
                .background(progressColor)
        )
    }
}

@Composable
fun DragGestureButton(
    buttonPosition: Dp,
    maxWidth: Dp,
    onButtonPositionChange: (Dp) -> Unit,
    currentPosition: Int
) {
    Box(
        modifier = Modifier
            .offset { IntOffset(buttonPosition.roundToPx(), 0) }
            .height(20.dp)
            .width(20.dp)
            .background(gestureButton, RoundedCornerShape(128.dp))
            .draggable(
                orientation = Orientation.Horizontal,
                state = rememberDraggableState { delta ->
                    println("Delta = $delta")
                    val smoothDelta = delta * 0.4f
                    val newPosition = (buttonPosition + smoothDelta.dp).coerceIn(0.dp, maxWidth)
                    onButtonPositionChange(newPosition)
                }
            ),
        contentAlignment = Alignment.Center
    ) {
        Box(
            modifier = Modifier
                .height(15.dp)
                .width(15.dp)
                .background(mainPrimary, RoundedCornerShape(128.dp))
        )
    }
}

@Composable
fun TextViewProgressBar(currentTime: Int, timeLeft: Int) {
    Row(
        Modifier.fillMaxWidth()
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 30.dp),
            contentAlignment = Alignment.CenterStart
        ) {
            Text(
                text = formatTime(currentTime),
                modifier = Modifier.padding(top = 8.dp),
                color = textSecondary
            )
            Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.CenterEnd) {
                Text(
                    text = "-" + formatTime(timeLeft),
                    modifier = Modifier.padding(top = 8.dp, start = 16.dp),
                    color = textSecondary
                )
            }
        }
    }
}

fun formatTime(milliseconds: Int): String {
    val seconds = milliseconds / 1000
    val minutes = seconds / 60
    val remainingSeconds = seconds % 60
    return "$minutes:${String.format("%02d", remainingSeconds)}"
}

fun updateProgressState(
    currentTimesPosition: Int,
    totalDuration: Int,
    maxWidth: Dp,
    onUpdateButtonPosition: (Dp) -> Unit,
    onUpdateTimeLeft: (Int) -> Unit
) {
    val buttonPosition = if (totalDuration > 0) {
        (currentTimesPosition.toFloat() / totalDuration * maxWidth.value).dp
    } else {
        0.dp
    }
    onUpdateButtonPosition(buttonPosition)
    onUpdateTimeLeft(totalDuration - currentTimesPosition)
}

fun updateTrackTime(
    player: ExoPlayer,
    clickPosition: Dp,
    maxWidth: Dp,
    totalDuration: Int,
    onUpdateCurrentTime: (Int) -> Unit
) {
    val newTime = ((clickPosition / maxWidth.value) * totalDuration).value.toInt()
    player.seekTo(newTime.toLong())
    onUpdateCurrentTime(newTime)
}

