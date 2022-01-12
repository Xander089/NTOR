package com.example.ntor.presentation.main.progress.detail

import android.util.Log
import androidx.lifecycle.*
import com.example.ntor.core.entities.Point
import com.example.ntor.core.entities.Run
import com.example.ntor.core.usecases.currentRun.RunInfoIOBoundary
import com.example.ntor.presentation.DataHelper
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class RunDetailFragmentViewModel @Inject constructor(private val boundary: RunInfoIOBoundary) :
    ViewModel() {

    var run: LiveData<Run> = MutableLiveData()
    var points: LiveData<List<Point>> = MutableLiveData(emptyList())
    lateinit var runId : LiveData<Int>

    fun setupDetailRun(time: Long) {
        run = boundary.getRunByTime(time).asLiveData()
        runId = boundary.getRunIdByDateAsFlow(time).asLiveData()
    }

    fun setRoute(id: Int){
        Log.v("aaa8",id.toString())
        points = boundary.getPointsByIdAsFlow(id).asLiveData()
    }

    fun formatDistance(distance: Double) = DataHelper.formatNumber(distance/1000.0)
    fun formatPacing(pacing: Double) = DataHelper.toMinutes(pacing)
    fun formatTime(seconds: Int) = DataHelper.toTime(seconds)
    fun formatDate(date: Long) = DataHelper.formatDate(date)

}