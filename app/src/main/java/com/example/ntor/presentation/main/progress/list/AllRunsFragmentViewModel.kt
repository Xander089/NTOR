package com.example.ntor.presentation.main.progress.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.ntor.core.entities.Run
import com.example.ntor.core.usecases.currentRun.RunInfoIOBoundary
import com.example.ntor.presentation.DataHelper
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class AllRunsFragmentViewModel @Inject constructor(private val boundary: RunInfoIOBoundary) :
    ViewModel() {

    fun getRuns() = boundary.getRunsAsFlow()
    val runs = getRuns().asLiveData()

    fun formatActivities(runs: List<Run>) = runs.size.toString()
    fun formatDistance(runs: List<Run>) : String{
        val distanceInKm = runs.map { it.distance }.sum()/1000.0
        return DataHelper.formatDouble(distanceInKm)
    }

    fun formatTime(runs: List<Run>) : String{
        val seconds = runs.map { it.time }.sum()
        return DataHelper.toTime(seconds)
    }


}