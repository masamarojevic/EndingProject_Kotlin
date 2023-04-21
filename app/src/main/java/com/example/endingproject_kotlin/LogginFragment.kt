package com.example.endingproject_kotlin

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.navigation.Navigation
import com.example.endingproject_kotlin.SharedViewModel.SharedViewModel
import com.example.endingproject_kotlin.databinding.FragmentLogginBinding
import com.google.firebase.database.*



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

        val sharedViewModel: SharedViewModel by activityViewModels()


        db= FirebaseDatabase.
        getInstance("https://horoscope-f10af-default-rtdb.europe-west1.firebasedatabase.app")
           .getReference("users")



        //id
        var username = binding.etUsername
        val password = binding.etUserPassword
        val btnLog = binding.btnLogIn
        val btnReg = binding.btnUserRegister


        //button for logging in the user and it is checking in the database if there is a user with same username
        //if there is it is also checking the password , if the users exist then this user becomes the current user,
        // with all variables assigned. We are also setting the username to be displayed later with the navigation to the mainpage
        btnLog.setOnClickListener {

            val inputUsername = username.text.toString()
            val inputPassword = password.text.toString()

           db.orderByChild("username").equalTo(inputUsername).addListenerForSingleValueEvent(object : ValueEventListener {
               override fun onDataChange(snapshot: DataSnapshot) {

                    if(snapshot.exists())
                   {
                       for (userSnapshot in snapshot.children){

                            val user = userSnapshot.getValue(User::class.java)

                            if (user != null && user.password == inputPassword){

                                val currentUser = userSnapshot.key?.let { it1 ->

                                    User(
                                        username = user.username,
                                        password =user.password,
                                        zodiacSign = user.zodiacSign,
                                        traits = user.traits,
                                        userId = it1
                                    )
                                }
                               sharedViewModel.getCurrentUser(currentUser)

                               sharedViewModel.setUsername(user.username)
                                user.zodiacSign?.let { it1 -> sharedViewModel.setZodiacSign(it1.zodiac) }

                                Navigation.findNavController(view).navigate(R.id.action_logginFragment_to_mainPageFragment)

                            }else{
                               Toast.makeText(activity,"wrong username or password,try again",Toast.LENGTH_LONG).show()
                           }
                       }
                   }
               }

               override fun onCancelled(error: DatabaseError) {
                   Toast.makeText(activity,"something went wrong,try again!",Toast.LENGTH_LONG).show()
               }
           })
        }

        btnReg.setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.action_logginFragment_to_registerFragment)
        }

        return view
    }

}