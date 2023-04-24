package com.example.workout

import android.app.Dialog
import android.content.Intent
import android.content.res.ColorStateList
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.workout.databinding.ActivityMainBinding
import com.example.workout.databinding.ExerciseInfoDialogBinding

class MainActivity : AppCompatActivity() {
    private var binding: ActivityMainBinding? = null
    private var infoDialogBinding: ExerciseInfoDialogBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding?.root)
        binding?.flStart?.setOnClickListener {
            val intent = Intent(this, ExerciseActivity::class.java)
            intent.putExtra(Constants.TESTING_MODE,binding?.switchNormal?.isChecked)
            startActivity(intent)
        }

        binding?.flBMI?.setOnClickListener {
            val intent = Intent(this, BMIActivity::class.java)
            startActivity(intent)
        }

        binding?.imgExerciseInfo?.setOnClickListener {
            openWorkoutInfoDialog()
        }

        binding?.imgHistory?.setOnClickListener {
            val intent = Intent(this,HistoryActivity::class.java)
            startActivity(intent)
        }

        val colorList = ColorStateList(
            arrayOf(
                intArrayOf(android.R.attr.state_checked),
                intArrayOf(-android.R.attr.state_checked)),
            intArrayOf(
                ContextCompat.getColor(this@MainActivity,
                        R.color.purple_200),
                ContextCompat.getColor(this@MainActivity,
                        R.color.bettergray)))

        binding?.switchNormal?.setOnClickListener {
            if (binding?.switchNormal?.isChecked == true) {
                binding?.switchNormal?.isChecked = true
                binding?.switchNormal?.text = "Testing Mode"
                binding?.switchNormal?.trackTintList = colorList
            } else {
                binding?.switchNormal?.isChecked = false
                binding?.switchNormal?.text = "Normal Mode"
            }
        }
    }

    private fun openWorkoutInfoDialog() {
        val dialog = Dialog(this@MainActivity)
        val infoAdapter = InfoAdapter(Constants.defaultExerciseList())
        infoDialogBinding = ExerciseInfoDialogBinding.inflate(layoutInflater)
        infoDialogBinding!!.rvInfoData.adapter = infoAdapter
        infoDialogBinding!!.rvInfoData.layoutManager = LinearLayoutManager(this)
        dialog.setContentView(infoDialogBinding!!.root)
        dialog.setCancelable(true)
        dialog.window?.attributes?.width = ViewGroup.LayoutParams.MATCH_PARENT
        dialog.setTitle("Workout Information")
        dialog.show()
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
        infoDialogBinding = null
    }
}