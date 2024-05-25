package com.example.musicmobileapp.controllers

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.musicmobileapp.models.screens.AlbumScreenModel
import com.example.musicmobileapp.models.screens.SearchScreenModel
import com.example.musicmobileapp.network.AlbumApiInterface
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AlbumController(private val albumApiInterface: AlbumApiInterface): ViewModel() {

    private val cScope = CoroutineScope(Dispatchers.IO)
    val liveAlbumData: MutableLiveData<AlbumScreenModel> = MutableLiveData()

    fun dataLoad(id: Long) {
        cScope.launch {
            albumApiInterface.getAlbum(id).collect() { response ->
                withContext(Dispatchers.Main)
                {
                    liveAlbumData.value = response
                    Log.d("AlbumController", response.toString())
                }

            }

        }
    }
}