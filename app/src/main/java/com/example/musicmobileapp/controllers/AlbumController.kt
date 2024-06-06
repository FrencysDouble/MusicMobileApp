package com.example.musicmobileapp.controllers

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.musicmobileapp.models.screens.AlbumScreenModel
import com.example.musicmobileapp.models.screens.SearchScreenModel
import com.example.musicmobileapp.network.AlbumApiInterface
import com.example.musicmobileapp.network.api.ApiResponse
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AlbumController(private val albumApiInterface: AlbumApiInterface): ViewModel() {

    val liveAlbumData: MutableLiveData<AlbumScreenModel> = MutableLiveData()

    fun dataLoad(id: Long) {
        viewModelScope.launch {
            albumApiInterface.getAlbum(id).collect() { response ->
                withContext(Dispatchers.Main)
                {
                    when (response) {
                        is ApiResponse.Success -> {
                            liveAlbumData.value = response.data
                        }

                        is ApiResponse.Error -> {
                            Log.e("AlbumController", "Error: ${response.errorMessage}")
                        }

                        is ApiResponse.Loading -> {
                            Log.d("AlbumController", "Loading...")
                        }
                    }

                }

            }
        }
    }
}