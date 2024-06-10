package com.example.musicmobileapp.main_ui

import android.util.Log
import android.view.LayoutInflater
import android.widget.FrameLayout
import androidx.annotation.OptIn
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
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonColors
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.media3.common.Player
import androidx.media3.common.Timeline
import androidx.media3.common.util.UnstableApi
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.DefaultTimeBar
import androidx.media3.ui.TimeBar
import androidx.navigation.NavHostController
import com.example.musicmobileapp.R
import com.example.musicmobileapp.controllers.MusicPlayerController
import com.example.musicmobileapp.network.service.MusicInterface
import com.example.musicmobileapp.ui.theme.mainBackground
import com.example.musicmobileapp.ui.theme.mainBackgroundAccent
import com.example.musicmobileapp.ui.theme.mainPrimary
import com.example.musicmobileapp.ui.theme.textPrimary
import com.example.musicmobileapp.ui.theme.textSecondary
import kotlinx.coroutines.delay


@Composable
fun MusicPlayerScreen(
    navController: NavHostController,
    controller: MusicPlayerController,
    player: ExoPlayer,
    trackId: String?,
    musicService: MusicInterface
)

{

    LaunchedEffect(trackId) {
        trackId?.let { controller.dataLoad(it.toLong()) }
    }

    val music by controller.musicData.observeAsState()
    val isPlaying by remember { mutableStateOf(controller.isPlaying) }
    var currentPosition by remember { mutableStateOf(0) }

    LaunchedEffect(key1 = player.currentPosition, key2 = player.isPlaying) {
        while (true) {
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
            music?.let { ImageLoading.loadImage(url = it.imageUrl,Modifier.size(350.dp), context = LocalContext.current) }
            ContainerView(player.duration.toInt(),currentPosition,player)
            Column(
                Modifier
                    .fillMaxWidth()
                    .padding(top = 25.dp)) {
                Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                    music?.let { Text(text = it.name, fontSize = 23.sp) }
                    IconButton(onClick = { /*TODO*/ },
                        Modifier
                            .wrapContentSize()
                            .padding(start = 110.dp)) {
                        Image(painter = painterResource(id = R.drawable.ma_heartp), contentDescription ="" )

                    }
                }
                music?.let { Text(text = it.artistName) }
            }
            MainPlayer(musicService,player,isPlaying)

        }
    }

}
@Composable()
fun MainPlayer(musicService: MusicInterface, player: ExoPlayer, isPlaying: MutableState<Boolean>)
{
    val repeatMode = remember { mutableStateOf(player.repeatMode) }
    Row (
        Modifier
            .fillMaxWidth()
            .padding(top = 100.dp), horizontalArrangement = Arrangement.Center)
    {
        IconButton(onClick = {
            Log.d("Repeat State = ", repeatMode.value.toString())
            when(repeatMode.value) {
            Player.REPEAT_MODE_ONE -> {
                musicService.onRepeatModeOff()
                repeatMode.value = Player.REPEAT_MODE_OFF
            }
            Player.REPEAT_MODE_OFF ->
            {
                musicService.onRepeatModeOn()
                repeatMode.value = Player.REPEAT_MODE_ONE
            }

        } }
            ,Modifier.padding(end = 30.dp))
        {
            Icon(painter = painterResource(id = if(repeatMode.value == Player.REPEAT_MODE_ONE)  R.drawable.ma_rotate_active else R.drawable.ma_rotate),
                contentDescription = "")
        }


        IconButton(onClick = { /*TODO*/ },Modifier.padding(end = 20.dp))
        {
            Image(painter = painterResource(id = R.drawable.ma_skip_previous), contentDescription ="")
        }
        IconButton(onClick = {
            if (player.isPlaying) {
                musicService.onPause()
            }
            else
            {
                musicService.onStart()
            }
        }) {
            Image(painter = painterResource(
                id = when(isPlaying.value) {
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
fun ContainerView(totalDuration: Int, currentTimesPosition: Int, player: ExoPlayer) {

    var trackDuration by remember { mutableStateOf(totalDuration) }
    var currentTime by remember { mutableStateOf(currentTimesPosition) }
    var timeLeft by remember { mutableStateOf(totalDuration - currentTimesPosition) }

    LaunchedEffect(totalDuration, currentTimesPosition) {
        trackDuration = totalDuration
        currentTime = currentTimesPosition

        updateProgressState(
            currentTimesPosition = currentTime,
            totalDuration = trackDuration,
            onUpdateTimeLeft = { newTimeLeft -> timeLeft = newTimeLeft }
        )
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(80.dp)
            .background(mainBackground)
            .padding(top = 20.dp),
        contentAlignment = Alignment.Center
    ) {
        TestTimeBar(player = player)

        TextViewProgressBar(currentTime = currentTime, timeLeft = timeLeft)
    }
}

@OptIn(UnstableApi::class)
@Composable
fun TestTimeBar(player: ExoPlayer) {
    var scrubberPosition by remember { mutableStateOf(player.currentPosition) }

    LaunchedEffect(player) {
        while (true) {
            scrubberPosition = player.currentPosition
            delay(1000)
        }
    }

    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        AndroidView(
            modifier = Modifier.fillMaxWidth(),
            factory = { ctx ->
                val view = LayoutInflater.from(ctx).inflate(R.layout.custom_time_bar, null) as FrameLayout
                val timeBar = view.findViewById<DefaultTimeBar>(R.id.custom_time_bar)

                timeBar.addListener(object : TimeBar.OnScrubListener {
                    override fun onScrubStart(timeBar: TimeBar, position: Long) {
                    }

                    override fun onScrubMove(timeBar: TimeBar, position: Long) {
                        scrubberPosition = position
                    }

                    override fun onScrubStop(timeBar: TimeBar, position: Long, canceled: Boolean) {
                        player.seekTo(position)
                    }
                })

                player.addListener(object : Player.Listener {
                    override fun onTimelineChanged(timeline: Timeline, reason: Int) {
                        if (timeline.windowCount > 0) {
                            val duration = timeline.getWindow(0, Timeline.Window()).durationMs
                            timeBar.setDuration(duration)
                        }
                    }

                    override fun onPositionDiscontinuity(reason: Int) {
                        timeBar.setPosition(player.currentPosition)
                    }

                    override fun onIsPlayingChanged(isPlaying: Boolean) {
                        if (isPlaying) {
                            timeBar.setPosition(player.currentPosition)
                        }
                    }

                    override fun onPlaybackStateChanged(state: Int) {
                        if (state == Player.STATE_READY) {
                            timeBar.setDuration(player.duration)
                            timeBar.setPosition(player.currentPosition)
                        }
                    }
                })

                timeBar.setDuration(player.duration)
                timeBar.setPosition(player.currentPosition)

                view
            },
            update = { view ->
                val timeBar = view.findViewById<DefaultTimeBar>(R.id.custom_time_bar)
                timeBar.setPosition(scrubberPosition)
            }
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
    onUpdateTimeLeft: (Int) -> Unit
) {
    onUpdateTimeLeft(totalDuration - currentTimesPosition)
}

