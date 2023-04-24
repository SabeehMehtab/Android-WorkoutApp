package com.example.workout

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.AttributeSet
import android.view.View
import android.widget.RelativeLayout
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.view.updateLayoutParams
import com.example.workout.databinding.ActivityBmiBinding
import com.google.android.material.snackbar.Snackbar
import java.math.BigDecimal
import java.math.RoundingMode

class BMIActivity : AppCompatActivity() {
    private var binding: ActivityBmiBinding? = null
    private var paramsBtnCalculate: RelativeLayout.LayoutParams? = null
    private var paramsResultView: RelativeLayout.LayoutParams? = null
    private var inMetricView: Boolean  = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBmiBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        setSupportActionBar(binding?.toolbarBmiActivity)
        if(supportActionBar != null) {
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
        }
        binding?.toolbarBmiActivity?.setNavigationOnClickListener {
            onBackPressed()
        }

        paramsBtnCalculate = binding?.btnCalculate?.layoutParams as RelativeLayout.LayoutParams
        paramsResultView = binding?.llDisplayBMIResult?.layoutParams as RelativeLayout.LayoutParams

        binding?.btnCalculate?.setOnClickListener {
            if (inMetricView && validateMetricValues() ) {
                val height = binding?.etMetricUnitHeight?.text.toString().toFloat() / 100
                val weight = binding?.etWeight?.text.toString().toFloat()
                val bmi = weight/(height*height)
                displayBMIResultView(bmi)
            }
            else if (!inMetricView && validateUsValues()) {
                val weight = binding?.etWeight?.text.toString().toFloat()
                val heightFeet = binding?.etUSUnitHeightFeet?.text.toString().toFloat()
                val heightInch = binding?.etUSUnitHeightInch?.text.toString().toFloat()
                val height = (heightFeet*12) + heightInch
                val bmi = 703 * (weight/(height*height))
                displayBMIResultView(bmi)
            }
            else Toast.makeText(
                this,"Please enter valid values",
                    Toast.LENGTH_SHORT).show()
        }

        binding?.rgUnits?.setOnCheckedChangeListener { _, btnID: Int ->
            if (btnID == R.id.rbUsUnits) {
                inMetricView = false
                setUsView()
            }
            else {
                inMetricView = true
                setMetricView()
            }
        }
    }

    private fun setMetricView() {
        binding?.tilWeight?.hint = "WEIGHT (in kg)"
        binding?.tilMetricUnitHeight?.visibility = View.VISIBLE
        binding?.tilUSUnitHeightFeet?.visibility = View.GONE
        binding?.tilUSUnitHeightInch?.visibility = View.GONE
        binding?.llDisplayBMIResult?.visibility = View.GONE

        paramsBtnCalculate?.addRule(RelativeLayout.BELOW, binding?.tilMetricUnitHeight?.id!!)
        paramsResultView?.addRule(RelativeLayout.BELOW, binding?.tilMetricUnitHeight?.id!!)

        binding?.etWeight?.text!!.clear()
        binding?.etMetricUnitHeight?.text!!.clear()
    }

    private fun setUsView() {
        binding?.tilWeight?.hint = "WEIGHT (in pounds)"
        binding?.tilMetricUnitHeight?.visibility = View.GONE
        binding?.tilUSUnitHeightFeet?.visibility = View.VISIBLE
        binding?.tilUSUnitHeightInch?.visibility = View.VISIBLE
        binding?.llDisplayBMIResult?.visibility = View.GONE

        paramsBtnCalculate?.addRule(RelativeLayout.BELOW, binding?.tilUSUnitHeightFeet?.id!!)
        paramsResultView?.addRule(RelativeLayout.BELOW, binding?.tilUSUnitHeightFeet?.id!!)

        binding?.etWeight?.text!!.clear()
        binding?.etUSUnitHeightFeet?.text!!.clear()
        binding?.etUSUnitHeightInch?.text!!.clear()
    }

    private fun validateMetricValues(): Boolean {
        var isValidEntry = false
        if (binding?.etWeight?.text.toString().isNotEmpty() &&
            binding?.etMetricUnitHeight?.text.toString().isNotEmpty()) {
                isValidEntry = true
        }

        return isValidEntry
    }

    private fun validateUsValues(): Boolean {
        var isValidEntry = false
        if (binding?.etWeight?.text.toString().isNotEmpty() &&
            binding?.etUSUnitHeightFeet?.text.toString().isNotEmpty() &&
            binding?.etUSUnitHeightInch?.text.toString().isNotEmpty()) {
            isValidEntry = true
        }
        return isValidEntry
    }

    private fun displayBMIResultView(bmi: Float) {
        lateinit var result: String
        lateinit var description: String
        val colorBMI: Int

        if (bmi.compareTo(15f) <= 0) {
            result = "Very severely underweight"
            description = "Oops! You really need to take better care of yourself! Eat more!"
            colorBMI = R.color.bmiVSUnderweight

        }
        else if (bmi.compareTo(15f) > 0 && bmi.compareTo(16f) <= 0) {
            result = "Severely underweight"
            description = "Oops! You really need to take better care of yourself! Eat more!"
            colorBMI = R.color.bmiSUnderweight

        }
        else if (bmi.compareTo(16f) > 0 && bmi.compareTo(18.5f) <= 0) {
            result = "Underweight"
            description = "Cmon! You are very close to having a good shape. Perhaps, eat a little more!"
            colorBMI = R.color.bmiUnderweight

        }
        else if (bmi.compareTo(18.5f) > 0 && bmi.compareTo(25f) <= 0 ) {
            result = "Normal"
            description = "Congratulations! You are in a good shape!"
            colorBMI = R.color.bmiNormal

        }
        else if (bmi.compareTo(25f) > 0 && bmi.compareTo(30f) <= 0) {
            result = "Overweight"
            description = "Cmon! You are very close to having a good shape. Perhaps, eat a little less!"
            colorBMI = R.color.bmiOverweight

        }
        else if (bmi.compareTo(30f) > 0 && bmi.compareTo(35f) <= 0 ) {
            result = "Obese Class | (Moderately obese)"
            description = "Oops! You really need to take care of your yourself! Workout maybe!"
            colorBMI = R.color.bmiObese1

        }
        else if (bmi.compareTo(35f) > 0 && bmi.compareTo(40f) <= 0 ) {
            result = "Obese Class || (Severely obese)"
            description = "OMG! You are in a very dangerous condition! Act now!"
            colorBMI = R.color.bmiObese2

        }
        else {
            result = "Obese Class ||| (Very Severely obese)"
            description = "OMG! You are in a very dangerous condition! Act now!"
            colorBMI = R.color.bmiObese3
        }



        binding?.llDisplayBMIResult?.visibility = View.VISIBLE
        binding?.tvBMIValue?.text = BigDecimal(bmi.toDouble()).setScale(2,RoundingMode.HALF_EVEN).toString()
        binding?.tvBMIType?.text = result
        binding?.tvBMIDescription?.text = description
        binding?.tvBMIValue?.setTextColor(Color.BLACK)
        binding?.tvBMIType?.setTextColor(ContextCompat.getColor(this, colorBMI))


        // After making the BMI result visible,
        // move the calculate button below it
        paramsBtnCalculate?.addRule(RelativeLayout.BELOW, binding?.llDisplayBMIResult?.id!!)
    }
}
