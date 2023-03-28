package com.example.endingproject_kotlin

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import com.example.endingproject_kotlin.databinding.FragmentLogginBinding
import com.example.endingproject_kotlin.databinding.FragmentRegisterBinding
import com.google.firebase.database.DatabaseReference


class LogginFragment : Fragment() {

    private var _binding: FragmentLogginBinding?=null
    private val binding get() = _binding!!
    private lateinit var db : DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentLogginBinding.inflate(layoutInflater, container, false)
        val view = binding.root

        //id
        var username = binding.etUsername
        val password = binding.etUserPassword
        val btnLog = binding.btnLogIn
        val btnReg = binding.btnUserRegister

        btnReg.setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.action_logginFragment_to_registerFragment)
        }


        return view
    }


}