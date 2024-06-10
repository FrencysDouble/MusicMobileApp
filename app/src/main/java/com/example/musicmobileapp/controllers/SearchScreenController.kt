package com.example.musicmobileapp.controllers

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.musicmobileapp.models.screens.SearchScreenModel
import com.example.musicmobileapp.network.SearchApiInterface
import com.example.musicmobileapp.network.api.ApiResponse
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SearchScreenController(
    private val searchApiInterface: SearchApiInterface
) : ViewModel() {

    val isNotActive : MutableState<Boolean> = mutableStateOf(false)
    val liveSearchData: MutableLiveData<List<SearchScreenModel>> = MutableLiveData(emptyList())
    val isDialogShow : MutableState<Boolean> = mutableStateOf(false)

    fun dataLoad(name: String) {
        viewModelScope.launch {
            searchApiInterface.searchData(name).collect { response ->
                withContext(Dispatchers.Main) {
                    when (response) {
                        is ApiResponse.Success -> {
                            liveSearchData.value = response.data
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


    fun searchBarActive()
    {
        isNotActive.value = true
    }

    fun searchBarDisable()
    {
        isNotActive.value = false
    }


    fun dialogActive()
    {
        isDialogShow.value = true
    }

    fun dialogDisable()
    {
        isDialogShow.value = false
    }

}