package com.example.musicmobileapp.network.service

import android.app.Service
import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import android.os.IBinder
import androidx.annotation.OptIn
import androidx.core.net.toUri
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.common.util.Log
import androidx.media3.common.util.UnstableApi
import androidx.media3.datasource.DefaultHttpDataSource
import androidx.media3.exoplayer.DefaultLoadControl
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.exoplayer.util.EventLogger
import androidx.media3.session.MediaSession
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File
import java.io.InputStream
import javax.inject.Inject

class MusicPlayerService @Inject constructor(private val player: ExoPlayer) {


    private val cScope = CoroutineScope(Dispatchers.IO)


    @OptIn(UnstableApi::class)
    fun initializePlayer(file: File)
    {

        val mediaItem = MediaItem.Builder().setUri(file.absolutePath.toUri()).build()

        player.setMediaItem(mediaItem)

        player.prepare()

    }
    fun createTempAudioFile(inputStream: InputStream?) : File {
        if (inputStream != null) {
            return try {
                val file = File.createTempFile("temp", ".mp3")
                cScope.launch {
                    file.outputStream().use { outputStream ->
                        inputStream.copyTo(outputStream)
                    }

                }
                file
            } catch (e : Exception) {
                e.printStackTrace()
                File.createTempFile("empty", ".mp3")
            }
        }
        return File.createTempFile("error", ".mp3")
    }

    fun start()
    {
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
            Log.d(TAG, "changed state to $stateString")
        }
    }


}