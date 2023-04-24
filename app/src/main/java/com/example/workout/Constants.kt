package com.example.workout

object Constants {
    const val TESTING_MODE = "testing"

    fun defaultExerciseList(): ArrayList<ExerciseModel> {
        val exerciseList = ArrayList<ExerciseModel>()
        // First Exercise Added
        val abdominalCrunch = ExerciseModel(
            1,"Abdominal Crunch",
            R.drawable.abdominal_crunch,
            25000, 15000) // 40 sec
        exerciseList.add(abdominalCrunch)

        // Second Exercise Added
        val highKneesRunning = ExerciseModel(
            2,"High Knees Running",
            R.drawable.high_knees_running_in_place,
            30000, 5000) // 35 sec
        exerciseList.add(highKneesRunning)

        // Third Exercise Added
        val jumpingJacks = ExerciseModel(
            3,"Jumping Jacks",
            R.drawable.jumping_jacks,
            27000, 8000) // 35 sec
        exerciseList.add(jumpingJacks)

        // 4th Exercise Added
        val lunge = ExerciseModel(
            4,"Lunge",
            R.drawable.lunge,
            26000, 6000) // 32 sec
        exerciseList.add(lunge)

        // 5th Exercise Added
        val plank = ExerciseModel(
            5,"Plank",
            R.drawable.plank,
            21000, 12000) // 33 sec
        exerciseList.add(plank)

        // 6th Exercise Added
        val pushUp = ExerciseModel(
            6,"Push Up",
            R.drawable.push_up,
            20000, 10000) // 30 sec
        exerciseList.add(pushUp)

        // 7th Exercise Added
        val pushUpRotation = ExerciseModel(
            7,"Push Up And Rotation",
            R.drawable.push_up_and_rotation,
            30000, 10000) // 40 sec
        exerciseList.add(pushUpRotation)

        // 8th Exercise Added
        val sidePlank = ExerciseModel(
            8,"Side Plank",
            R.drawable.side_plank,
            20000, 13000) // 33 sec
        exerciseList.add(sidePlank)

        // 9th Exercise Added
        val squat = ExerciseModel(
            9,"Squat",
            R.drawable.squat,
            25000, 8000) // 33 sec
        exerciseList.add(squat)

        // 10th Exercise Added
        val stepUpOnChair = ExerciseModel(
            10,"Step-Up Onto Chair",
            R.drawable.step_up_onto_chair,
            30000, 6000) // 36 sec
        exerciseList.add(stepUpOnChair)

        // 11th Exercise Added
        val tricepsDipOnChair = ExerciseModel(
            11,"Triceps Dip Onto Chair",
            R.drawable.triceps_dip_on_chair,
            24000, 7000)
        exerciseList.add(tricepsDipOnChair) // 31 sec

        // 12th(Last) Exercise Added
        val wallSit = ExerciseModel(
            12,"Wall Sit",
            R.drawable.wall_sit,
            22000, 0) // 22 sec
        exerciseList.add(wallSit)

        return exerciseList
    }

}