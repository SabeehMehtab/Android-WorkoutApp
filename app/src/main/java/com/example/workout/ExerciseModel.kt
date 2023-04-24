package com.example.workout

class ExerciseModel (
    private var id: Int,
    private var name: String,
    private var image: Int,
    private var duration: Long,
    private var restDuration: Long,
    private var isCompleted: Boolean = false,
    private var isSelected: Boolean = false
    ) {
    ////
    fun getId(): Int {
        return this.id
    }
    fun setId(id: Int) {
        this.id = id
    }
    ////
    fun getName(): String {
        return this.name
    }
    fun setName(name: String) {
        this.name = name
    }
    ////
    fun getImage(): Int {
        return this.image
    }
    fun setImage(image: Int) {
        this.image = image
    }
    ////
    fun getIsCompleted(): Boolean {
        return this.isCompleted
    }
    fun setIsCompleted(isCompleted: Boolean) {
        this.isCompleted = isCompleted
    }
    ////
    fun getIsSelected(): Boolean {
        return this.isSelected
    }
    fun setIsSelected(isSelected: Boolean) {
        this.isSelected = isSelected
    }
    ////
    fun getDuration(): Long {
        return this.duration
    }
    fun setDuration(duration: Long) {
        this.duration = duration
    }
    ////
    fun getRestDuration(): Long {
        return this.restDuration
    }
    fun setRestDuration(restDuration: Long) {
        this.restDuration = restDuration
    }

}