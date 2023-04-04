package com.example.endingproject_kotlin

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import com.example.endingproject_kotlin.ProfilePix.ProfilePixViewModel
import com.example.endingproject_kotlin.databinding.FragmentProfileIconsBinding
import com.google.firebase.database.DatabaseReference


class ProfileIconsFragment : Fragment() {

    private var _binding: FragmentProfileIconsBinding?=null
    private val binding get() = _binding!!

    val viewModel: ProfilePixViewModel by activityViewModels()
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

        val cancelButton=binding.iconCancel


        //update the new image for profile picture
        profilePicTwo.setOnClickListener {
            viewModel.updateProfilePicture(R.drawable.circle_2)
        }


       //go back to the main page
        cancelButton.setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.action_profileIcons_to_mainPageFragment)
        }

        return view
    }


}