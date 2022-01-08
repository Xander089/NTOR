package com.example.ntor.presentation.run.preview

import androidx.lifecycle.*
import com.example.ntor.core.entities.Point
import com.example.ntor.core.usecases.currentRun.RunInfoIOBoundary
import com.example.ntor.presentation.DataHelper
import com.example.ntor.presentation.RunParcelable
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RunCompletedFragmentViewModel @Inject constructor(
    private val boundary: RunInfoIOBoundary,
    private val dataHelper: DataHelper
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
    }


}