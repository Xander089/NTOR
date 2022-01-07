package com.example.ntor.core.entities

import java.util.*

fun main() {
    val a = Date().time
    println(a)

}


data class Run(
    val distance: Float = 0.0f,
    val time: Int = 0,
    val date: Long = 0,
) {

    val avgSpeed: Float
        get() = if (this.time == 0) {
            0.0f
        } else {
            this.distance / this.time
        }
}