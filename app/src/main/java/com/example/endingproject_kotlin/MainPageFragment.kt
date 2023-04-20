package com.example.endingproject_kotlin

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import com.example.endingproject_kotlin.Api.DailyTextApi
import com.example.endingproject_kotlin.Api.Dailytext
import com.example.endingproject_kotlin.CurrentUser.UserViewModel
import com.example.endingproject_kotlin.ProfilePix.ProfilePixViewModel
import com.example.endingproject_kotlin.ZodiacSign.ZodiacSignViewModel
import com.example.endingproject_kotlin.databinding.FragmentMainPageBinding
import com.google.firebase.database.*
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

        _binding = FragmentMainPageBinding.inflate(layoutInflater, container, false)
        val view = binding.root

        val userViewModel :UserViewModel by activityViewModels()
        val viewModel: ProfilePixViewModel by activityViewModels()
        val viewModelZodiac: ZodiacSignViewModel by viewModels()



       db= FirebaseDatabase.
        getInstance("https://horoscope-f10af-default-rtdb.europe-west1.firebasedatabase.app")
            .getReference("users")


        //id
        val profilPicture = binding.ibSelectProfilePix
        val displayName = binding.tvDisplayText
        val displayTextapi = binding.tvRandomText
        val monthInput = binding.etMonthInput
        val dayInput = binding.etDayInput
        val btnSubmit = binding.btnSubmit
        val displaySign = binding.tvZodiacSign


        //updating the zodiac sign
        btnSubmit.setOnClickListener {

             val currentUser = userViewModel.currentUser.value
             val updatedZodiacSign = calculateZodiac(monthInput,dayInput)
             val updatedTraits = calculateTraits(updatedZodiacSign)

            //current user copies the constructor and becomes an updated user
             if (currentUser != null ){
                 val updatedUser = currentUser.copy(zodiacSign = ZodiacSigns(updatedZodiacSign), traits = updatedTraits)

                 //retrieve the current user ID for entering the specific path to which user , when currentUser is found
                 //we updated that user in database with setValue
                 val currentUserId = userViewModel.currentUserId.value
                 if (currentUserId != null) {
                     FirebaseDatabase.getInstance("https://horoscope-f10af-default-rtdb.europe-west1.firebasedatabase.app")
                         .getReference("users").child(currentUserId).setValue(updatedUser)
                         .addOnSuccessListener {  println("updated")  }.addOnFailureListener { println("failed") }
                 }

                viewModelZodiac.setZodiacSign(updatedZodiacSign)

               //the zodiac with traits for the updated zodiacsign, when non of it is matching return empty list
                when(updatedZodiacSign){
                    "capricorn" -> Traits(
                        listOf("Responsible","disciplined","self-control"),
                        listOf("Know-it-all","unforgiving","expecting the worst"),
                         )
                    "aquarius" -> Traits(
                         listOf("progressive","original","independent"),
                         listOf("runs from emotional expression","uncompromising","temperamental"),

                         )
                    "pisces" -> Traits(
                       listOf("compassionate","artistic","intuitive"),
                        listOf("desire to escape reality","fearful","overly trusting"),

                         )
                    "aries" -> Traits(
                         listOf("courageous","confident","honest"),
                         listOf("impatient","moody","impulsive"),

                         )


                    else -> Traits(emptyList(), emptyList())
                }

             }

           }

        //api
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


        //lifescope for displaying the current users username
        lifecycleScope.launch{
            userViewModel.currentUser.collect{
                currentUser ->
                if (currentUser != null ){
                    val username = currentUser.username
                    displayName.text = username
                }
            }
        }

      //lifescope for displaying zodiac sign
       lifecycleScope.launch{
           viewModelZodiac.zodiacSignUistate.collect{
               state -> displaySign.text = state.zodiacSign
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
    }}
  private fun calculateTraits(zodiacSign: String): Traits{
      return when(zodiacSign){
          "capricorn" -> Traits(
              positiveTraits = listOf("Responsible", "Disciplined", "Self-control"),
              negativeTraits = listOf("Know-it-all", "Unforgiving", "Expecting the worst"),

          )
          "aquarius" -> Traits(
              positiveTraits = listOf("Progressive", "Original", "Independent"),
              negativeTraits = listOf("Runs from emotional expression", "Uncompromising", "Temperamental"),

          )
          "pisces" -> Traits(
              positiveTraits = listOf("Compassionate", "Artistic", "Intuitive"),
              negativeTraits = listOf("Desire to escape reality", "Fearful", "Overly trusting"),

          )
          "aries" -> Traits(
              positiveTraits = listOf("Courageous", "Confident", "Honest"),
              negativeTraits = listOf("Impatient", "Moody", "Impulsive"),

          )
          else -> Traits()
      }
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
             println("invalid input")
          }

       }
       return ""
    }




