package com.example.ntor.presentation.run.countdown

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ntor.presentation.utils.DataHelper
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class CountDownViewModel : ViewModel() {

    private var countDownTimer: Flow<Int>? = null
    private var timerJob: Job? = null

    private val _timerText = MutableLiveData<String>()
    val timerText: LiveData<String> = _timerText

    init {
        countDownTimer = DataHelper.provideCountDownTimerDesc(10)
    }

    fun startTimer() {
        timerJob = viewModelScope.launch {
            countDownTimer?.cancellable()
                ?.collect { time ->
                    _timerText.value = time.toString()
                }
        }
        viewModelScope.launch {
            timerJob?.join()
        }
    }

    private fun stopTimer() {
        timerJob?.cancel()
    }

    fun addSeconds(seconds: Int, time: String) {
        stopTimer()
        countDownTimer = DataHelper.provideCountDownTimerDesc(seconds + time.toInt())
        startTimer()
    }

    fun disposeTimer() {
        stopTimer()
        timerJob = null
        countDownTimer = null

    }


}