package com.example.ntor.presentation.main.progress.list

import androidx.lifecycle.ViewModel
import com.example.ntor.core.usecases.currentRun.RunInfoIOBoundary
import javax.inject.Inject

class AllRunsFragmentViewModel @Inject constructor(private val boundary: RunInfoIOBoundary) :
    ViewModel() {}