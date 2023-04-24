package com.example.workout

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.workout.databinding.ItemExerciseInfoBinding

class InfoAdapter(private val exerciseList: ArrayList<ExerciseModel>):
    RecyclerView.Adapter<InfoAdapter.InfoViewHolder>() {


    class InfoViewHolder(binding: ItemExerciseInfoBinding):
        RecyclerView.ViewHolder(binding.root) {
        // these variables handle assignment of individual items/rows
        val number = binding.number
        val exerciseName = binding.exerciseName
        val duration = binding.duration
        val restPeriod = binding.restDuration
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InfoViewHolder {
        return InfoViewHolder(ItemExerciseInfoBinding.inflate(
            LayoutInflater.from(parent.context), parent,false))
    }

    override fun onBindViewHolder(holder: InfoViewHolder, position: Int) {
        val exerciseModel: ExerciseModel = exerciseList[position]
        holder.number.text = exerciseModel.getId().toString()
        holder.exerciseName.text = exerciseModel.getName()
        holder.duration.text = (exerciseModel.getDuration() / 1000).toString()
        holder.restPeriod.text = (exerciseModel.getRestDuration() / 1000).toString()
        if(position % 2 == 0) {
            holder.itemView.setBackgroundColor(
                ContextCompat.getColor(holder.itemView.context, R.color.white))
        } else {
            holder.itemView.setBackgroundColor(
                ContextCompat.getColor(holder.itemView.context, R.color.bgInfoItem))
        }

    }

    override fun getItemCount(): Int {
        return exerciseList.size
    }
}