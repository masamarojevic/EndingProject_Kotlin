package com.example.endingproject_kotlin.Api
//class dailtytext is a list av objects of type dailytextitem
//dailtytext is inherited from : ArrayList<DailytextItem>()
class Dailytext : ArrayList<DailytextItem>()

//  class dailytextitem has quote and
data class DailytextItem(var q:String)
{

}