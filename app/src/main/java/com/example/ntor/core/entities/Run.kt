package com.example.ntor.core.entities


data class Run(
    val distance: Double = 0.0,
    val time: Int = 0,
    val date: Long = 0,
    val pacing: Double = 0.0,
    val calories: Double = 0.0,
) {

    val avgSpeed: Double
        get() = if (this.time == 0) {
            0.0
        } else {
            this.distance / this.time
        }
}