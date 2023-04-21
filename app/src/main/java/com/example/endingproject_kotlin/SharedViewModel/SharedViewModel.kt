package com.example.endingproject_kotlin.SharedViewModel

import androidx.lifecycle.ViewModel
import com.example.endingproject_kotlin.Api.DailyTextApi
import com.example.endingproject_kotlin.Api.Dailytext

import com.example.endingproject_kotlin.R
import com.example.endingproject_kotlin.Traits
import com.example.endingproject_kotlin.User
import com.google.firebase.database.FirebaseDatabase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.tasks.await
import com.google.firebase.database.ktx.getValue
import retrofit2.*
import retrofit2.converter.gson.GsonConverterFactory

class SharedViewModel: ViewModel() {
    //user, userId
    private val _currentUser = MutableStateFlow<User?>(null)
    val currentUser = _currentUser.asStateFlow()

    private val _currentUserId = MutableStateFlow<String?>(null)
    val currentUserId = _currentUserId.asStateFlow()

    fun getCurrentUser(user:User?){
        _currentUser.value = user

        _currentUserId.value = user?.userId
    }

    //uistate for username
    private val _username =MutableStateFlow<String?>(null)
    val username: StateFlow<String?> = _username.asStateFlow()

    fun setUsername(username:String){
        _username.value = username
    }

    //uistate for zodiac sign
    private val _zodiacSign = MutableStateFlow<String?>(null)
    val zodiacSign: StateFlow<String?> = _zodiacSign.asStateFlow()

    fun setZodiacSign(zodiac: String) {
        _zodiacSign.value = zodiac
    }

    //uistate for profile image
    private val _profilePix = MutableStateFlow<Int?>( R.drawable.profile_picture)
    val profilePix: StateFlow<Int?> = _profilePix.asStateFlow()

    fun updateProfilePicture(newPicture: Int) {
        _profilePix.value = newPicture
    }

    //uistate for random quote
    private val _randomQuote = MutableStateFlow<String?>(null)
    val randomQuote : StateFlow<String?> = _randomQuote.asStateFlow()

    fun fetchRandomQuote() {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://zenquotes.io/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val randomQuoteApi = retrofit.create<DailyTextApi>().getInfo()
        randomQuoteApi.enqueue(object : Callback<Dailytext> {
            override fun onResponse(call: Call<Dailytext>, response: Response<Dailytext>) {
                if (response.isSuccessful) {
                    val dailytext = response.body()
                    if (dailytext != null && dailytext.isNotEmpty()) {
                        val randomQuote = dailytext.random()
                        _randomQuote.value = randomQuote.q
                    }
                } else {
                    println("error")
                }
            }

            override fun onFailure(call: Call<Dailytext>, t: Throwable) {
                //print out error todo: toast 404 not found
                println(t.printStackTrace())
            }
        })
    }

    private val _quoteFetched = MutableStateFlow(false)
    val quoteFetched :StateFlow<Boolean> = _quoteFetched.asStateFlow()

    fun setQuoteFetchedStatus(status: Boolean){
        _quoteFetched.value=status
    }

    val traits = MutableStateFlow<Traits?>(null)

    suspend fun fetchTraits(userId: String){
        val db = FirebaseDatabase.getInstance("https://horoscope-f10af-default-rtdb.europe-west1.firebasedatabase.app")
            .getReference("users")
        val userSnapshot = db.child(userId).get().await()

        val user =userSnapshot.getValue(User::class.java)
        if(user != null){
            traits.value = user.traits
        }
    }



}