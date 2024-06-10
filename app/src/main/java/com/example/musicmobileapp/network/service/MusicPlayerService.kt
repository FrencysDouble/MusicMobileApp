package com.example.musicmobileapp.network.service

import android.content.ContentValues.TAG
import androidx.annotation.OptIn
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.platform.PlatformTextInputInterceptor
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.common.util.Log
import androidx.media3.common.util.UnstableApi
import androidx.media3.datasource.DataSource
import androidx.media3.datasource.DefaultHttpDataSource
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.exoplayer.source.MediaSource
import androidx.media3.exoplayer.source.ProgressiveMediaSource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers

class MusicPlayerService (private val player: ExoPlayer) : MusicInterface {


    private val cScope = CoroutineScope(Dispatchers.IO)

    val isPlayingState = mutableStateOf(false)

    @OptIn(UnstableApi::class)
    fun initializePlayer(id: Long)
    {
        val url = "http://10.0.2.2:8080/api/v1/file/stream/$id"
        val dataSourceFactory: DataSource.Factory = DefaultHttpDataSource.Factory()



        val mediaSource: MediaSource = ProgressiveMediaSource.Factory(dataSourceFactory)
            .createMediaSource(MediaItem.fromUri(url))


        player.setMediaSource(mediaSource)

        player.prepare()
        player.addListener(playerListener)
    }

    @OptIn(UnstableApi::class)
    override fun onStart()
    {
        Log.d("BufferedPosition",player.bufferedPosition.toString())
        Log.d("BufferedPercentage",player.bufferedPercentage.toString())
        Log.d("IsLoading" ,player.isLoading.toString())
        Log.d("FULL Duration",player.duration.toString())
        player.play()

    }

    override fun onPause()
    {
        player.pause()

    }

    fun clear()
    {
        player.release()
    }


    override fun onRepeatModeOn() {
        player.repeatMode = Player.REPEAT_MODE_ONE
    }

    override fun onRepeatModeOff()
    {
        player.repeatMode = Player.REPEAT_MODE_OFF
    }


    private val playerListener = object : Player.Listener {

        @OptIn(UnstableApi::class)
        override fun onPlaybackStateChanged(playbackState: Int) {
            val stateString: String = when (playbackState) {
                ExoPlayer.STATE_IDLE -> "ExoPlayer.STATE_IDLE      -"
                ExoPlayer.STATE_BUFFERING -> "ExoPlayer.STATE_BUFFERING -"
                ExoPlayer.STATE_READY -> "ExoPlayer.STATE_READY     -"
                ExoPlayer.STATE_ENDED -> "ExoPlayer.STATE_ENDED     -"
                else -> "UNKNOWN_STATE             -"
            }

            if (playbackState == ExoPlayer.STATE_READY) {
                Log.d(TAG, "Player is ready, duration: ${player.duration}")
            }
            Log.d(TAG, "changed state to $stateString")
        }

        override fun onIsPlayingChanged(isPlaying: Boolean) {
            isPlayingState.value = isPlaying
        }

    }
}

interface MusicInterface {

    fun onStart()

    fun onPause()

    fun onRepeatModeOn()

    fun onRepeatModeOff()

}