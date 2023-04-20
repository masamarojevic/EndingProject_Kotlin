package com.example.endingproject_kotlin.CurrentUser

import androidx.lifecycle.ViewModel
import com.example.endingproject_kotlin.Traits
import com.example.endingproject_kotlin.User
import com.example.endingproject_kotlin.ZodiacSigns
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class UserViewModel: ViewModel() {
    private val _currentUser = MutableStateFlow<User?>(null)
    val currentUser = _currentUser.asStateFlow()

    private val _currentUserId = MutableStateFlow<String?>(null)
    val currentUserId = _currentUserId.asStateFlow()

    fun getCurrentUser(user:User?){
        _currentUser.value = user

        _currentUserId.value = user?.userId
    }
}