package com.example.endingproject_kotlin

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.Navigation
import com.example.endingproject_kotlin.ProfilePix.ProfilePixViewModel
import com.example.endingproject_kotlin.databinding.FragmentLogginBinding

import com.example.endingproject_kotlin.databinding.FragmentMainPageBinding
import com.google.firebase.database.DatabaseReference
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

        //viewModel
        val changePixModel: ProfilePixViewModel by viewModels()
        //id
        var profilPicture = binding.ibSelectProfilePix

        //async lifecycle function for state
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            changePixModel.uiState.collect { state ->
                println("ProfilePixViewModel Default profile picture changed to: ${state.defaultPix}")
                profilPicture.setImageResource(state.defaultPix)
            }
        }




        profilPicture.setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.action_mainPageFragment_to_profileIcons)
        }





        return view
    }


}