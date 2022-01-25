package com.example.ntor.presentation.run.preview

import android.util.Log
import androidx.lifecycle.*
import com.example.ntor.core.entities.Point
import com.example.ntor.core.usecases.currentRun.RunInfoIOBoundary
import com.example.ntor.presentation.utils.Constants.M_TO_KM
import com.example.ntor.presentation.utils.DataHelper
import com.example.ntor.presentation.utils.RunParcelable
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
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
        points.value?.let {
            boundary.insertPoints(runId, it)
            boundary.deleteTempPoints()
        }
    }

    fun formatDistance(distance: Double) = DataHelper.formatNumber(distance / M_TO_KM)
    fun formatPacing(pacing: Double) = DataHelper.toMinutes(pacing)
    fun formatCalories(calories: Double) = DataHelper.formatDouble(calories)
    fun formatTime(seconds: Int) = DataHelper.toTime(seconds)
    fun formatDate(date: Long) = DataHelper.formatDate(date)

}