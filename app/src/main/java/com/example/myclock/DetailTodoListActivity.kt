package com.example.myclock

import android.content.ContentValues.TAG
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myclock.databinding.ActivityDetailTodoListBinding
import com.google.firebase.database.FirebaseDatabase

class DetailTodoListActivity : AppCompatActivity(), MyAdapter.MyClickListener {
    private lateinit var binding: ActivityDetailTodoListBinding
    private lateinit var myPref: SessionManager
    private lateinit var database: FirebaseDatabase
    private lateinit var newRecyclerview: RecyclerView
    private lateinit var newArrayList: ArrayList<multiDataClass>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailTodoListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        myPref = SessionManager(this)

        database = FirebaseDatabase.getInstance()
        var hourList = mutableListOf("")
        var minuteList = mutableListOf("")
        var taskList = mutableListOf("")
        var count = 0

        newRecyclerview = binding.recyclerview
        newRecyclerview.layoutManager = LinearLayoutManager(this)
        newRecyclerview.setHasFixedSize(true)
        newArrayList = arrayListOf<multiDataClass>()

        getData()

        binding.iconBack.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }

        //print todolist set before

    }

    private fun getData(){
        var tempList : MutableList<String> = mutableListOf("")
        var count: Int = 0
        database.reference.child("users").child(myPref.getUserName().toString())
            .child("tasks").child("task").get().addOnSuccessListener {
                Log.i("firebase", "Got value " + it.value.toString())
                Log.i("firebase", "Got value " + it.value.toString().length)
                var a: String = it.value.toString()
                if(it.value.toString() != "null"){
                    for(t in 0..(a.length - 1)){
                        if(a[t]==':'){
                            count++
                        }
                    }
                    Log.d("ABABABA", count.toString())
                    for(j in 0..(count-1)){
                        Log.d("ZZZZZ", a.splitToSequence(":").elementAt(j))
                        tempList.add(a.splitToSequence(":").elementAt(j))
                    }
                    tempList.removeAt(0)
                    Log.d("QQQQQQQQ", tempList.toString())
                    for (i in tempList.indices step 3){
                        val multiData = multiDataClass(tempList[i], tempList[i+1].toString(), tempList[i+2].toString())
                        newArrayList.add(multiData)
                    }
                    newRecyclerview.adapter = MyAdapter(newArrayList, this@DetailTodoListActivity)


                }
            }.addOnFailureListener{
                Log.e("firebase", "Error getting data", it)
            }
    }

    override fun onClick(position: Int) {
        Log.d(TAG, "Get url: " + newArrayList[position])
    }
}