package com.example.myclock

import android.content.ContentValues.TAG
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.myclock.databinding.ActivityMainBinding
import com.example.myclock.databinding.ActivitySignInBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var myPref: SessionManager
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var database: FirebaseDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Initialize Firebase Auth and Firebase Database
        firebaseAuth = Firebase.auth
        database = FirebaseDatabase.getInstance()
        initMypPref()

        binding.tvName.text = myPref.getName()
        binding.tvEmail.text = myPref.getUserName()

//        var email = ""
//        var name = ""
//        var iEmail = ""
//        val databaseRef = database.reference.child("users")
//        databaseRef.addValueEventListener(object : ValueEventListener {
//            override fun onDataChange(dataSnapshot: DataSnapshot) {
//                // This method is called once with the initial value and again
//                // whenever data at this location is updated.
//
//                    for(i in dataSnapshot.children){
//                        iEmail = i.child("email").value as String
//                        if(iEmail == email){        //through email to get name
//                            binding.tvName.text = i.child("name").value as String
//
//                            break
//                        }
//                        name = i.child("name").value as String
//                        email = i.child("email").value as String
//                    }
//
//
//            }
//            override fun onCancelled(error: DatabaseError) {
//                // Failed to read value
//                Log.w(TAG, "Failed to read value.", error.toException())
//            }
//        })

//        binding.button.setOnClickListener{
//            myPref.removeData()
//            val intent = Intent(this, SignInActivity::class.java)
//            startActivity(intent)
//            finish()
//        }

        binding.imgLogout.setOnClickListener{
            myPref.removeData()
            val intent = Intent(this, SignInActivity::class.java)
            startActivity(intent)
            finish()
        }


        binding.btSetAlarm.setOnClickListener {
            val intent = Intent(this, SetAlarmActivity::class.java)
            startActivity(intent)
            finish()
            Toast.makeText(this,
                "Select Set Alarm",
                Toast.LENGTH_SHORT).show()
        }

        binding.btTodoList.setOnClickListener {
            Toast.makeText(this,
                "Select Todo List",
                Toast.LENGTH_SHORT).show()
        }

        binding.btTellStory.setOnClickListener {
            Toast.makeText(this,
                "Select Tells Stories",
                Toast.LENGTH_SHORT).show()
        }
    }

    private fun initMypPref() {
        myPref = SessionManager(this)
    }

}