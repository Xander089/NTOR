package com.example.ntor.presentation.run.countdown

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class CountDownVIewModel : ViewModel() {

    companion object {
        private const val RESET_TIME = "00:00:00"
        private const val ZERO = "0"
    }

    private var countDownTimer: Flow<Int>
    private lateinit var timerJob: Job
    private val _timerText = MutableLiveData<String>()
    val timerText: LiveData<String> = _timerText

    init {
        countDownTimer = provideCountDownTimer(10)
    }

    private fun secondsToString(seconds: Int) = seconds.toString()

    private fun provideCountDownTimer(time: Int) =
        flow {
            var currentTime = time
            while (currentTime > 0) {
                delay(1000)
                currentTime--
                emit(currentTime)
            }
        }


    fun startTimer() {
        timerJob = viewModelScope.launch {
            countDownTimer.cancellable()
                .collect { time ->
                    _timerText.value = secondsToString(time)

                }
        }
        viewModelScope.launch {
            timerJob.join()
        }
    }

    fun stopTimer() {
        timerJob.cancel()
    }

    fun addSeconds(seconds: Int, time: String) {
        stopTimer()
        countDownTimer = provideCountDownTimer( seconds  + time.toInt())
        startTimer()
    }


}