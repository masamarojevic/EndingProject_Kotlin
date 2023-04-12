package com.example.endingproject_kotlin

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import com.example.endingproject_kotlin.Api.DailyTextApi
import com.example.endingproject_kotlin.Api.Dailytext
import com.example.endingproject_kotlin.ProfilePix.ProfilePixViewModel
import com.example.endingproject_kotlin.Username.UserNameViewModel
import com.example.endingproject_kotlin.ZodiacSign.ZodiacSignViewModel

import com.example.endingproject_kotlin.databinding.FragmentMainPageBinding
import com.google.firebase.database.*
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import retrofit2.*
import retrofit2.converter.gson.GsonConverterFactory


class MainPageFragment : Fragment() {
    private var _binding: FragmentMainPageBinding?=null
    private val binding get() = _binding!!
    private lateinit var db : DatabaseReference




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    @SuppressLint("UnsafeRepeatOnLifecycleDetector", "ResourceType")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
       val viewModel1: UserNameViewModel by activityViewModels()


        _binding = FragmentMainPageBinding.inflate(layoutInflater, container, false)
        val view = binding.root

        //activityViewModels used for sharing the viewmodel
        val viewModel: ProfilePixViewModel by activityViewModels()

        val viewModelZodiac: ZodiacSignViewModel by viewModels()


        //id
        db= FirebaseDatabase.
        getInstance("https://horoscope-f10af-default-rtdb.europe-west1.firebasedatabase.app")
            .getReference("users")
            .child("zodiac-sign")

        //id
        var profilPicture = binding.ibSelectProfilePix
        var displayName = binding.tvDisplayText
        var displayTextapi = binding.tvRandomText
        var monthInput = binding.etMonthInput
        var dayInput = binding.etDayInput
        var btnSubmit = binding.btnSubmit
        var displaySign = binding.tvZodiacSign



        btnSubmit.setOnClickListener {



               var zodiacSign
               = calculateZodiac(monthInput,dayInput)
          viewModelZodiac.setZodiacSign(zodiacSign)

            db.push().setValue(zodiacSign).addOnSuccessListener {
                Toast.makeText(activity,"$zodiacSign has been added successfully",Toast.LENGTH_LONG).show()
            }
                .addOnFailureListener {
                    Toast.makeText(activity,"something went wrong",Toast.LENGTH_LONG).show()
                }
        }


        val retrofit = Retrofit.Builder()
            .baseUrl("https://zenquotes.io/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val randomQuote =retrofit.create<DailyTextApi>().getInfo()
        randomQuote.enqueue(object : Callback<Dailytext>{
            override fun onResponse(call: Call<Dailytext>, response: Response<Dailytext>) {
              if(response.isSuccessful){
                  val dailytext =response.body()
                  if (dailytext != null && dailytext.isNotEmpty()) {
                      val randomQuote = dailytext.random()
                      displayTextapi.text = randomQuote.q
                  }
              }else{
                  println("error")
              }
            }

            override fun onFailure(call: Call<Dailytext>, t: Throwable) {
                //print out error todo: toast 404 not found
                println(t.printStackTrace())
            }
        })

       lifecycleScope.launch{
           viewModelZodiac.zodiacSignUistate.collect{
               state -> displaySign.text = state.zodiacSign
           }
       }

        //lifecycleScope for displaying the username
        lifecycleScope.launch {
            viewModel1.usernameUiState.collect { state ->
                displayName.text = state.username
            }
        }



       //lifecycleScope for changing the profile Picture
      lifecycleScope.launch {
          viewModel.profilePixState.collect(){
              state -> profilPicture.setImageResource(state.defaultPix)
          }
      }


        // open another fragment
        profilPicture.setOnClickListener {

            Navigation.findNavController(view).navigate(R.id.action_mainPageFragment_to_profileIcons)
        }





        return view
    }


   private fun calculateZodiac(monthInput: EditText,dayInput:EditText):String{
        //converting from string to int
        val month = monthInput.text.toString().toIntOrNull()
        val day = dayInput.text.toString().toIntOrNull()

        //checking if conversion was fine and if no numeric value was inserted then print out error
        if (month == null || day == null){
            println("wrong input try again!")
           return ""
        }


        //all 12 zodiacs
      when (month){
            1-> return if (day<20) {
             "capricorn"
            }else{
                "aquarius"
            }
            2-> return if (day<19){
             "aquarius"
            }else{
              "pisces"
            }
            3 -> return if (day<21){
               "pisces"
            }else{
                "aries"
            }
            4-> return if (day<20){
              "aries"
            }else{
              "Taurus"
            }
            5-> return if (day<19){
            "taurus"
            }else{
              "gemini"
            }
            6-> return if (day<20){
             "Gemini"
            }else{
               "cancer"
            }
            7-> return if (day<21){
             "cancer"
            }else{
                "leo"
            }
            8-> return if (day<22){
            "leo"
            }else{
               "virgo"
            }
            9-> return if (day<22){
                "virgo"
            }else{
               "libra"
            }
            10-> return if (day<22){
            "libra"
            }else{
               "scorpius"
            }
            11-> return if (day<23){
             "scorpius"
            }else{
               "sagittarius"
            }
            12-> return if (day<21){
                "sagittarius"
            }else{
               "capricorn"
            }
          else -> {
              println("invalid month or day please try again ")
          }

        }
       return ""


    }


    }


