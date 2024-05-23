package com.example.musicmobileapp.network.service

import android.content.ContentValues.TAG
import androidx.annotation.OptIn
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.core.net.toUri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.media3.common.C
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.common.SimpleBasePlayer
import androidx.media3.common.Timeline
import androidx.media3.common.util.Log
import androidx.media3.common.util.UnstableApi
import androidx.media3.datasource.DataSource
import androidx.media3.datasource.DefaultHttpDataSource
import androidx.media3.exoplayer.DefaultLoadControl
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.exoplayer.audio.MediaCodecAudioRenderer
import androidx.media3.exoplayer.source.MediaSource
import androidx.media3.exoplayer.source.ProgressiveMediaSource
import androidx.media3.exoplayer.upstream.DefaultAllocator
import com.example.musicmobileapp.models.PlayerState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File
import java.io.InputStream

class MusicPlayerService (private val player: ExoPlayer) {


    private val cScope = CoroutineScope(Dispatchers.IO)

    val isPlayingState = mutableStateOf(false)

    @OptIn(UnstableApi::class)
    fun initializePlayer(file: File)
    {
        val url = "https://ia804604.us.archive.org/2/items/playboi-carti-magnolia_202201/Playboi%20Carti%20-%20Magnolia.mp3"
        val dataSourceFactory: DataSource.Factory = DefaultHttpDataSource.Factory()

        val mediaSource: MediaSource = ProgressiveMediaSource.Factory(dataSourceFactory)
            .createMediaSource(MediaItem.fromUri(url))


        player.setMediaSource(mediaSource)

        player.prepare()
        player.addListener(playerListener)
    }
    fun createTempAudioFile(inputStream: InputStream?): File {
        if (inputStream != null) {
            return try {
                val file = File.createTempFile("temp", ".mp3")
                val buffer = ByteArray(4096)
                cScope.launch {
                    file.outputStream().use { outputStream ->
                        var bytesRead: Int
                        while (inputStream.read(buffer).also { bytesRead = it } != -1) {
                            outputStream.write(buffer, 0, bytesRead)
                        }
                    }
                }
                file
            } catch (e: Exception) {
                e.printStackTrace()
                File.createTempFile("empty", ".mp3")
            }
        }
        return File.createTempFile("error", ".mp3")
    }

    @OptIn(UnstableApi::class)
    fun start()
    {
        Log.d("FULL Duration",player.duration.toString())
        player.play()

    }

    fun pause()
    {
        player.pause()

    }

    fun clear()
    {
        player.release()
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