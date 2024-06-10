package com.example.musicmobileapp.controllers

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.media3.common.Player
import com.example.musicmobileapp.models.dto.TrackModel
import com.example.musicmobileapp.network.MusicApiInterface
import com.example.musicmobileapp.network.api.ApiResponse
import com.example.musicmobileapp.network.service.MusicPlayerService
import com.example.musicmobileapp.network.service.Resource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.InputStream

class MusicPlayerController(
    private val musicApiInterface: MusicApiInterface,
    private val musicPlayerService: MusicPlayerService

) : ViewModel() {

     val musicData : MutableLiveData<TrackModel> = MutableLiveData()

    val isPlaying: MutableState<Boolean> = musicPlayerService.isPlayingState
    private val _musicData = MutableLiveData<Resource<InputStream>>()

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
                //val file = musicPlayerService.createTempAudioFile(inputStream)
                withContext(Dispatchers.Main)
                {
                    //musicPlayerService.initializePlayer()
                }

            } catch (e: Exception) {
                e.printStackTrace()
                _musicData.postValue(Resource.Error("Exception: ${e.message}", e))
            }
        }
    }


    fun dataLoad(id : Long)
    {
        viewModelScope.launch {
            musicApiInterface.getById(id).collect { response ->
                withContext(Dispatchers.Main)
                {
                    when(response)
                    {
                        is ApiResponse.Success -> {
                            musicData.value = response.data
                            musicPlayerService.initializePlayer(id)
                        }
                        is ApiResponse.Error -> {
                            Log.d("MusicPlayerController",response.errorMessage)
                        }
                        is ApiResponse.Loading ->
                        {
                            TODO()
                        }

                        else -> {}
                    }

                }
            }
        }
    }

    override fun onCleared()
    {
        musicPlayerService.clear()
    }

}