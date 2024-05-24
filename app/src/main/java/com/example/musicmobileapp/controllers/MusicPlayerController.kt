package com.example.musicmobileapp.controllers

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.musicmobileapp.models.PlayerState
import com.example.musicmobileapp.network.MusicApiInterface
import com.example.musicmobileapp.network.service.MusicPlayerService
import com.example.musicmobileapp.network.service.Resource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.InputStream
import javax.inject.Inject

class MusicPlayerController(
    private val musicApiInterface: MusicApiInterface,
    private val musicPlayerService: MusicPlayerService

) : ViewModel() {


    val isPlaying : MutableState<Boolean> = musicPlayerService.isPlayingState
    private val _musicData = MutableLiveData<Resource<InputStream>>()
    val musicData: LiveData<Resource<InputStream>> = _musicData

    private val cScope = CoroutineScope(Dispatchers.IO)

    init {
        dataGetting()
    }

    fun dataGetting() {
        cScope.launch {
            _musicData.postValue(Resource.Loading)
            try {
                val inputStream = withContext(Dispatchers.IO)
                {
                    musicApiInterface.streamMusic(1)
                }
                println(inputStream)
                _musicData.postValue(Resource.Success(inputStream))
               val file = musicPlayerService.createTempAudioFile(inputStream)
                withContext(Dispatchers.Main)
                {
                    musicPlayerService.initializePlayer(file)
                }

            } catch (e: Exception) {
                e.printStackTrace()
                _musicData.postValue(Resource.Error("Exception: ${e.message}", e))
            }
        }
    }

    fun onPlayerPause()
    {
        musicPlayerService.pause()
    }

    fun onPlayerStart()
    {
        musicPlayerService.start()
    }

    override fun onCleared()
    {
        musicPlayerService.clear()
    }

}