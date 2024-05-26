package com.example.musicmobileapp.controllers

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.musicmobileapp.models.screens.SearchScreenModel
import com.example.musicmobileapp.network.SearchApiInterface
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SearchScreenController(
    private val searchApiInterface: SearchApiInterface
) : ViewModel() {

    private val cScope = CoroutineScope(Dispatchers.IO)
    val isNotActive : MutableState<Boolean> = mutableStateOf(false)
    val liveSearchData: MutableLiveData<List<SearchScreenModel>> = MutableLiveData(emptyList())
    val isDialogShow : MutableState<Boolean> = mutableStateOf(false)

    fun dataLoad(name: String)
    {
        cScope.launch {
            searchApiInterface.searchData(name).collect() {response ->
                withContext(Dispatchers.Main)
                {
                    liveSearchData.value = response
                    response.forEachIndexed { index, innerList ->
                        Log.d("SearchScreenController", "Inner list $index:, SeatchModel $innerList")
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