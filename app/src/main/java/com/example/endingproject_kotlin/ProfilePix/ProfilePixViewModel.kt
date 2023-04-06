package com.example.endingproject_kotlin.ProfilePix

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.endingproject_kotlin.R
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class ProfilePixViewModel : ViewModel() {

    private val _profilePixState = MutableStateFlow(ProfilePixUiState())
    val profilePixState: StateFlow<ProfilePixUiState> = _profilePixState.asStateFlow()

    fun updateProfilePicture(newPicture: Int) {
        val newState = ProfilePixUiState(defaultPix = newPicture)
        _profilePixState.value = newState
    }

    }



