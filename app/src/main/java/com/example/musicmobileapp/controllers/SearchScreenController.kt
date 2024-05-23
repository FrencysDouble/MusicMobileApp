package com.example.musicmobileapp.controllers

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class SearchScreenController : ViewModel() {

    val isPlaying : MutableState<Boolean> = mutableStateOf(false)

}