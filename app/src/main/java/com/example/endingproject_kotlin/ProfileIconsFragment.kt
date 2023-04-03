package com.example.endingproject_kotlin

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import com.example.endingproject_kotlin.ProfilePix.ProfilePixViewModel
import com.example.endingproject_kotlin.databinding.FragmentMainPageBinding
import com.example.endingproject_kotlin.databinding.FragmentProfileIconsBinding
import com.google.firebase.database.DatabaseReference


class ProfileIconsFragment : Fragment() {

    private var _binding: FragmentProfileIconsBinding?=null
    private val binding get() = _binding!!

    val changePixModel: ProfilePixViewModel by viewModels()
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
        val profilePicOne=binding.ibChoiceOne
        val profilePicTwo=binding.ibChoiceTwo
        val profilePicThree=binding.ibChoiceThree

        val cancelButton=binding.iconCancel

        profilePicOne.setOnClickListener {
         changePixModel.changePix(R.drawable.circle_g0c77788da_640)
        }
        profilePicTwo.setOnClickListener {
            changePixModel.changePix(R.drawable.color_circle_g6881cba0e_640)
        }
        profilePicThree.setOnClickListener {
            changePixModel.changePix(R.drawable.color_circle_gfcd681f58_640)
        }
        cancelButton.setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.action_profileIcons_to_mainPageFragment)
        }

        return view
    }


}