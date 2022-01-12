package com.example.ntor.presentation.run.preview

import android.util.Log
import androidx.lifecycle.*
import com.example.ntor.core.entities.Point
import com.example.ntor.core.usecases.currentRun.RunInfoIOBoundary
import com.example.ntor.presentation.DataHelper
import com.example.ntor.presentation.RunParcelable
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RunCompletedFragmentViewModel @Inject constructor(
    private val boundary: RunInfoIOBoundary
) :
    ViewModel() {


    var points: LiveData<List<Point>> = MutableLiveData()

    fun setupRoute() {
        points = boundary.getTempPoints().asLiveData()
    }

    fun formatDistance(distance: Double) = DataHelper.formatNumber(distance/1000.0)
    fun formatPacing(pacing: Double) = DataHelper.toMinutes(pacing)
    fun formatCalories(calories: Double) = DataHelper.formatDouble(calories)
    fun formatTime(seconds: Int) = DataHelper.toTime(seconds)
    fun formatDate(date: Long) = DataHelper.formatDate(date)

    fun createRun(
        parcel: RunParcelable
    ) = viewModelScope.launch(Dispatchers.IO) {

        boundary.insertRun(
            parcel.distance,
            parcel.time,
            parcel.date,
            parcel.pacing,
            parcel.calories
        )

        insertPoints(boundary.getLastId())
    }

    private suspend fun insertPoints(runId: Int) {
        points.value?.forEach {
            boundary.insertPoint(runId,it.latitude,it.longitude)
        }
    }


}