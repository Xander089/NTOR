package com.example.ntor.entities

data class Run(
    val points: List<Point>,
    val distance: Float,
    val time: Int,
    //val date: Date
    val cityFrom: String,
    val cityTo: String,
) {
}