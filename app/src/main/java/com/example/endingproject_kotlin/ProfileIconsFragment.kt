package com.example.endingproject_kotlin

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.Navigation
import com.example.endingproject_kotlin.SharedViewModel.SharedViewModel
import com.example.endingproject_kotlin.databinding.FragmentProfileIconsBinding
import com.google.firebase.database.DatabaseReference


class ProfileIconsFragment : Fragment() {

    private var _binding: FragmentProfileIconsBinding?=null
    private val binding get() = _binding!!
    private lateinit var db : DatabaseReference
    val sharedViewModel:SharedViewModel by activityViewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentProfileIconsBinding.inflate(layoutInflater, container, false)
        val view = binding.root

        //id
        val profilePicTwo=binding.ibChoiceTwo
        val profilePicOne=binding.ibChoiseOne
        val profilePicThree=binding.ibChoiceThree
        val profilePicFour = binding.ibChoiceFour
        val profilePicFive = binding.ibChoiceFive
        val profilePicSix = binding.ibChoiceSix
        val profilePicSeven = binding.ibChoiceSeven
        val profilePicEight=binding.ibChoiceEight
        val profilePicNine =binding.ibChoiceNine




        //update the new image for profile picture
        profilePicTwo.setOnClickListener {
            sharedViewModel.updateProfilePicture(R.drawable.circle_two)
            Navigation.findNavController(view).navigate(R.id.action_profileIcons_to_mainPageFragment)
        }
        profilePicOne.setOnClickListener {
            sharedViewModel.updateProfilePicture(R.drawable.circle_one)
            Navigation.findNavController(view).navigate(R.id.action_profileIcons_to_mainPageFragment)
        }
        profilePicThree.setOnClickListener {
            sharedViewModel.updateProfilePicture(R.drawable.circle_three)
            Navigation.findNavController(view).navigate(R.id.action_profileIcons_to_mainPageFragment)
        }
        profilePicFour.setOnClickListener {
            sharedViewModel.updateProfilePicture(R.drawable.flower_of_life_7230983_1920)
            Navigation.findNavController(view).navigate(R.id.action_profileIcons_to_mainPageFragment)
        }
        profilePicFive.setOnClickListener{
            sharedViewModel.updateProfilePicture(R.drawable.mandala_7008967_1920)
            Navigation.findNavController(view).navigate(R.id.action_profileIcons_to_mainPageFragment)
        }
        profilePicSix.setOnClickListener {
            sharedViewModel.updateProfilePicture(R.drawable.mandala_4462495)
            Navigation.findNavController(view).navigate(R.id.action_profileIcons_to_mainPageFragment)
        }
        profilePicSeven.setOnClickListener {
            sharedViewModel.updateProfilePicture(R.drawable.rosette_7699737_1920)
            Navigation.findNavController(view).navigate(R.id.action_profileIcons_to_mainPageFragment)
        }

        profilePicEight.setOnClickListener {
            sharedViewModel.updateProfilePicture(R.drawable.mandala_1309911_1920)
            Navigation.findNavController(view).navigate(R.id.action_profileIcons_to_mainPageFragment)
        }

        profilePicNine.setOnClickListener {
            sharedViewModel.updateProfilePicture(R.drawable.colorful_1360007)
            Navigation.findNavController(view).navigate(R.id.action_profileIcons_to_mainPageFragment)
        }




        return view
    }


}