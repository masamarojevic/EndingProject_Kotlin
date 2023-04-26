package com.example.endingproject_kotlin

import android.annotation.SuppressLint

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import com.example.endingproject_kotlin.SharedViewModel.SharedViewModel
import com.example.endingproject_kotlin.databinding.FragmentMainPageBinding
import com.google.firebase.database.*

import kotlinx.coroutines.launch



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

        //viewmodel
        val sharedViewModel:SharedViewModel by activityViewModels()



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
        val btn_userProfile = binding.btnProfile


        //function to calculate zodiacSign from users input
       fun calculateZodiac(monthInput: EditText,dayInput:EditText):String{
            //converting from string to int
            val month = monthInput.text.toString().toIntOrNull()
            val day = dayInput.text.toString().toIntOrNull()

            //checking if conversion was fine and if no numeric value was inserted then print out error
            if (month == null || day == null){
               Toast.makeText(activity,"You left the field empty",Toast.LENGTH_LONG).show()
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




        //updating the zodiac sign
        btnSubmit.setOnClickListener {

             val currentUser = sharedViewModel.currentUser.value
             val updatedZodiacSign = calculateZodiac(monthInput,dayInput)
             val updatedTraits = calculateTraits(updatedZodiacSign)

            //current user copies the constructor and becomes an updated user
             if (currentUser != null ){
                 val updatedUser = currentUser.copy(zodiacSign = ZodiacSigns(updatedZodiacSign), traits = updatedTraits)

                 //retrieve the current user ID for entering the specific path to which user , when currentUser is found
                 //we updated that user in database with setValue
                 val currentUserId = sharedViewModel.currentUserId.value
                 if (currentUserId != null) {
                     FirebaseDatabase.getInstance("https://horoscope-f10af-default-rtdb.europe-west1.firebasedatabase.app")
                         .getReference("users").child(currentUserId).setValue(updatedUser)
                         .addOnSuccessListener {  println("updated")  }.addOnFailureListener { println("failed") }
                 }

                sharedViewModel.setZodiacSign(updatedZodiacSign)

             }

           }



        //checking if the quote was fetched
        if (!sharedViewModel.quoteFetched.value){
            sharedViewModel.fetchRandomQuote()
            sharedViewModel.setQuoteFetchedStatus(true)
        }




        //lifescope for displaying the current users username
        lifecycleScope.launch{
            sharedViewModel.currentUser.collect{
                currentUser ->
                if (currentUser != null ){
                    val username = currentUser.username
                    displayName.text = username
                }
            }
        }

      //lifescope for displaying zodiac sign
       lifecycleScope.launch{
          sharedViewModel.zodiacSign.collect{
               state -> displaySign.text = state
           }
       }


       //lifecycleScope for changing the profile Picture
      lifecycleScope.launch {
          sharedViewModel.profilePix.collect(){
              newPicture ->
              if (newPicture != null) {
                  profilPicture.setImageResource(newPicture)
              }
          }
      }
        //lifecycleScope for random quote text
        lifecycleScope.launch{
            sharedViewModel.randomQuote.collect(){
                quote -> if (quote != null){
                    displayTextapi.text = quote
                }
            }
        }

        // navigate to another fragment
        profilPicture.setOnClickListener {

            Navigation.findNavController(view).navigate(R.id.action_mainPageFragment_to_profileIcons)
        }

        btn_userProfile.setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.action_mainPageFragment_to_profileFragment)
        }



        return view
    }}

//function to receive zodiacSign Traits
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
          "taurus" -> Traits(
              positiveTraits = listOf("Reliable","Practical","Patient"),
              negativeTraits = listOf("Stubborn","Possessive","Uncompromising")
          )
          "gemini" -> Traits(
              positiveTraits = listOf("Gentle","Curious","Adaptable"),
              negativeTraits = listOf("Nervous","Inconsistent","Indecisive")
          )
          "cancer" -> Traits(
              positiveTraits = listOf("Loyal","Emotional","Highly imaginative"),
              negativeTraits = listOf("Moody","Pessimistic","Manipulative")
          )
          "leo" -> Traits(
              positiveTraits = listOf("Creative","Generous","Warm-hearted"),
              negativeTraits = listOf("Arrogant","Stubborn","Inflexible")
          )
          "virgo" -> Traits(
              positiveTraits = listOf("Analytical","Hardworking","Practical"),
              negativeTraits = listOf("Shy","Overly critical","Serious")
          )
          "libra" -> Traits(
              positiveTraits = listOf("Cooperative","Gracious","Social"),
              negativeTraits = listOf("Indecisive","Avoiding confrontations","Grudge carrying")
          )
          "scorpio" -> Traits(
              positiveTraits = listOf("Resourceful","Powerful","Brave"),
              negativeTraits = listOf("Distrusting","Jealous","Manipulative")
          )
          "sagittarius" -> Traits(
              positiveTraits = listOf("Idealistic","Generous","Humorous"),
              negativeTraits = listOf("Impatient","Blunt","Inconsistent")
          )
          else -> Traits()
      }
  }







