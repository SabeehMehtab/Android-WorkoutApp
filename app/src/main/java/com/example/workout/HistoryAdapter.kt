package com.example.workout

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.workout.databinding.ItemHistoryDatabaseBinding
import java.util.*
import kotlin.collections.ArrayList
import kotlin.concurrent.schedule

class HistoryAdapter(
    private val datesList: ArrayList<HistoryEntity>,
    private val deleteRecord: (record: HistoryEntity) -> Unit) :
    RecyclerView.Adapter<HistoryAdapter.HistoryHolder>() {


    class HistoryHolder(binding: ItemHistoryDatabaseBinding)
        :RecyclerView.ViewHolder(binding.root) {
        val ll = binding.ll
        val number = binding.number
        val date = binding.date
        val delete = binding.delete
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryHolder {
        return HistoryHolder(ItemHistoryDatabaseBinding.inflate(
            LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: HistoryHolder, position: Int) {
        holder.number.text = (position+1).toString()
        holder.date.text = datesList[position].date
        if (position % 2 == 0) {
            holder.ll.setBackgroundColor(
                ContextCompat.getColor(holder.itemView.context,R.color.lightgray)
            )
        } else {
            holder.ll.setBackgroundColor(
                ContextCompat.getColor(holder.itemView.context,R.color.white)
            )
        }

        holder.delete.setOnClickListener {
            deleteRecord.invoke(datesList[position])
        }
    }

    override fun getItemCount(): Int {
        return datesList.size
    }


}