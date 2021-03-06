package com.example.ntor.presentation.main.progress.detail

import android.util.Log
import androidx.lifecycle.*
import com.example.ntor.core.entities.Point
import com.example.ntor.core.entities.Run
import com.example.ntor.core.usecases.currentRun.RunInfoIOBoundary
import com.example.ntor.presentation.utils.DataHelper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@HiltViewModel
class RunDetailFragmentViewModel @Inject constructor(private val boundary: RunInfoIOBoundary) :
    ViewModel() {

    var run: LiveData<Run>? = MutableLiveData()
    private var _points = MutableLiveData<List<Point>>()
    val points: LiveData<List<Point>> = _points
    var runId: LiveData<Int>? = null

    fun setupDetailRun(time: Long) {
        run = boundary.getRunByTime(time).asLiveData()
        runId = boundary.getRunIdByDateAsFlow(time).asLiveData()
    }

    fun setRoute(id: Int) = viewModelScope.launch {
        _points.value = boundary.getPointsById(id)
    }

    fun releaseLiveData() {
        run = null
        runId = null
    }

    fun formatDistance(distance: Double) = DataHelper.formatNumber(distance / 1000.0)
    fun formatPacing(pacing: Double) = DataHelper.toMinutes(pacing)
    fun formatTime(seconds: Int) = DataHelper.toTime(seconds)
    fun formatDate(date: Long) = DataHelper.formatDate(date)

}