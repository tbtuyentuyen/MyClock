package com.example.myclock

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.core.widget.doOnTextChanged
import androidx.recyclerview.widget.RecyclerView
import com.example.myclock.databinding.ActivityModifyTaskBinding
import com.google.firebase.database.FirebaseDatabase

class ModifyTaskActivity : AppCompatActivity() {
    private lateinit var binding: ActivityModifyTaskBinding
    private lateinit var myPref: SessionManager
    private lateinit var database: FirebaseDatabase
    private lateinit var newRecyclerview: RecyclerView
    private lateinit var newArrayList: ArrayList<multiDataClass>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityModifyTaskBinding.inflate(layoutInflater)
        setContentView(binding.root)
        myPref = SessionManager(this)

//        database = FirebaseDatabase.getInstance()
//        var tvTaskNew = binding.tvTaskNew
//        var tvHourNew = binding.tvHourNew
//        var tvMinuteNew = binding.tvMinuteNew
//        var listTask = mutableListOf("")
//
//        tvTaskNew.editText?.doOnTextChanged { _, _, _, _ ->
//            tvTaskNew.error = null
//        }
//        tvHourNew.editText?.doOnTextChanged { _, _, _, _ ->
//            tvHourNew.error = null
//        }
//        tvMinuteNew.editText?.doOnTextChanged { _, _, _, _ ->
//            tvMinuteNew.error = null
//        }
//
//        var hour = ""
//        var minute = ""
//        var task = ""
//        var i = 0
//
//        binding.btSave.setOnClickListener{
//            task = tvTaskNew.editText?.text.toString().trim()
//            hour = tvHourNew.editText?.text.toString().trim()
//            minute = tvMinuteNew.editText?.text.toString().trim()
//            if (task.isEmpty()) {
//                tvTaskNew.error = "Please enter task"
//                return@setOnClickListener
//            }
//            if (hour.isEmpty()) {
//                tvHourNew.error = "Invalid!"
//                return@setOnClickListener
//            }
//            if (minute.isEmpty()) {
//                tvMinuteNew.error = "Invalid!"
//                return@setOnClickListener
//            }
//            if (hour.toInt() > 23) {
//                tvHourNew.error = "Wrong!"
//                return@setOnClickListener
//            }
//            if (minute.toInt() > 59) {
//                tvMinuteNew.error = "Wrong!"
//                return@setOnClickListener
//            }
//            listTask.add(i, hour)
//            i++
//            listTask.add(i, minute)
//            i++
//            listTask.add(i, task)
//            i++
//
//            var tempList : MutableList<String> = mutableListOf("")
//            var count = 0
//            database.reference.child("users").child(myPref.getUserName().toString())
//                .child("tasks").child("task").get().addOnSuccessListener {
//                    Log.i("firebase", "Got value " + it.value.toString())
//                    var taskString: String = it.value.toString()
//                    if(it.value.toString() != "null"){
//                        for(t in 0..(taskString.length - 1)){
//                            if(taskString[t]==':'){
//                                count++
//                            }
//                        }
//                        Log.d("ABABABA", count.toString())
//                        for(j in 0..(count-1)){
//                            Log.d("ZZZZZ", taskString.splitToSequence(":").elementAt(j))
//                            tempList.add(taskString.splitToSequence(":").elementAt(j))
//                        }
//                        tempList.removeAt(0)
//                        Log.d("QQQQQQQQ", tempList.toString())
//                        for (i in tempList.indices step 3){
//                            val multiData = multiDataClass(tempList[i], tempList[i+1].toString(), tempList[i+2].toString())
//                            newArrayList.add(multiData)
//                        }
//                        newRecyclerview.adapter = MyAdapter(newArrayList, this@ModifyTaskActivity)
//
//                    }
//                }.addOnFailureListener{
//                    Log.e("firebase", "Error getting data", it)
//                }
//
//        }
//
//        binding.btDetail.setOnClickListener {
//            val intent = Intent(this, DetailTodoListActivity::class.java)
//            startActivity(intent)
//            finish()
//        }
//
//        binding.iconBack.setOnClickListener {
//            val intent = Intent(this, MainActivity::class.java)
//            startActivity(intent)
//            finish()
//        }
    }
}
