package com.example.endingproject_kotlin

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.Navigation
import com.example.endingproject_kotlin.databinding.FragmentRegisterBinding
import com.google.firebase.database.*


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


        db = FirebaseDatabase
            .getInstance("https://horoscope-f10af-default-rtdb.europe-west1.firebasedatabase.app")
            .getReference("users")

        //id
        val etUsername = binding.etRegUsername
        val etPassword = binding.etRegPassword
        val etRePassword = binding.etRegRePassword
        val btnRegister = binding.btnRegister
        val btnNavLogIn = binding.btnNavLogIn


        // button for registering a user and cheking if the username ,password is inserted then going in the database
        //to see if the username already exist, if not then a new user is created  with a new user ID together with other variables
        btnRegister.setOnClickListener {

            val username = etUsername.text.toString().trim()
            val password = etPassword.text.toString().trim()
            val rePassword = etRePassword.text.toString().trim()
            val zodiacSign =ZodiacSigns("")

            if(username.isNotEmpty() && password.isNotEmpty() && rePassword.isNotEmpty()){

                   if (password == rePassword){


                       db.orderByChild("username").equalTo(username).addListenerForSingleValueEvent(object :ValueEventListener {
                           override fun onDataChange(snapshot: DataSnapshot) {

                               if (snapshot.exists()){
                                   Toast.makeText(activity,"username exist",Toast.LENGTH_LONG).show()
                               }
                               else{
                                   val newUserId = db.push().key
                                   val newUser =newUserId?.let { User(username, password, zodiacSign = zodiacSign, traits = Traits(listOf(""), listOf("")), userId = it) }
                                   db.child(newUserId!!).setValue(newUser).addOnSuccessListener {

                                       Toast.makeText(activity,"user registered",Toast.LENGTH_LONG).show()

                                   }.addOnFailureListener{

                                       Toast.makeText(activity,"failed to register",Toast.LENGTH_LONG).show()
                                   }

                               }
                           }

                           override fun onCancelled(error: DatabaseError) {
                               Toast.makeText(activity,"something went wrong! try again",Toast.LENGTH_LONG).show()
                           }

                       })

                   }else{
                       Toast.makeText(activity,"password not matching",Toast.LENGTH_LONG).show()}
            }else{

                Toast.makeText(activity,"fill in the fields",Toast.LENGTH_LONG).show()
            }

        }
        //after registering user can hop on to login site
        btnNavLogIn.setOnClickListener {

            Navigation.findNavController(view).navigate(R.id.action_registerFragment_to_logginFragment2)

        }

        return view
    }
    override fun onDestroyView(){
        super.onDestroyView()
        _binding = null
    }

}


