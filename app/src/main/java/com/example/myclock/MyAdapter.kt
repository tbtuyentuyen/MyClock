package com.example.myclock

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder

class MyAdapter(private val newsList: ArrayList<multiDataClass>):
    RecyclerView.Adapter<MyAdapter.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.list_item, parent, false)
        return MyViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return newsList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = newsList[position]
        holder.itemHour.text = currentItem.hour
        holder.itemMinute.text = currentItem.minute
        holder.itemDetailTask.text = currentItem.task
    }


    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val itemHour: TextView = itemView.findViewById(R.id.tvHourTask)
        val itemMinute: TextView = itemView.findViewById(R.id.tvMinuteTask)
        val itemDetailTask: TextView = itemView.findViewById(R.id.tvDetailTask)
    }

}