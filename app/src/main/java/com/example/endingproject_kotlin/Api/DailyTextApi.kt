package com.example.endingproject_kotlin.Api

import retrofit2.Call
import retrofit2.http.GET

interface DailyTextApi {
@GET("/api/quotes/random")
fun getInfo(): Call<Dailytext>
}