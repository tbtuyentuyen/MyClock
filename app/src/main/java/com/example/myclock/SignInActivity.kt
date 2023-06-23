package com.example.myclock

import android.content.ContentValues.TAG
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
import kotlinx.coroutines.delay
import java.time.Duration
import java.util.concurrent.TimeUnit
import kotlin.concurrent.thread

class SignInActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignInBinding
    private lateinit var myPref: SessionManager
    private lateinit var database: FirebaseDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignInBinding.inflate(layoutInflater)
        setContentView(binding.root)
        myPref = SessionManager(this)

        // Initialize Firebase Database
        database = FirebaseDatabase.getInstance()
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

        binding.buttonSignIn.setOnClickListener {
            val id = tvID.editText?.text.toString().trim()
            myPref.setInfoUser(id)
            Log.d("ID of id", id)
            // Set error when empty input email, password
            if (id.isEmpty()) {
                tvID.error = "User ID cannot be empty"
                return@setOnClickListener
            }

            val intent = Intent(this, MainActivity::class.java)
            var count = 0
            database.reference.child("users")
                .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    // This method is called once with the initial value and again
                    // whenever data at this location is updated.
                    for (i in dataSnapshot.children) {
                        dbID = i.key.toString()
                        count++
                        if (dbID == id) {
                            Log.d("ID of dbID", "${dbID}")
                            myPref.setInfoUser(dbID)
                            //tvID.error = null
                            startActivity(intent)
                            break
                        }
                    }
                    if(count == dataSnapshot.childrenCount.toInt()){
                        tvID.error = "Wrong User ID"
                    }
                }
                override fun onCancelled(error: DatabaseError) {
                    // Failed to read value
                    Log.w(TAG, "Failed to read value.", error.toException())
                }
            })



            if (binding.checkboxRememberMe.isChecked) {
                Log.d(TAG, "Checked Box Remember me")
                myPref.setRememberMe(true)
            } else {
                Log.d(TAG, "Unchecked Box Remember me")
                myPref.setRememberMe(false)
            }

        }

    }

//    private fun checkLogin() {
//        if (myPref.isLogin()!!) {
//            val intent = Intent(this, MainActivity::class.java)
//            startActivity(intent)
//            finish()
//        }
//    }
}



