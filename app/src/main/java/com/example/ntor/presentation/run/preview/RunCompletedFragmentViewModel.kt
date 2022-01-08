package com.example.ntor.presentation.run.preview

import androidx.lifecycle.ViewModel
import com.example.ntor.core.usecases.currentRun.RunInfoIOBoundary
import javax.inject.Inject

class RunCompletedFragmentViewModel @Inject constructor(private val boundary: RunInfoIOBoundary) : ViewModel() {}