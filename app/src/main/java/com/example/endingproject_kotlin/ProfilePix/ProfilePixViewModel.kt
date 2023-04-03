package com.example.endingproject_kotlin.ProfilePix

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class ProfilePixViewModel : ViewModel() {
    private val _uiState = MutableStateFlow((ProfilePixUiState()))
    val uiState: StateFlow<ProfilePixUiState> = _uiState.asStateFlow()

    fun changePix(newPix:Int){
        _uiState.update {
            state -> state.copy(
            defaultPix = newPix
            )
        }
    }
}