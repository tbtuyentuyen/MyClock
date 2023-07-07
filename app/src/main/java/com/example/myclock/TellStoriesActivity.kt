package com.example.myclock

import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.myclock.databinding.ActivityTellStoriesBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener


class TellStoriesActivity : AppCompatActivity() {
    private lateinit var binding: ActivityTellStoriesBinding
    private lateinit var myPref: SessionManager
    private lateinit var database: FirebaseDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTellStoriesBinding.inflate(layoutInflater)
        setContentView(binding.root)
        myPref = SessionManager(this)

        // Initialize Firebase Database
        database = FirebaseDatabase.getInstance()
        var arrayAdapter: ArrayAdapter<*>
        var stories = mutableListOf("")

        var count = 0
        val intentDetail = Intent(this, ReadStoryActivity::class.java)
        val databaseRef = database.reference.child("stories")
        val eventListener: ValueEventListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                for (ds in dataSnapshot.children) {
                    val key = ds.key
                    stories.add(count, key.toString())
                    count++
                }
                for(t in stories.indices){
                    Log.d("Firebase", stories[t])
                }


                var mListView = findViewById<ListView>(R.id.listView)
                arrayAdapter = ArrayAdapter(applicationContext,
                    android.R.layout.simple_list_item_1, stories)
                mListView.adapter = arrayAdapter
                mListView.onItemClickListener =
                    AdapterView.OnItemClickListener { _, _, position, _ ->
                        val storySelected = storyClass(stories[position])
                        val databaseRef = myPref.getUserName()
                            ?.let { it1 -> database.reference.child("users").child(it1).child("story") }
                        databaseRef?.setValue(storySelected)?.addOnSuccessListener {
                            Toast.makeText(applicationContext,
                                "Choose " + stories[position],
                                Toast.LENGTH_SHORT).show()
                            myPref.setStory(stories[position])
                            startActivity(intentDetail)

                        }?.addOnFailureListener {
                            Toast.makeText(applicationContext, "Failed!", Toast.LENGTH_SHORT).show()
                        }

                    }
            }

            override fun onCancelled(databaseError: DatabaseError) {}
        }
        databaseRef.addListenerForSingleValueEvent(eventListener)



        binding.iconBack.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}