package com.example.endingproject_kotlin

data class User (
    var username:String="",
    val password:String="",
    val zodiacSign:ZodiacSigns?=null,
    val traits: Traits?=null,
    val userId:String=""
        ){

}