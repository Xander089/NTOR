package com.example.ntor.presentation.run.started

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.ntor.core.usecases.currentRun.CurrentRunBoundaryService
import com.example.ntor.core.usecases.currentRun.CurrentRunInteractor
import dagger.hilt.android.lifecycle.HiltViewModel
import java.util.*
import javax.inject.Inject

@HiltViewModel
class RunFragmentViewModel @Inject constructor(private val boundary: CurrentRunInteractor) :
    ViewModel() {

    val lastInsertion : Long = Date().time
    val latestRun = boundary.getLatestRun().asLiveData()

    private var userMotionState = UserMotion.STILL
    fun setUserMotionState(state: UserMotion) {
        userMotionState = state
    }

    private fun isUserMoving() = userMotionState == UserMotion.MOVING

    fun createNewRun(){}

    fun insertNewPosition(latitude: Double, longitude: Double) {

        if (isUserMoving()) {
            Log.v("punto", latitude.toString() + " " + longitude.toString())
            setUserMotionState(UserMotion.STILL)
        }

    }

}