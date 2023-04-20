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

    val sharedViewModel:SharedViewModel by activityViewModels()
    private lateinit var db : DatabaseReference
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

        val cancelButton=binding.iconCancel


        //update the new image for profile picture
        profilePicTwo.setOnClickListener {
            sharedViewModel.updateProfilePicture(R.drawable.circle_two)
        }
        profilePicOne.setOnClickListener {
            sharedViewModel.updateProfilePicture(R.drawable.circle_one)
        }
        profilePicThree.setOnClickListener {
            sharedViewModel.updateProfilePicture(R.drawable.circle_three)
        }



       //go back to the main page
        cancelButton.setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.action_profileIcons_to_mainPageFragment)
        }

        return view
    }


}