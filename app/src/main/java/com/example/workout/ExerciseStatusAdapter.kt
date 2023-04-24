package com.example.workout

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.workout.databinding.ItemlayoutExerciseStatusBinding

class ExerciseStatusAdapter(val items: ArrayList<ExerciseModel>):
    RecyclerView.Adapter<ExerciseStatusAdapter.ViewHolder>() {

    inner class ViewHolder(binding: ItemlayoutExerciseStatusBinding):
                RecyclerView.ViewHolder(binding.root) {
                    val individualItem = binding.tvItem
                }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ItemlayoutExerciseStatusBinding.inflate(
            LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val model: ExerciseModel = items[position]
        holder.individualItem.text = model.getId().toString()
        when {
            model.getIsSelected() -> {
                holder.individualItem.background =
                    ContextCompat.getDrawable(holder.itemView.context,
                        R.drawable.recyclerview_item_selected_bg)
            }
            model.getIsCompleted() -> {
                holder.individualItem.background =
                    ContextCompat.getDrawable(holder.itemView.context,
                    R.drawable.progressbar_circular_background)
//                holder.individualItem.setTextColor(Color.WHITE)
            }
            else -> {
                holder.individualItem.background =
                    ContextCompat.getDrawable(holder.itemView.context,
                        R.drawable.recyclerview_item_bg)
            }
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }
}