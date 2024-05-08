package com.example.musicmobileapp.controllers

import androidx.lifecycle.ViewModel
import com.example.musicmobileapp.network.MusicApiInterface
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MusicPlayerController(private val musicApiInterface: MusicApiInterface) : ViewModel() {

    private val cScope = CoroutineScope(Dispatchers.IO)

    private fun test() {
        cScope.launch {
            try {

                val response = musicApiInterface.streamMusic(1)
                if (response.isSuccessful) {

                }
                else {
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

}