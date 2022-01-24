package com.example.ntor.presentation.utils

import com.example.ntor.core.entities.Point
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow
import java.util.*
import kotlin.math.atan2
import kotlin.math.cos
import kotlin.math.sin
import kotlin.math.sqrt

object DataHelper {


    private const val EARTH_RADIUS_M = 6371000
    private const val RESET_TIME = "00:00:00"
    private const val ZERO = "0"
    private const val LB_KG = 0.4536
    private const val TO_KM = 1000.0
    private const val FACTOR = 0.75
    private const val MILES_KM = 1.608
    private const val AVG_WEIGHT_KG = 70.0
    private const val AVG_CONSUMPTION_PER_KM = AVG_WEIGHT_KG / LB_KG * FACTOR / MILES_KM

    private val monthMap = mapOf<String, String>(
        "Jan" to "01",
        "Feb" to "02",
        "Mar" to "03",
        "Apr" to "04",
        "May" to "05",
        "Jun" to "06",
        "Jul" to "07",
        "Aug" to "08",
        "Sep" to "09",
        "Oct" to "10",
        "Nov" to "11",
        "Dec" to "12",
    )

    fun formatNumber(number: Double): String {
        val numString = number.toString()
        val pointPosition = numString.indexOf(".")
        val formatted = numString.substring(0, pointPosition + 2)
        return "$formatted km"
    }

    fun formatDate(time: Long): String {
        val split = Date(time).toString().split(" ")
        return "${monthMap[split[1]]}/${split[2]}/${split[5]}"
    }


    fun provideCountDownTimer(currentTime: Int, maxTime: Int) =
        flow {
            var counter = currentTime
            while (counter < maxTime) {
                delay(1000)
                counter++
                emit(counter)
            }
        }

    fun provideCountDownTimerDesc(time: Int) =
        flow {
            var currentTime = time
            while (currentTime > 0) {
                delay(Constants.DEFAULT_DELAY_TIME)
                currentTime--
                emit(currentTime)
            }
        }


    fun formatDouble(number: Double): String {
        val numToString = number.toString()
        val pointPosition = numToString.indexOf('.')
        return numToString.substring(0, pointPosition + 2)
    }


    fun calculateDistance(currentDistance: Double, route: List<Point>): Double {

        var delta = 0.0

        if (route.isEmpty() || route.size <= 1) {
            return 0.0
        } else {
            for (i in 0 until route.size - 1) {
                val phi1 = route[i].latitude * Math.PI / 180.0
                val phi2 = route[i + 1].latitude * Math.PI / 180.0
                val deltaPhi = (route[i + 1].latitude - route[i].latitude) * Math.PI / 180.0
                val deltaLambda =
                    (route[i + 1].longitude - route[i].longitude) * Math.PI / 180.0
                val a = sin(deltaPhi / 2) * sin(deltaPhi / 2) + cos(phi1) * cos(phi2) *
                        sin(deltaLambda / 2) * sin(deltaLambda / 2)
                val c = 2 * atan2(sqrt(a), sqrt(1 - a))
                delta += EARTH_RADIUS_M * c
            }
        }

        return currentDistance + delta
    }

    fun calculateCalories(distance: Double): Double =
        distance / TO_KM * AVG_CONSUMPTION_PER_KM

    fun calculateMinutesPerKm(seconds: Int, distance: Double): Double {
        val minutes = seconds.toDouble() / 60.0
        val km = distance / TO_KM
        return minutes / km
    }

    fun toTime(seconds: Int): String {

        if (seconds <= 0) {
            return RESET_TIME
        }

        val hour = seconds / 3600
        var remainderSeconds = seconds % 3600
        val minutes = remainderSeconds / 60
        remainderSeconds %= 60

        val h = if (hour < 10) "$ZERO$hour" else "$hour"
        val m = if (minutes < 10) "$ZERO$minutes" else "$minutes"
        val s = if (remainderSeconds < 10) "$ZERO$remainderSeconds" else "$remainderSeconds"

        return "$h:$m:$s"
    }

    fun toSeconds(time: String): Int {

        val splitTime = time.split(":")

        if (splitTime.size < 3) {
            return 0
        }

        val hours = splitTime[0].toInt() * 3600
        val minutes = splitTime[1].toInt() * 60
        val seconds = splitTime[2].toInt()

        return hours + minutes + seconds
    }

    fun toMinutes(value: Double): String {

        val minutes = value.toInt()
        val mantissa = value - minutes
        val remainingSeconds = (mantissa * 60).toInt()

        return "$minutes:$remainingSeconds"
    }

    fun toHours(seconds: Int): String {
        val hours = seconds / 3600.0
        return formatDouble(hours)
    }

    fun metresToKm(distance: Double) = distance / 1000.0

}