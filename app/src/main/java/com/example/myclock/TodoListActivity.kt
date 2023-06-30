package com.example.myclock

import android.content.ContentValues
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.util.Log
import android.widget.Toast
import androidx.core.widget.doOnTextChanged
import com.example.myclock.databinding.ActivityMainBinding
import com.example.myclock.databinding.ActivityTodoListBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

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

        //print todolist set before
        database.reference.child("users").child(myPref.getUserName().toString())
            .child("tasks").get().addOnSuccessListener {
                    Log.i("firebase", "Got value ${it.value}")
                    if(it.value.toString() != "null"){
                        binding.tvTodoList.editText?.setText(it.value.toString())
                    }
        }.addOnFailureListener{
            Log.e("firebase", "Error getting data", it)
        }

        // Reset state input when have change on it
        tvTodoList.editText?.doOnTextChanged { _, _, _, _ ->
            tvTodoList.error = null
        }

        binding.btSave.setOnClickListener{
            val tasks = tvTodoList.editText?.text.toString().trim()
            if (tasks.isEmpty()) {
                tvTodoList.error = "Please enter tasks"
                return@setOnClickListener
            }
            textClass(tasks)
            val databaseRef = myPref.getUserName()
                ?.let { it1 -> database.reference.child("users").child(it1).child("tasks") }
            databaseRef?.setValue(tasks)?.addOnSuccessListener {
                Toast.makeText(this, "Success!", Toast.LENGTH_SHORT).show()
            }?.addOnFailureListener {
                Toast.makeText(this, "Failed!", Toast.LENGTH_SHORT).show()
            }
        }

        binding.iconBack.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}