package com.example.endingproject_kotlin

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.os.bundleOf
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.Navigation
import com.example.endingproject_kotlin.ProfilePix.ProfilePixViewModel
import com.example.endingproject_kotlin.databinding.FragmentLogginBinding

import com.example.endingproject_kotlin.databinding.FragmentMainPageBinding
import com.google.firebase.database.DatabaseReference
import kotlinx.coroutines.flow.collect
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

        //activityViewModels used for sharing the viewmodel
        val viewModel: ProfilePixViewModel by activityViewModels()

        //id
        var profilPicture = binding.ibSelectProfilePix

        //livedata to observe the change od data - observe in this case the default profile picture
        viewModel.profilePixState.observe(viewLifecycleOwner) { state ->
            profilPicture.setImageResource(state.defaultPix)
        }

        // open another fragment
        profilPicture.setOnClickListener {

            Navigation.findNavController(view).navigate(R.id.action_mainPageFragment_to_profileIcons)
        }


        return view
    }

    }


