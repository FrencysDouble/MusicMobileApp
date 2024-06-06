package com.example.musicmobileapp.controllers

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.musicmobileapp.models.dto.PlaylistDTO
import com.example.musicmobileapp.models.screens.PlaylistScreenModel
import com.example.musicmobileapp.network.PlaylistApiInterface
import com.example.musicmobileapp.network.api.ApiResponse
import com.example.musicmobileapp.security.UserSecurityManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class PlaylistController(
    private val playlistApiInterface: PlaylistApiInterface,
    private val userSecurityManager: UserSecurityManager
) : ViewModel() {

    private val userId = userSecurityManager.getUserSession().first!!.toLong()

    val livePlaylistDataList: MutableLiveData<List<PlaylistScreenModel>> = MutableLiveData()

    val livePlaylistData : MutableLiveData<PlaylistScreenModel> = MutableLiveData()

    init {
        dataLoad()
    }

    fun createPlaylist(name: String)
    {
        viewModelScope.launch {
            playlistApiInterface.createPlaylist(PlaylistDTO(name, userId)).collect{response ->
                withContext(Dispatchers.Main)
                {
                    Log.d("PlaylistController",response.toString())
                }
            }
        }
    }

    fun dataLoad()
    {
        viewModelScope.launch {
            playlistApiInterface.getAllByCreator(userId).collect { response ->
                withContext(Dispatchers.Main)
                {
                    when(response)
                    {
                        is ApiResponse.Success ->
                        {
                            livePlaylistDataList.value = response.data
                        }
                        is ApiResponse.Error ->
                        {
                            Log.d("PlaylistController",response.errorMessage)
                        }
                        is ApiResponse.Loading ->
                        {
                            TODO()
                        }
                    }

                }
            }
        }

    }


    fun oneDataLoad(id: String?)
    {
        viewModelScope.launch {
            playlistApiInterface.getByPlaylistId(id!!.toLong()).collect { response ->
                withContext(Dispatchers.Main)
                {
                    when(response)
                    {
                        is ApiResponse.Success ->
                        {
                            livePlaylistData.value = response.data
                        }
                        is ApiResponse.Error ->
                        {
                            Log.d("PlaylistController",response.errorMessage)
                        }
                        is ApiResponse.Loading ->
                        {
                            TODO()
                        }
                    }
                }
            }
        }
    }
}