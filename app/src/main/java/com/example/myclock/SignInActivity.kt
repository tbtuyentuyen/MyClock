package com.example.myclock

import android.content.ContentValues.TAG
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract.Data
import android.util.Log
import android.util.Patterns
import android.widget.Toast
import androidx.core.widget.doOnTextChanged
import com.example.myclock.databinding.ActivitySignInBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseRegistrar
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.ktx.Firebase
import java.lang.StringBuilder

class SignInActivity : AppCompatActivity() {
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var binding: ActivitySignInBinding
    private lateinit var myPref: SessionManager
    private lateinit var database: FirebaseDatabase
    private var TAG = "TEST_LOGIN"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignInBinding.inflate(layoutInflater)
        setContentView(binding.root)
        myPref = SessionManager(this)

        // Initialize Firebase Auth and Firebase Database
        firebaseAuth = Firebase.auth
        database = FirebaseDatabase.getInstance()
        var name = ""
        checkLogin()

        // Initialize Email, Password textField
        var tvEmail = binding.textviewEmail
        var tvPassword = binding.textviewPassword

        // Reset state input when have change on it
        tvEmail.editText?.doOnTextChanged { _, _, _, _ ->
            tvEmail.error = null
        }
        tvPassword.editText?.doOnTextChanged { _, _, _, _ ->
            tvPassword.error = null
        }

        // Check remember me
        if (myPref.isRememberMe()!!) {
            Log.d(TAG, "Set text because have remember me")
            tvEmail.editText?.setText(myPref.getUserName().toString())
            tvPassword.editText?.setText(myPref.getPassword().toString())
        } else {
            Log.d(TAG, "Set text failed because don't have remember me")
        }

        binding.textviewSignUp.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }

        binding.buttonSignIn.setOnClickListener {
            val email = tvEmail.editText?.text.toString().trim()
            val password = tvPassword.editText?.text.toString().trim()

            // Set error when empty input email, password
            if (email.isEmpty()) {
                tvEmail.error = "Email cannot be empty"
                return@setOnClickListener
            }

            if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                tvEmail.error = "Invalid email address"
                return@setOnClickListener
            }

            if (password.isEmpty()) {
                tvPassword.error = "Password cannot be empty"
                return@setOnClickListener
            }

            var iEmail = ""
            val databaseRef = database.reference.child("users")
            // Read from the database to get name
            databaseRef.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    // This method is called once with the initial value and again
                    // whenever data at this location is updated.
                    for(i in dataSnapshot.children){
                        iEmail = i.child("email").value as String
                        if(iEmail == email){        //through email to get name
                            name = i.child("name").value as String
                            break
                        }
                    }

                    Log.d(TAG, "Value is: $name")

                }

                override fun onCancelled(error: DatabaseError) {
                    // Failed to read value
                    Log.w(TAG, "Failed to read value.", error.toException())
                }
            })



            firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener{
                if(it.isSuccessful)
                {
                    //Set user information to remember me
                    if (binding.checkboxRememberMe.isChecked){
                        Log.d(TAG, "Checked Box Remember me")
                        myPref.setRememberMe(true)
                    }
                    else{
                        Log.d(TAG, "Unchecked Box Remember me")
                        myPref.setRememberMe(false)
                    }

                    //Set myPref to keep login
                    myPref.setLogin(true)
                    myPref.setInfoUser(email, password, name)

                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                    finish()
                }
                else
                {
                    Toast.makeText(this,
                        "Wrong email or password",
                        Toast.LENGTH_SHORT).show()
                }
            }

        }

        binding.textviewForgotPassword.setOnClickListener{
            val intent = Intent(this, ResetPasswordActivity::class.java)
            startActivity(intent)
        }

    }

    private fun checkLogin() {
        if (myPref.isLogin()!!) {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}



