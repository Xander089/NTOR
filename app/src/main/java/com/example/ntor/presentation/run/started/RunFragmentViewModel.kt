package com.example.ntor.presentation.run.started

import android.util.Log
import androidx.lifecycle.*
import com.example.ntor.core.usecases.currentRun.CurrentRunBoundaryService
import com.example.ntor.core.usecases.currentRun.CurrentRunInteractor
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

@HiltViewModel
class RunFragmentViewModel @Inject constructor(private val boundary: CurrentRunInteractor) :
    ViewModel() {

    val lastInsertion: Long = Date().time
    val latestRun = boundary.getLatestRun().asLiveData()

    private var userMotionState = UserMotion.STILL
    fun setUserMotionState(state: UserMotion) {
        userMotionState = state
    }

    private fun isUserMoving() = userMotionState == UserMotion.MOVING

    fun createNewRun() {
        viewModelScope.launch(Dispatchers.IO) {

        }
    }

    fun insertNewPosition(latitude: Double, longitude: Double) {
        viewModelScope.launch(Dispatchers.IO) {
            if (isUserMoving()) {
                boundary.insertCurrentPoint(latitude, longitude)
            }
        }
        setUserMotionState(UserMotion.STILL)
    }

}