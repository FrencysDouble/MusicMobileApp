package com.example.musicmobileapp.models

data class PlayerState(
    val currentPosition: Int,
    val duration: Int,
    var state : Boolean
)
