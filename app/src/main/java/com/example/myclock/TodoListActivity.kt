package com.example.myclock

import android.content.Intent
import android.graphics.Color
import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doOnTextChanged
import com.example.myclock.databinding.ActivityTodoListBinding
import com.google.firebase.database.FirebaseDatabase

class TodoListActivity : AppCompatActivity() {
    private lateinit var binding: ActivityTodoListBinding
    private lateinit var myPref: SessionManager
    private lateinit var database: FirebaseDatabase
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTodoListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        myPref = SessionManager(this)
        // Initialize Firebase Auth and Firebase Database
        database = FirebaseDatabase.getInstance()
        var tvTodoList = binding.tvTodoList
        var tvHour = binding.tvHour
        var tvMinute = binding.tvMinute
        var list = mutableListOf("")
        var timeTask : String

//

        // Reset state input when have change on it
        tvTodoList.editText?.doOnTextChanged { _, _, _, _ ->
            tvTodoList.error = null
        }
        tvHour.editText?.doOnTextChanged { _, _, _, _ ->
            tvHour.error = null
        }
        tvMinute.editText?.doOnTextChanged { _, _, _, _ ->
            tvMinute.error = null
        }

        var hour = ""
        var minute = ""
        var task = ""
        var i = 0


        binding.btSave.setOnClickListener{
            task = tvTodoList.editText?.text.toString().trim()
            hour = tvHour.editText?.text.toString().trim()
            minute = tvMinute.editText?.text.toString().trim()
            if (task.isEmpty()) {
                tvTodoList.error = "Please enter task"
                return@setOnClickListener
            }
            if (hour.isEmpty()) {
                tvHour.error = "Invalid!"
                return@setOnClickListener
            }
            if (minute.isEmpty()) {
                tvMinute.error = "Invalid!"
                return@setOnClickListener
            }
            if (hour.toInt() > 23) {
                tvHour.error = "Wrong!"
                return@setOnClickListener
            }
            if (minute.toInt() > 59) {
                tvMinute.error = "Wrong!"
                return@setOnClickListener
            }
            list.add(i, hour)
            i++
            list.add(i, minute)
            i++
            list.add(i, task)
            i++
            timeTask = list.joinToString(
//                prefix = "[",
                separator = ":",
//                postfix = "]",
                limit = 1000,
                truncated = "..."
            )
            Log.d("Time and Task", timeTask)

            var taskClass = textClass(timeTask, (0..1000000).random().toString())
            val databaseRef = myPref.getUserName()
                ?.let { it1 -> database.reference.child("users").child(it1).child("tasks") }
            databaseRef?.setValue(taskClass)?.addOnSuccessListener {
                Toast.makeText(this, "Success!", Toast.LENGTH_SHORT).show()
            }?.addOnFailureListener {
                Toast.makeText(this, "Failed!", Toast.LENGTH_SHORT).show()
            }

        }

        binding.btDetail.setOnClickListener {
            val intent = Intent(this, DetailTodoListActivity::class.java)
            startActivity(intent)
            finish()
        }

        binding.iconBack.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}