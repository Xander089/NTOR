package com.example.ntor.presentation.run.preview

import androidx.lifecycle.*
import com.example.ntor.core.entities.Point
import com.example.ntor.core.usecases.currentRun.RunInfoIOBoundary
import com.example.ntor.presentation.DataHelper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RunCompletedFragmentViewModel @Inject constructor(private val boundary: RunInfoIOBoundary) :
    ViewModel() {

    val dataHelper = DataHelper()

    var points: LiveData<List<Point>> = MutableLiveData()

    fun setupRoute() {
        points = boundary.getTempPoints().asLiveData()
    }

    fun createRun(
        distance: Double,
        time: Int,
        date: Long,
        pacing: Double,
        calories: Double
    ) = viewModelScope.launch(Dispatchers.IO) { boundary.insertRun(distance, time, date, pacing, calories) }

}