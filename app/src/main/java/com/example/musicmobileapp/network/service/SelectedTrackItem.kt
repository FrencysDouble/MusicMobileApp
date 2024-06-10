package com.example.musicmobileapp.network.service

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SelectedTrackItem : ViewModel() {
    private val _selectedItemId = MutableLiveData<Int>()
    val selectedItemId: LiveData<Int> = _selectedItemId

    fun setSelectedItemId(itemId: Int) {
        _selectedItemId.value = itemId
    }
}