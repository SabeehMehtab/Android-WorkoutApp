package com.example.workout

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.workout.databinding.ActivityHistoryBinding
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class HistoryActivity : AppCompatActivity() {
    private var binding: ActivityHistoryBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHistoryBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        setSupportActionBar(binding?.toolbarHistory)
        val actionbar = supportActionBar//actionbar
        if (actionbar != null) {
            actionbar.setDisplayHomeAsUpEnabled(true) //set back button
            actionbar.title = "HISTORY" // Setting a title in the action bar.
            actionbar.setHomeButtonEnabled(true)
        }
        binding?.toolbarHistory?.setNavigationOnClickListener {
            onBackPressed()
        }

        val dao = (application as WorkoutApp).db.historyDao()
        showCompletedWorkoutDates(dao)
    }

    private fun showCompletedWorkoutDates(historyDao: HistoryDao) {
        lifecycleScope.launch {
            historyDao.fetchAllDates().collect { datesList ->
                if(datesList.isEmpty()){
                    binding?.tvNoDataAvailable?.visibility = View.VISIBLE
                    binding?.tvHistory?.visibility = View.GONE
                    binding?.rvHistory?.visibility = View.GONE
                } else {
                    binding?.tvNoDataAvailable?.visibility = View.GONE
                    binding?.tvHistory?.visibility = View.VISIBLE
                    binding?.rvHistory?.visibility = View.VISIBLE

                    val adapter = HistoryAdapter(datesList as ArrayList<HistoryEntity>) {
                        record -> deleteRecord(historyDao,record)
                    }
                    binding?.rvHistory?.adapter = adapter
                    binding?.rvHistory?.layoutManager =
                        LinearLayoutManager(this@HistoryActivity)

                }

            }
        }
    }

    private fun deleteRecord(historyDao: HistoryDao,
                             record: HistoryEntity) {
        lifecycleScope.launch{
            historyDao.deleteWorkout(record)
            Toast.makeText(this@HistoryActivity,
                "Record deleted successfully",
                Toast.LENGTH_SHORT).show()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }

}