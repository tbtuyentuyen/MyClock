package com.example.myclock

import android.content.ContentValues.TAG
import android.util.Log
import com.example.myclock.databinding.ActivitySignInBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class checkUser (var id: String) {
    private lateinit var myPref: SessionManager
    private lateinit var database: FirebaseDatabase



}