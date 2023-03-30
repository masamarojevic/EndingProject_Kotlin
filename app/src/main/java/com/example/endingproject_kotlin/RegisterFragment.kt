package com.example.endingproject_kotlin

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.endingproject_kotlin.databinding.FragmentRegisterBinding
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase


class RegisterFragment : Fragment() {

   private var _binding: FragmentRegisterBinding?=null
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
        _binding=FragmentRegisterBinding.inflate(layoutInflater,container,false)
        val view = binding.root

        //id
        db = FirebaseDatabase

            .getInstance("https://horoscope-f10af-default-rtdb.europe-west1.firebasedatabase.app")
            .getReference("users")
        val etUsername = binding.etRegUsername
        val etPassword = binding.etRegPassword
        val etRePassword = binding.etRegRePassword
        val btnRegister = binding.btnRegister


        btnRegister.setOnClickListener {
            val username = etUsername.text.toString()
            val password = etPassword.text.toString()
            val rePassword = etRePassword.text.toString()
            val newUser = User(username,password)
            //todo - check if username and pass already exist before pushing it to db
            if (password == rePassword){
             db.push().setValue(newUser).addOnSuccessListener {
                 Toast.makeText(activity,"$newUser has been registered successfully",Toast.LENGTH_LONG).show()
             }.addOnFailureListener {
                 Toast.makeText(activity,"Something went wrong, try again!",Toast.LENGTH_LONG).show()
             }

            }else{
                Toast.makeText(activity,"Password not matching, try again!",Toast.LENGTH_LONG).show()
            }

        }

        return view
    }


}