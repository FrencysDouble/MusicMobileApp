package com.example.musicmobileapp.main_ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.detectDragGestures
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.consumeAllChanges
import androidx.compose.ui.input.pointer.consumePositionChange
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.input.pointer.positionChange
import androidx.compose.ui.layout.layout
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintSet
import androidx.constraintlayout.compose.Dimension
import com.example.musicmobileapp.R
import com.example.musicmobileapp.ui.theme.gestureButton
import com.example.musicmobileapp.ui.theme.mainBackground
import com.example.musicmobileapp.ui.theme.mainPrimary
import com.example.musicmobileapp.ui.theme.progressOne
import com.example.musicmobileapp.ui.theme.progressTwo
import com.example.musicmobileapp.ui.theme.textSecondary
import kotlin.math.absoluteValue
import kotlin.math.roundToInt


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
            ContainerView(totalDuration = 333)
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
        modifier = Modifier
            .wrapContentSize()
            .padding(top = 16.dp)
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

@Composable
fun ContainerView(totalDuration: Int) {
    var buttonPosition = remember { mutableStateOf(0.dp) }
    val maxWidth = remember { mutableStateOf(0.dp) }
    val trackDuration = remember { mutableStateOf(totalDuration) }
    val currentTime = remember { mutableStateOf(0) }
    val timeLeft = remember { mutableStateOf(0) }
    Box(
        modifier = Modifier
            .wrapContentSize()
            .background(mainBackground)
            .padding(top = 20.dp)
            .layout { measurable, constraints ->
                maxWidth.value = constraints.maxWidth.toDp()
                val placeable = measurable.measure(constraints)
                layout(placeable.width, placeable.height) {
                    placeable.place(0, 0)
                }
            },
        contentAlignment = Alignment.Center
    ) {
        MusicPlayerProgressBar(
            modifier = Modifier.fillMaxWidth(),
            buttonPosition = buttonPosition.value,
            maxWidth = maxWidth.value,
            onButtonPositionChange = { newPosition ->
                buttonPosition.value = newPosition
            },
            onProgressBarClick = { clickPosition ->
                buttonPosition.value = clickPosition.coerceIn(0.dp, maxWidth.value)
                currentTime.value = ((clickPosition.value / maxWidth.value.value) * trackDuration.value).toInt()
                timeLeft.value = (trackDuration.value - currentTime.value)
            }
        )
        Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.TopStart)
        {
            DragGestureButton(
                buttonPosition = buttonPosition.value,
                maxWidth = maxWidth.value,
                onButtonPositionChange = { newPosition ->
                    buttonPosition.value = newPosition
                    currentTime.value = ((newPosition.value / maxWidth.value.value) * trackDuration.value).toInt()
                    timeLeft.value = (trackDuration.value - currentTime.value)

                }
            )
        }
        TextViewProgressBar(currentTime = currentTime.value, timeLeft = timeLeft.value)
    }
}

@Composable
fun MusicPlayerProgressBar(
    modifier: Modifier = Modifier,
    buttonPosition: Dp,
    maxWidth: Dp,
    onButtonPositionChange: (Dp) -> Unit,
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
                    val clickPosition = offset.x
                    onProgressBarClick(clickPosition.toDp())
                }
            },
        contentAlignment = Alignment.CenterStart
    ) {
        // Background layer
        Spacer(
            modifier = Modifier
                .fillMaxHeight()
                .fillMaxWidth()
                .background(backgroundColor)
        )
        // Progress layer
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
    onButtonPositionChange: (Dp) -> Unit
) {
    Box(
        modifier = Modifier
            .offset { IntOffset(buttonPosition.value.dp.roundToPx(), 0) }
            .height(20.dp)
            .width(20.dp)
            .background(gestureButton, RoundedCornerShape(128.dp))
            .draggable(
                orientation = Orientation.Horizontal,
                state = rememberDraggableState { delta ->
                    println("Delta = " + delta)
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
fun TextViewProgressBar(currentTime : Int,timeLeft : Int)
{
    Row(
        Modifier
            .fillMaxWidth()) {
        Box(modifier = Modifier
            .fillMaxWidth()
            .padding(top = 30.dp), contentAlignment = Alignment.CenterStart)
        {
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

fun formatTime(seconds: Int): String {
    val minutes = seconds / 60
    val remainingSeconds = seconds % 60
    return "$minutes:${String.format("%02d", remainingSeconds)}"
}

@Composable
@Preview
fun ProgressBarView()
{
    ContainerView(111)
}
