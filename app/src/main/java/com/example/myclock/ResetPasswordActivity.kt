package com.example.myclock

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.widget.Toast
import androidx.core.widget.doOnTextChanged
import com.example.myclock.databinding.ActivityResetPasswordBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase

class ResetPasswordActivity : AppCompatActivity() {
    private lateinit var binding: ActivityResetPasswordBinding
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var myPref: SessionManager


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityResetPasswordBinding.inflate(layoutInflater)
        setContentView(binding.root)
        myPref = SessionManager(this)

        // Initialize Firebase Auth and Firebase Database
        firebaseAuth = Firebase.auth

        var tvEmailReset = binding.textviewEmail

        // Reset state input when have change on it
        tvEmailReset.editText?.doOnTextChanged { _, _, _, _ ->
            tvEmailReset.error = null
        }

        binding.buttonReset.setOnClickListener{
            val emailReset = tvEmailReset.editText?.text.toString()

            // Set error when empty input email, password
            if (emailReset.isEmpty()) {
                tvEmailReset.error = "Email cannot be empty"
                return@setOnClickListener
            }

            if(!Patterns.EMAIL_ADDRESS.matcher(emailReset).matches()){
                tvEmailReset.error = "Invalid email address"
                return@setOnClickListener
            }



            firebaseAuth.sendPasswordResetEmail(emailReset).addOnCompleteListener{
                if(it.isSuccessful)
                {
                    Toast.makeText(this,
                        "Email sent successfully to reset your password",
                        Toast.LENGTH_SHORT).show()
                    finish()
                }
                else
                {
                    Toast.makeText(this,
                        "Email does not exist",
                        Toast.LENGTH_SHORT).show()
                    finish()
                }
            }
        }



    }
}