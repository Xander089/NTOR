package com.example.ntor.presentation.main.progress.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.ntor.core.usecases.currentRun.RunInfoIOBoundary
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class AllRunsFragmentViewModel @Inject constructor(private val boundary: RunInfoIOBoundary) :
    ViewModel() {

        val runs = boundary.getRunsAsFlow().asLiveData()

    }