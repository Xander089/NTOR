package com.example.ntor.presentation.run.started

import android.util.Log
import androidx.lifecycle.*
import com.example.ntor.core.entities.Point
import com.example.ntor.core.usecases.currentRun.RunInfoIOBoundary
import com.example.ntor.presentation.DataHelper
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
import java.util.*
import javax.inject.Inject
import kotlin.math.atan2
import kotlin.math.cos
import kotlin.math.sin
import kotlin.math.sqrt

@HiltViewModel
class RunFragmentViewModel @Inject constructor(private val boundary: RunInfoIOBoundary) :
    ViewModel() {

    companion object {
        private const val FOUR_SECONDS = 4000
        private const val MAX_TIME = 7200
        private const val RESET_TIME = "00:00:00"
    }

    val dataHelper = DataHelper()
    private var countDownTimer: Flow<Int> = dataHelper.provideCountDownTimer(0, MAX_TIME)
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

    private fun isUserMoving() = userMotionState == UserMotion.MOVING

    private var timerState = TimerState.ON
    fun setTimerState(state: TimerState) {
        timerState = state
    }

    private fun isTimerStateOn() = timerState == TimerState.ON

    fun updatePacing(time: String, distance: Double) {
        val seconds = dataHelper.toSeconds(time)
        _pacing.value = dataHelper.calculateMinutesPerKm(seconds, distance)
    }

    fun updateCalories(distance: Double) {
        _calories.value = dataHelper.calculateCalories(distance)
    }

    fun updateDistance(points: List<Point>) {
        when (points.size) {
            0 -> return
            1 -> return
            else -> {
                val currentDistance = _distance.value ?: 0.0
                val lastTwoPoints = points.takeLast(2)
                _distance.value = dataHelper.calculateDistance(currentDistance, lastTwoPoints)
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
        countDownTimer = dataHelper.provideCountDownTimer(dataHelper.toSeconds(time), MAX_TIME)
        timerJob = viewModelScope.launch {
            countDownTimer.cancellable()
                .collect { seconds ->
                    _timerText.value = dataHelper.toTime(seconds)

                }
        }
        viewModelScope.launch {
            timerJob.join()
        }
    }

    fun stopTimer() {
        timerJob.cancel()
    }


    fun createNewRun() {
        viewModelScope.launch(Dispatchers.IO) {

        }
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