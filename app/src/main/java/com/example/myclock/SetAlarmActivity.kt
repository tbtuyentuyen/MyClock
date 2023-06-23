package com.example.myclock

import android.content.ContentValues.TAG
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.core.widget.doOnTextChanged
import com.example.myclock.databinding.ActivitySetAlarmBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase

class SetAlarmActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySetAlarmBinding
    private lateinit var myPref: SessionManager
    private lateinit var database: FirebaseDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySetAlarmBinding.inflate(layoutInflater)
        setContentView(binding.root)
        myPref = SessionManager(this)

        database = FirebaseDatabase.getInstance()
        var tvHour1 = binding.tvHour1
        var tvMinute1 = binding.tvMinute1
        var tvHour2 = binding.tvHour2
        var tvMinute2 = binding.tvMinute2
        var tvHour3 = binding.tvHour3
        var tvMinute3 = binding.tvMinute3

        tvHour1.editText?.doOnTextChanged { _, _, _, _ ->
            tvHour1.error = null
        }
        tvMinute1.editText?.doOnTextChanged { _, _, _, _ ->
            tvMinute1.error = null
        }
        tvHour2.editText?.doOnTextChanged { _, _, _, _ ->
            tvHour2.error = null
        }
        tvMinute2.editText?.doOnTextChanged { _, _, _, _ ->
            tvMinute2.error = null
        }
        tvHour3.editText?.doOnTextChanged { _, _, _, _ ->
            tvHour3.error = null
        }
        tvMinute3.editText?.doOnTextChanged { _, _, _, _ ->
            tvMinute3.error = null
        }

        var hour1 = ""
        var minute1 = ""
        var hour2 = ""
        var minute2 = ""
        var hour3 = ""
        var minute3 = ""

        binding.btSave.setOnClickListener {

            for(i in 0..1) {
                val databaseRef = myPref.getUserName()
                    ?.let { it1 -> database.reference.child("users").child(it1).child("time") }

                //Alarm 1
                if (binding.checkBoxTime1.isChecked) {
                    hour1 = tvHour1.editText?.text.toString().trim()
                    minute1 = tvMinute1.editText?.text.toString().trim()
                    val time = timeClass(hour1, minute1, hour2, minute2, hour3, minute3)
                    //don't fill hour and minute
                    if (hour1.isEmpty()) {
                        tvHour1.error = "Invalid!"
                        return@setOnClickListener
                    }
                    if (minute1.isEmpty()) {
                        tvMinute1.error = "Invalid!"
                        return@setOnClickListener
                    }
                    if (hour1.toInt() > 24) {
                        tvHour1.error = "Wrong!"
                        return@setOnClickListener
                    }
                    if (minute1.toInt() > 59) {
                        tvMinute1.error = "Wrong!"
                        return@setOnClickListener
                    }

                    tvHour1.editText?.setText(hour1)
                    tvMinute1.editText?.setText(minute1)

                    databaseRef?.setValue(time)?.addOnSuccessListener {
                        Toast.makeText(this, "Set Alarm 1!", Toast.LENGTH_SHORT).show()
                    }?.addOnFailureListener {
                        Toast.makeText(this, "Failed!", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    hour1 = ""
                    minute1 = ""
                }

                Log.d(TAG, " 1=========== $hour1 $minute1")

                //Alarm 2
                if (binding.checkBoxTime2.isChecked) {
                    hour2 = tvHour2.editText?.text.toString().trim()
                    minute2 = tvMinute2.editText?.text.toString().trim()
                    val time = timeClass(hour1, minute1, hour2, minute2, hour3, minute3)
                    //don't fill hour and minute
                    if (hour2.isEmpty()) {
                        tvHour2.error = "Invalid!"
                        return@setOnClickListener
                    }
                    if (minute2.isEmpty()) {
                        tvMinute2.error = "Invalid!"
                        return@setOnClickListener
                    }
                    if (hour2.toInt() > 24) {
                        tvHour2.error = "Wrong!"
                        return@setOnClickListener
                    }
                    if (minute2.toInt() > 59) {
                        tvMinute2.error = "Wrong!"
                        return@setOnClickListener
                    }

                    tvHour2.editText?.setText(hour2)
                    tvMinute2.editText?.setText(minute2)

                    databaseRef?.setValue(time)?.addOnSuccessListener {
                        Toast.makeText(this, "Set Alarm 2!", Toast.LENGTH_SHORT).show()
                    }?.addOnFailureListener {
                        Toast.makeText(this, "Failed!", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    hour2 = ""
                    minute2 = ""
                }

                Log.d(TAG, " 2=========== $hour2 $minute2")

                //Alarm 3
                if (binding.checkBoxTime3.isChecked) {
                    hour3 = tvHour3.editText?.text.toString().trim()
                    minute3 = tvMinute3.editText?.text.toString().trim()
                    val time = timeClass(hour1, minute1, hour2, minute2, hour3, minute3)
                    //don't fill hour and minute
                    if (hour3.isEmpty()) {
                        tvHour3.error = "Invalid!"
                        return@setOnClickListener
                    }
                    if (minute3.isEmpty()) {
                        tvMinute3.error = "Invalid!"
                        return@setOnClickListener
                    }
                    if (hour3.toInt() > 24) {
                        tvHour3.error = "Wrong!"
                        return@setOnClickListener
                    }
                    if (minute3.toInt() > 59) {
                        tvMinute3.error = "Wrong!"
                        return@setOnClickListener
                    }

                    tvHour3.editText?.setText(hour3)
                    tvMinute3.editText?.setText(minute3)

                    databaseRef?.setValue(time)?.addOnSuccessListener {
                        Toast.makeText(this, "Set Alarm 3!", Toast.LENGTH_SHORT).show()
                    }?.addOnFailureListener {
                        Toast.makeText(this, "Failed!", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    hour3 = ""
                    minute3 = ""
                }
                Log.d(TAG, " 3=========== $hour3 $minute3")
            }
        }

        binding.iconBack.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

}