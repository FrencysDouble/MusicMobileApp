package com.example.musicmobileapp.controllers

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.musicmobileapp.models.dto.TrackModel
import com.example.musicmobileapp.models.screens.SearchScreenModel
import com.example.musicmobileapp.network.MusicApiInterface
import com.example.musicmobileapp.network.api.ApiResponse
import com.example.musicmobileapp.network.service.SelectedTrackItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MoreDialogController(
    private val musicApiInterface: MusicApiInterface,
    private val selectedTrackItem: SelectedTrackItem
) : ViewModel() {

    val liveDialogData: MutableLiveData<TrackModel> = MutableLiveData()

    fun dataLoad()
    {

        val id = selectedTrackItem.selectedItemId.value
        viewModelScope.launch {
            id?.let { musicApiInterface.getById(it.toLong()) }?.collect{response ->
                withContext(Dispatchers.Main)
                {
                    when(response)
                    {
                        is ApiResponse.Success -> {
                            liveDialogData.value = response.data
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