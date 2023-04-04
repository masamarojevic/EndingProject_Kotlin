package com.example.endingproject_kotlin.ProfilePix

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class ProfilePixViewModel : ViewModel() {
    private val _profilePixState = MutableLiveData(ProfilePixUiState())
    private val _uiState = MutableStateFlow((ProfilePixUiState()))
    val uiState: StateFlow<ProfilePixUiState> = _uiState.asStateFlow()
    val profilePixState: LiveData<ProfilePixUiState>
        get() = _profilePixState

    fun updateProfilePicture(newPicture: Int) {
        val newState = ProfilePixUiState(defaultPix = newPicture)
        _profilePixState.value = newState
    }

    }



