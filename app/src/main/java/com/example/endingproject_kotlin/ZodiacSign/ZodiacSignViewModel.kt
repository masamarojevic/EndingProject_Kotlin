package com.example.endingproject_kotlin.ZodiacSign

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class ZodiacSignViewModel: ViewModel() {


    private val _zodiacSignUiState = MutableStateFlow(ZodiacsignUistate())
    val zodiacSignUistate : StateFlow<ZodiacsignUistate> =_zodiacSignUiState.asStateFlow()

    fun setZodiacSign(zodiac:String){
        _zodiacSignUiState.value = ZodiacsignUistate(zodiac)
    }
}