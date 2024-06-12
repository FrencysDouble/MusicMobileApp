package com.example.musicmobileapp.controllers

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.musicmobileapp.models.screens.ArtistScreenModel
import com.example.musicmobileapp.network.ArtistApiInterface
import com.example.musicmobileapp.network.api.ApiResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ArtistController(
    private val artistApiInterface: ArtistApiInterface
): ViewModel() {

    val liveArtistData: MutableLiveData<ArtistScreenModel> = MutableLiveData()


    fun dataLoad(id : Long)
    {
        viewModelScope.launch {
            artistApiInterface.getArtistData(id).collect {response ->
                withContext(Dispatchers.Main)
                {
                    when (response) {
                        is ApiResponse.Success -> {
                            liveArtistData.value = response.data
                        }
                        is ApiResponse.Error -> {
                            Log.e("SearchScreenController", "Error: ${response.errorMessage}")
                        }
                        is ApiResponse.Loading -> {
                            Log.d("SearchScreenController", "Loading...")
                        }

                        else -> {}
                    }
                }
            }

        }
    }

}