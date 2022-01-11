package com.example.ntor.presentation.run.started

import android.util.Log
import androidx.lifecycle.*
import com.example.ntor.core.entities.Point
import com.example.ntor.core.usecases.currentRun.RunInfoIOBoundary
import com.example.ntor.presentation.DataHelper
import com.example.ntor.presentation.RunParcelable
import com.example.ntor.presentation.run.countdown.CountDownVIewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.cancellable
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import java.sql.Time
import java.util.*
import javax.inject.Inject
import kotlin.math.atan2
import kotlin.math.cos
import kotlin.math.sin
import kotlin.math.sqrt

@HiltViewModel
class RunFragmentViewModel @Inject constructor(
    private val boundary: RunInfoIOBoundary
) :
    ViewModel() {

    companion object {
        private const val FOUR_SECONDS = 4000
        private const val MAX_TIME = 7200
        private const val RESET_TIME = "00:00:00"
    }

    fun formatDouble(number: Double) = DataHelper.formatDouble(number)
    fun toMinutes(minutes: Double) = DataHelper.toMinutes(minutes)
    fun toTime(seconds: Int) = DataHelper.toTime(seconds)

    private var countDownTimer: Flow<Int> = DataHelper.provideCountDownTimer(0, MAX_TIME)
    private lateinit var timerJob: Job

    val points = boundary.getTempPoints().asLiveData()

    private val _timerText = MutableLiveData<String>()
    val timerText: LiveData<String> = _timerText

    private val _calories = MutableLiveData<Double>(0.0)
    val calories: LiveData<Double> = _calories

    private val _pacing = MutableLiveData<Double>(0.0)
    val pacing: LiveData<Double> = _pacing

    private val _distance = MutableLiveData<Double>(0.0)
    val distance: LiveData<Double> = _distance

    private var lastInsertion: Long = Date().time

    private var userMotionState = UserMotion.STILL
    fun setUserMotionState(state: UserMotion) {
        userMotionState = state
    }

    fun buildRunParcelable() : RunParcelable {
        val distance: Double = _distance.value ?: 0.0
        val time: Int = DataHelper.toSeconds(_timerText.value.orEmpty())
        val date: Long = Date().time
        val pacing: Double = _pacing.value ?: 0.0
        val calories: Double = _calories.value ?: 0.0

        return RunParcelable(distance, time, date, pacing, calories)
    }

    private fun isUserMoving() = userMotionState == UserMotion.MOVING

    private var timerState = TimerState.ON
    fun setTimerState(state: TimerState) {
        timerState = state
    }

    private fun isTimerStateOn() = timerState == TimerState.ON

    fun updatePacing(time: String, distance: Double) {
        val seconds = DataHelper.toSeconds(time)
        _pacing.value = DataHelper.calculateMinutesPerKm(seconds, distance)
    }

    fun updateCalories(distance: Double) {
        _calories.value = DataHelper.calculateCalories(distance)
    }

    fun updateDistance(points: List<Point>) {
        when (points.size) {
            0 -> return
            1 -> return
            else -> {
                val currentDistance = _distance.value ?: 0.0
                val lastTwoPoints = points.takeLast(2)
                _distance.value = DataHelper.calculateDistance(currentDistance, lastTwoPoints)
            }
        }
    }

    private fun clearTempPoints() =
        viewModelScope.launch(Dispatchers.IO) { boundary.deleteTempPoints() }

    init {
        startTimer(RESET_TIME)
        clearTempPoints()
    }


    fun startTimer(time: String) {
        countDownTimer = DataHelper.provideCountDownTimer(DataHelper.toSeconds(time), MAX_TIME)
        timerJob = viewModelScope.launch {
            countDownTimer.cancellable()
                .collect { seconds ->
                    _timerText.value = DataHelper.toTime(seconds)

                }
        }
        viewModelScope.launch {
            timerJob.join()
        }
    }

    fun stopTimer() {
        timerJob.cancel()
    }


    fun insertNewPosition(latitude: Double, longitude: Double) {

        val time = Date().time
        if (time - lastInsertion > FOUR_SECONDS) {
            lastInsertion = time
            viewModelScope.launch(Dispatchers.IO) {
                if (isTimerStateOn()) {
                    boundary.insertCurrentPoint(latitude, longitude)
                }
                //setUserMotionState(UserMotion.STILL)
            }
        }

    }

}