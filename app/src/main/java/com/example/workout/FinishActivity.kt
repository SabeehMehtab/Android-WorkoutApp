package com.example.workout

import android.graphics.drawable.TransitionDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.lifecycleScope
import com.example.workout.databinding.ActivityFinishBinding
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class FinishActivity : AppCompatActivity() {
    private var binding: ActivityFinishBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFinishBinding.inflate(layoutInflater)
        setContentView(binding?.root)
        setSupportActionBar(binding?.finishToolbar)
        if(supportActionBar != null) {
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
        }
        binding?.finishToolbar?.setNavigationOnClickListener {
            onBackPressed()
        }

        val img = binding?.finishImage?.drawable
        if (img is TransitionDrawable) {
            img.isCrossFadeEnabled = false
            img.startTransition(2000)
        }
        binding?.btnFinish?.setOnClickListener {
            finish()
        }

        val dao = (application as WorkoutApp).db.historyDao()
        addWorkoutToDatabase(dao)
    }

    private fun addWorkoutToDatabase(historyDao: HistoryDao) {
        val currentDate = SimpleDateFormat("dd MMM yyyy hh:mm",
            Locale.getDefault())
            .format(Calendar.getInstance().time)

        lifecycleScope.launch{
            historyDao.insert(HistoryEntity(currentDate))
            Log.e("Date Added : ","" + currentDate)
        }
    }
}