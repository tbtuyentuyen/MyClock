package com.example.myclock

import android.content.ContentValues
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.Toast
import androidx.core.widget.doOnTextChanged
import com.example.myclock.databinding.ActivitySignInBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class SignInActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignInBinding
    private lateinit var myPref: SessionManager
    private lateinit var database: FirebaseDatabase
    private var TAG = "TEST_LOGIN"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignInBinding.inflate(layoutInflater)
        setContentView(binding.root)
        myPref = SessionManager(this)

        // Initialize Firebase Database
        database = FirebaseDatabase.getInstance()
        checkLogin()
        var dbID = ""

        // Initialize Email, Password textField
        var tvID = binding.tvID

        // Reset state input when have change on it
        tvID.editText?.doOnTextChanged { _, _, _, _ ->
            tvID.error = null
        }

        // Check remember me
        if (myPref.isRememberMe()!!) {
            Log.d(TAG, "Set text because have remember me")
            tvID.editText?.setText(myPref.getUserName().toString())
        } else {
            Log.d(TAG, "Set text failed because don't have remember me")
        }


        myPref.setUser(false)
        Log.d("Check", "111111111----------------${myPref.isUser()}")

        binding.buttonSignIn.setOnClickListener {
            val id = tvID.editText?.text.toString().trim()
            // Set error when empty input email, password
            if (id.isEmpty()) {
                tvID.error = "User ID cannot be empty"
                return@setOnClickListener
            }

            checkUser(id)

            Log.d("Check", "----------------${myPref.isUser()}")
            if(myPref.isUser() == false){
                tvID.error = "Wrong User ID"
                return@setOnClickListener
            }

            if (binding.checkboxRememberMe.isChecked) {
                Log.d(TAG, "Checked Box Remember me")
                myPref.setRememberMe(true)
            } else {
                Log.d(TAG, "Unchecked Box Remember me")
                myPref.setRememberMe(false)
            }
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }

    }



    private fun checkLogin() {
        if (myPref.isLogin()!!) {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun checkUser(id: String){
        database.reference.child("users").addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                var count = 0
                for (i in dataSnapshot.children) {
                    count++
                    var dbID = i.key.toString()
                    if (dbID == id) {
                        Log.d("Equal", "$dbID----$count")
                        myPref.setInfoUser(dbID)
                        myPref.setUser(true)
                        break
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                // Failed to read value
                Log.w(ContentValues.TAG, "Failed to read value.", error.toException())
            }
        })
    }
}



