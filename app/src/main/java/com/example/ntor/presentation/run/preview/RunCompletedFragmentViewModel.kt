package com.example.ntor.presentation.run.preview

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
    private val latestRunId = boundary.getLatestRunId().asLiveData()

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
        insertPoints()
    }

    private suspend fun insertPoints() {
        val id = latestRunId.value ?: 0
        points.value?.forEach {
            boundary.insertPoint(id,it.latitude,it.longitude)
        }
    }


}