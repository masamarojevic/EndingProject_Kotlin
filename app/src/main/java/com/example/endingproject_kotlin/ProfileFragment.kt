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

        val sharedViewModel: SharedViewModel by activityViewModels()
        _binding = FragmentProfileBinding.inflate(layoutInflater, container, false)
        val view = binding.root

        db= FirebaseDatabase.
        getInstance("https://horoscope-f10af-default-rtdb.europe-west1.firebasedatabase.app")
            .getReference("users")

        //id
        val tv_displayZodiac = binding.tvDisplayProfileZodiac
        val tv_displayPositive = binding.tvDisplayPosAttributes
        val tv_displayNegative = binding.tvDisplayNegAttributes
        val btn_deleteUser =binding.btnDeleteUser
        val btn_signOutUser = binding.btnSignOut

        btn_deleteUser.setOnClickListener {


            deleteUser(sharedViewModel.currentUserId.value)

        }
        btn_signOutUser.setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.action_profileFragment_to_logginFragment)
        }


        //fetch
        lifecycleScope.launch {
            sharedViewModel.currentUserId.value?.let { userId ->
                sharedViewModel.fetchTraits(userId)
            }
        }

        lifecycleScope.launch {
            sharedViewModel.traits.collect{
                traits -> traits?.let {
                tv_displayPositive.text = it.positiveTraits.joinToString(separator = ", ")
                tv_displayNegative.text = it.negativeTraits.joinToString(separator = ", ")
            }
            }
            }


        lifecycleScope.launch{
            sharedViewModel.zodiacSign.collect{ zodiacSign ->
                   if (zodiacSign != null){
                       tv_displayZodiac.text = zodiacSign
                   }
            }
        }


      return view
    }
    private fun deleteUser(userId:String?){
        // Navigation.findNavController(view).navigate(R.id.action_profileIcons_to_mainPageFragment)


        if (userId != null) {
            println("user id : $userId")
            db.child(userId).removeValue().addOnSuccessListener {
                Toast.makeText(activity, "User deleted successfully", Toast.LENGTH_LONG).show()
                view?.let { it1 -> Navigation.findNavController(it1).navigate(R.id.action_profileFragment_to_logginFragment) }
            }.addOnFailureListener {
                Toast.makeText(activity, "profile was not successfully deleted", Toast.LENGTH_LONG).show()
            }
        }
    }



    }


