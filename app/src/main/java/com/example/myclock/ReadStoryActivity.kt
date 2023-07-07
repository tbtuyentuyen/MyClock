package com.example.myclock

import android.content.ContentValues
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.util.Log
import android.util.TypedValue
import androidx.annotation.RequiresApi
import com.example.myclock.databinding.ActivityReadStoryBinding
import com.example.myclock.databinding.ActivityTellStoriesBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class ReadStoryActivity : AppCompatActivity() {
    private lateinit var binding: ActivityReadStoryBinding
    private lateinit var myPref: SessionManager
    private lateinit var database: FirebaseDatabase

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityReadStoryBinding.inflate(layoutInflater)
        setContentView(binding.root)
        myPref = SessionManager(this)

        // Initialize Firebase Database
        database = FirebaseDatabase.getInstance()
        var story = myPref.getStory()
        var dbStory: String
        binding.tvStoryName.setAutoSizeTextTypeUniformWithConfiguration(
            5, 20, 1, TypedValue.COMPLEX_UNIT_DIP)

        database.reference.child("stories")
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    // This method is called once with the initial value and again
                    // whenever data at this location is updated.
                    for (i in dataSnapshot.children) {
                        dbStory = i.key.toString()
                        if (dbStory == story) {
                            Log.d("ID of dbID", "${i.value}")
                            binding.tvStoryName.text = i.key
                            binding.tvReadStory.movementMethod = ScrollingMovementMethod()
                            binding.tvReadStory.text = i.value.toString()
                        }
                    }
                }
                override fun onCancelled(error: DatabaseError) {
                    // Failed to read value
                    Log.w(ContentValues.TAG, "Failed to read value.", error.toException())
                }
            })

        binding.iconBack.setOnClickListener {
            val intent = Intent(this, TellStoriesActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}