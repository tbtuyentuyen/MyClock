package com.example.myclock

import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doOnTextChanged
import com.example.myclock.databinding.ActivityRegisterBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase


class RegisterActivity: AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var database: FirebaseDatabase


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Initialize Firebase Auth and Firebase Database
        firebaseAuth = Firebase.auth
        database = FirebaseDatabase.getInstance()

        val tvEmail = binding.textviewEmailRegister
        val tvPassword = binding.textviewPasswordRegister
        val tvName = binding.textviewNameRegister

        tvEmail.editText?.doOnTextChanged { _, _, _, _ ->
            tvEmail.error = null
        }

        tvPassword.editText?.doOnTextChanged { _, _, _, _ ->
            tvPassword.error = null
        }

        tvName.editText?.doOnTextChanged { _, _, _, _ ->
            tvName.error = null
        }


        binding.buttonSignup.setOnClickListener {
            var email = tvEmail.editText?.text.toString().trim()
            var password = tvPassword.editText?.text.toString().trim()
            var name = tvName.editText?.text.toString().trim()

            if (email.isEmpty()) {
                tvEmail.error = "Email cannot be empty"
                //Log.d(TAG, tvEmail.error)
                return@setOnClickListener
            }

            if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                tvEmail.error = "Invalid email address"
                return@setOnClickListener
            }

            if (name.isEmpty()) {
                tvName.error = "Name cannot be empty"
                return@setOnClickListener
            }

            if (password.isEmpty()) {
                tvPassword.error = "Password cannot be empty"
                return@setOnClickListener
            }

            if (password.length < 6) {
                tvPassword.error = "Password must contain at least 6 characters"
                return@setOnClickListener
            }

            firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        // Sign in success, update UI with the signed-in user's information
                        Log.d(TAG, "createUserWithEmail:success")

                        val databaseRef = database.reference.child("users").child(firebaseAuth.currentUser!!.uid)
                        val user = userClass(email, name, password)
                        databaseRef.setValue(user).addOnSuccessListener {
                            Toast.makeText(this,"Success!",Toast.LENGTH_SHORT).show()
                            val intent = Intent(this, SignInActivity::class.java)
                            startActivity(intent)


                        }.addOnFailureListener{

                            Toast.makeText(this,"Failed!",Toast.LENGTH_SHORT).show()

                        }
                        finish()
                    } else {
                        // If sign in fails, display a message to the user.
                        Log.w(TAG, "createUserWithEmail:failure", task.exception)
                        Toast.makeText(
                            this,
                            "Authentication failed.",
                            Toast.LENGTH_SHORT,
                        ).show()
                        finish()
                    }
                }


        }


    }
}