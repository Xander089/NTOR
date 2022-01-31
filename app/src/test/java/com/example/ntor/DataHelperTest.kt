package com.example.ntor

import com.example.ntor.core.entities.Point
import com.example.ntor.presentation.utils.Constants
import com.example.ntor.presentation.utils.DataHelper
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Test
import java.util.*
import kotlin.math.atan2
import kotlin.math.cos
import kotlin.math.sin
import kotlin.math.sqrt

class DataHelperTest {

    @Test
    fun `when input distance is 1 point 12345 then the returned string is 1 point 1 km`() {
        //given
        val inputDistance = 1.12345
        //when
        val actual = DataHelper.formatNumber(inputDistance)
        //then
        assertEquals("1.1 km", actual)
    }

    @Test
    fun `when input is one second then the timer elapsed time is one second`() = runBlocking{
        //given
        val actual = DataHelper.provideCountDownTimer(0,1).last()
        //then
        assertEquals(1, actual)
    }

    @Test
    fun `when input distance is 1 point 12345 then the returned string is 1 point 1`() {
        //given
        val inputDistance = 1.12345
        //when
        val actual = DataHelper.formatDouble(inputDistance)
        //then
        assertEquals("1.1", actual)
    }


//    fun calculateCalories(distance: Double): Double =
//        distance / DataHelper.TO_KM * DataHelper.AVG_CONSUMPTION_PER_KM
//
//    fun calculateMinutesPerKm(seconds: Int, distance: Double): Double {
//        val minutes = seconds.toDouble() / 60.0
//        val km = distance / DataHelper.TO_KM
//        return minutes / km
//    }
//
//    fun toTime(seconds: Int): String {
//
//        if (seconds <= 0) {
//            return DataHelper.RESET_TIME
//        }
//
//        val hour = seconds / 3600
//        var remainderSeconds = seconds % 3600
//        val minutes = remainderSeconds / 60
//        remainderSeconds %= 60
//
//        val h = if (hour < 10) "${DataHelper.ZERO}$hour" else "$hour"
//        val m = if (minutes < 10) "${DataHelper.ZERO}$minutes" else "$minutes"
//        val s = if (remainderSeconds < 10) "${DataHelper.ZERO}$remainderSeconds" else "$remainderSeconds"
//
//        return "$h:$m:$s"
//    }
//
//    fun toSeconds(time: String): Int {
//
//        val splitTime = time.split(":")
//
//        if (splitTime.size < 3) {
//            return 0
//        }
//
//        val hours = splitTime[0].toInt() * 3600
//        val minutes = splitTime[1].toInt() * 60
//        val seconds = splitTime[2].toInt()
//
//        return hours + minutes + seconds
//    }
//
//    fun toMinutes(value: Double): String {
//
//        val minutes = value.toInt()
//        val mantissa = value - minutes
//        val remainingSeconds = (mantissa * 60).toInt()
//
//        return "$minutes:$remainingSeconds"
//    }
//
//    fun toHours(seconds: Int): String {
//        val hours = seconds / 3600.0
//        return formatDouble(hours)
//    }
//
//    fun metresToKm(distance: Double) = distance / 1000.0


}