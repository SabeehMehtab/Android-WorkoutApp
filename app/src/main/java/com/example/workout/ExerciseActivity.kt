package com.example.workout

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.media.MediaPlayer
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.speech.tts.TextToSpeech
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.workout.databinding.ActivityExerciseBinding
import java.lang.Exception
import java.util.*
import kotlin.collections.ArrayList

class ExerciseActivity : AppCompatActivity(), TextToSpeech.OnInitListener {
    private var binding: ActivityExerciseBinding? = null
    private var restTimer: CountDownTimer? = null
    private var restDurationLeft: Long = 5000
    private var restProgress = 0
    private var exerciseTimer: CountDownTimer? = null
    private var exerciseDuration: Long = 30000
    private var exerciseProgress = 0
    private var currentExerciseId = -1
    private var exerciseList : ArrayList<ExerciseModel>? = null
    private var exerciseAdapter: ExerciseStatusAdapter? = null
    private var tts: TextToSpeech? = null
    private var player: MediaPlayer? = null
    private var exerciseMode: Boolean = false
    private var testingMode: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityExerciseBinding.inflate(layoutInflater)
        setContentView(binding?.root)

//      Setting up Navigation Bar as Action Bar
//      and adding the back arrow with its functionality
        setSupportActionBar(binding?.exerciseToolbar)
        if(supportActionBar != null) {
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
        }
        binding?.exerciseToolbar?.setNavigationOnClickListener {
            onBackPressed()
        }

        testingMode = intent.getBooleanExtra(Constants.TESTING_MODE,false)
        tts = TextToSpeech(this, this)
        exerciseList = Constants.defaultExerciseList()
        setupRestView()
        setupExerciseStatusRV()
    }

    override fun onBackPressed() {
        exerciseTimer?.cancel()
        restTimer?.cancel()
        tts?.stop()

        val builder = AlertDialog.Builder(this, R.style.Alert)
        builder.setTitle(R.string.alert_title)
            .setMessage(R.string.alert_message)
            .setPositiveButton(
                R.string.resume, DialogInterface.OnClickListener { dialogInterface, id ->
                    // Checking whether in exercise view or rest view
                    if (exerciseMode) {
                        exerciseDuration -= 1000
                        setExerciseProgressBar()
                    } else {
                        restDurationLeft -= 1000
                        setRestProgressBar()
                    }
                })
            .setNegativeButton(
                R.string.quit, DialogInterface.OnClickListener { dialogInterface, id ->
                    finish()
            })
            .setCancelable(false)
        val alertDialog = builder.create()
        alertDialog.show()
    }

    private fun setupExerciseStatusRV(){
        binding?.rvExerciseStatus?.layoutManager = LinearLayoutManager(
            this, LinearLayoutManager.HORIZONTAL, false)
        exerciseAdapter = ExerciseStatusAdapter(exerciseList!!)
        binding?.rvExerciseStatus?.adapter = exerciseAdapter
    }

    private fun setupRestView() {
        binding?.tvExerciseName?.visibility = View.GONE
        binding?.exerciseImage?.visibility = View.GONE
        binding?.flExerciseView?.visibility = View.GONE
        binding?.flRestView?.visibility = View.VISIBLE
        binding?.tvRestTitle?.visibility = View.VISIBLE
        binding?.tvUpcoming?.visibility = View.VISIBLE
        binding?.tvUpcomingExercise?.visibility = View.VISIBLE

        when (currentExerciseId) {
            -1 -> {
                binding?.tvRestTitle?.setText(R.string.ready)
                binding?.tvUpcoming?.text = "FIRST EXERCISE:"
            }
            exerciseList!!.size-2 -> {
                restDurationLeft = exerciseList!![currentExerciseId]
                    .getRestDuration()
                speakOut("Take"+(restDurationLeft/1000)+" second rest")
                binding?.tvRestTitle?.setText(R.string.rest)
                binding?.tvUpcoming?.text = "LAST EXERCISE:"
            }
            else -> {
                restDurationLeft = exerciseList!![currentExerciseId]
                    .getRestDuration()
                speakOut("Take"+(restDurationLeft/1000)+" second rest")
                binding?.tvRestTitle?.setText(R.string.rest)
                binding?.tvUpcoming?.text = "NEXT EXERCISE:"
            }
        }

        if (testingMode) restDurationLeft = 2000

        if(restTimer != null) {
            restTimer?.cancel()
            restProgress = 0
        }

        binding?.tvUpcomingExercise?.text = exerciseList!![currentExerciseId+1].getName()
        binding?.progressBar?.max = (restDurationLeft/1000).toInt()
        setRestProgressBar()
    }

    private fun setRestProgressBar(){
        exerciseMode = false
        binding?.progressBar?.progress = restProgress
        restTimer = object: CountDownTimer(restDurationLeft,1000) {
            override fun onTick(timeLeft: Long) {
                restDurationLeft = timeLeft
                restProgress++
                binding?.progressBar?.progress = (timeLeft/1000).toInt()
                binding?.timer?.text = (timeLeft/1000).toString()
            }
            override fun onFinish() {
                currentExerciseId++
                exerciseList!![currentExerciseId].setIsSelected(true)
                exerciseAdapter!!.notifyDataSetChanged()
                setupExerciseView()
            }
        }.start()
    }

    private fun setupExerciseView() {
        binding?.tvRestTitle?.visibility = View.GONE
        binding?.flRestView?.visibility = View.GONE
        binding?.tvUpcoming?.visibility = View.GONE
        binding?.tvUpcomingExercise?.visibility = View.GONE
        binding?.tvExerciseName?.visibility = View.VISIBLE
        binding?.exerciseImage?.visibility = View.VISIBLE
        binding?.flExerciseView?.visibility = View.VISIBLE

        /*
        The below commented code shows how to access
        constraint layout params in the xml file
        val params = binding?.tvTitle?.layoutParams as ConstraintLayout.LayoutParams
        params.bottomToTop = binding?.flExerciseView?.id!!
         */

        if(exerciseTimer != null) {
            exerciseTimer?.cancel()
            exerciseProgress = 0
        }

        speakOut(exerciseList!![currentExerciseId].getName())
        binding?.exerciseImage?.setImageResource(
            exerciseList!![currentExerciseId].getImage())
        binding?.tvExerciseName?.text = exerciseList!![currentExerciseId].getName()
        exerciseDuration = exerciseList!![currentExerciseId].getDuration()
        binding?.progressBarExercise?.max = (exerciseDuration/1000).toInt()

        if(testingMode) exerciseDuration = 2000

        setExerciseProgressBar()
    }

    private fun setExerciseProgressBar(){
        exerciseMode = true
        binding?.progressBarExercise?.progress = exerciseProgress
        exerciseTimer = object: CountDownTimer(exerciseDuration,1000) {
            override fun onTick(timeLeft: Long) {
                exerciseDuration = timeLeft
                exerciseProgress++
                binding?.progressBarExercise?.progress = (timeLeft/1000).toInt()
                binding?.timerExercise?.text = (timeLeft/1000).toString()
            }
            override fun onFinish() {
                try {
                    val soundURI = Uri.parse(
                        "android.resource://com.example.workout/" + R.raw.press_start)
                    player = MediaPlayer.create(applicationContext,soundURI)
                    player?.isLooping = false
                    player?.start()
                } catch (e: Exception) {
                    e.printStackTrace()
                }
                exerciseList!![currentExerciseId].setIsSelected(false)
                exerciseList!![currentExerciseId].setIsCompleted(true)
                exerciseAdapter!!.notifyDataSetChanged()
                if(currentExerciseId < (exerciseList!!.size-1)) {
                    setupRestView()
                }else {
                    finish()
                    val intent = Intent(this@ExerciseActivity, FinishActivity::class.java)
                    startActivity(intent)
                }
            }
        }.start()
    }

    private fun speakOut(text: String) {
        if (tts != null) {
            tts!!.speak(text, TextToSpeech.QUEUE_ADD, null, "")
        }
    }

    override fun onInit(status: Int) {
        if (status == TextToSpeech.SUCCESS) {
            // set US English as language for tts
            val result = tts?.setLanguage(Locale.US)
            // checking for any issues with language
            if (result == TextToSpeech.LANG_MISSING_DATA ||
                result == TextToSpeech.LANG_NOT_SUPPORTED) {
                Log.e("TTS", "The Language specified is not supported")
            }
            speakOut("Get ready for the first exercise")
        } else Log.e("TTS", "Initialization Failed!!!")
    }

    override fun onDestroy() {
        super.onDestroy()
        if(restTimer != null) {
            restTimer?.cancel()
            restProgress = 0
        }

        if(exerciseTimer != null) {
            exerciseTimer?.cancel()
            exerciseProgress = 0
        }

        if (tts != null) {
            tts!!.stop()
            tts!!.shutdown()
        }

        if(player != null){
            player!!.stop()
        }

        binding = null
    }

}