package com.example.endingproject_kotlin

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import com.example.endingproject_kotlin.SharedViewModel.SharedViewModel
import com.example.endingproject_kotlin.databinding.FragmentMainPageBinding
import com.example.endingproject_kotlin.databinding.FragmentProfileBinding
import com.google.firebase.database.*
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch


class ProfileFragment : Fragment() {
    private var _binding: FragmentProfileBinding?=null
    private val binding get() = _binding!!
    private lateinit var db : DatabaseReference


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        _binding = FragmentProfileBinding.inflate(layoutInflater, container, false)
        val view = binding.root

        //database
        db= FirebaseDatabase.
        getInstance("https://horoscope-f10af-default-rtdb.europe-west1.firebasedatabase.app")
            .getReference("users")

        //viewmodel
        val sharedViewModel: SharedViewModel by activityViewModels()



        //id
        val tv_displayZodiac = binding.tvDisplayProfileZodiac
        val tv_displayPositive = binding.tvDisplayPosAttributes
        val tv_displayNegative = binding.tvDisplayNegAttributes
        val btn_deleteUser =binding.btnDeleteUser
        val btn_signOutUser = binding.btnSignOut
        val btn_homeButton = binding.ibHome

       //global navigation
        btn_homeButton.setOnClickListener {
           view->view.findNavController().navigate(R.id.action_global_mainPageFragment2)
     }

        //delete user
        btn_deleteUser.setOnClickListener {
            deleteUser(sharedViewModel.currentUserId.value)
        }
        //sign out user
        btn_signOutUser.setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.action_profileFragment_to_logginFragment)
        }


        //fetch traits
        lifecycleScope.launch {
            sharedViewModel.currentUserId.value?.let { userId ->
                sharedViewModel.fetchTraits(userId)
            }
        }

       //display the pos and neg traits
        lifecycleScope.launch {
            sharedViewModel.traits.collect{
                traits -> traits?.let {
                tv_displayPositive.text = it.positiveTraits.joinToString(separator = ", ")
                tv_displayNegative.text = it.negativeTraits.joinToString(separator = ", ")
            }
            }
            }

       //display zodiacSign
        lifecycleScope.launch{
            sharedViewModel.zodiacSign.collect{ zodiacSign ->
                   if (zodiacSign != null){
                       tv_displayZodiac.text = zodiacSign
                   }
            }
        }


      return view
    }
    //function for deleting user
    private fun deleteUser(userId:String?){

        if (userId != null) {

            db.child(userId).removeValue().addOnSuccessListener {
                Toast.makeText(activity, "User deleted successfully", Toast.LENGTH_LONG).show()
                view?.let { it1 -> Navigation.findNavController(it1).navigate(R.id.action_profileFragment_to_logginFragment) }
            }.addOnFailureListener {
                Toast.makeText(activity, "profile was not successfully deleted", Toast.LENGTH_LONG).show()
            }
        }
    }



    }


