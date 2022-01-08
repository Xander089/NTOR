package com.example.ntor.core.entities

import java.util.*

fun main() {
    val a = Date().time
    println(a)

}


data class Run(
    val distance: Double = 0.0,
    val time: Int = 0,
    val date: Long = 0,
) {

    val avgSpeed: Double
        get() = if (this.time == 0) {
            0.0
        } else {
            this.distance / this.time
        }
}