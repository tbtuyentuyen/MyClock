package com.example.myclock

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.widget.doOnTextChanged
import com.example.myclock.databinding.ActivitySetAlarmBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase

class SetAlarmActivity : AppCompatActivity() {
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var binding: ActivitySetAlarmBinding
    private lateinit var myPref: SessionManager
    private lateinit var database: FirebaseDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySetAlarmBinding.inflate(layoutInflater)
        setContentView(binding.root)
        myPref = SessionManager(this)

        firebaseAuth = Firebase.auth
        database = FirebaseDatabase.getInstance()
        var tvHour1 = binding.tvHour1
        var tvMinute1 = binding.tvMinute1

        tvHour1.editText?.doOnTextChanged { _, _, _, _ ->
            tvHour1.error = null
        }
        tvMinute1.editText?.doOnTextChanged { _, _, _, _ ->
            tvMinute1.error = null
        }

        binding.btSave.setOnClickListener {
            val hour1 = tvHour1.editText?.text.toString().trim()
            val minute1 = tvMinute1.editText?.text.toString().trim()

            // Set error when empty input email, password
            if (hour1.isEmpty()) {
                tvHour1.error = "Invalid!"
                return@setOnClickListener
            }
            if (minute1.isEmpty()) {
                tvMinute1.error = "Invalid!"
                return@setOnClickListener
            }

            tvHour1.editText?.setText(hour1)
            tvMinute1.editText?.setText(minute1)

            val databaseRef = database.reference.child("time").child(firebaseAuth.currentUser!!.uid)
            val time = timeClass(hour1, minute1)
            databaseRef.setValue(time).addOnSuccessListener {
                Toast.makeText(this,"Success!",Toast.LENGTH_SHORT).show()

            }.addOnFailureListener{

                Toast.makeText(this,"Failed!",Toast.LENGTH_SHORT).show()

            }

            Toast.makeText(this,
                "Save",
                Toast.LENGTH_SHORT).show()
        }
    }
}