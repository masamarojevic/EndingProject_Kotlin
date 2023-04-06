package com.example.endingproject_kotlin.Username

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.endingproject_kotlin.ProfilePix.ProfilePixUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class UserNameViewModel : ViewModel()
{

  private val _usernameUiState =MutableStateFlow(UsernameUiState())
    val usernameUiState : StateFlow<UsernameUiState> = _usernameUiState.asStateFlow()

    fun setUsername(username:String){
        _usernameUiState.value = UsernameUiState(username)
    }
}