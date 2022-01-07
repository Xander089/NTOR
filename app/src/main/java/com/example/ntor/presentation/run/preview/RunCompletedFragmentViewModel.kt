package com.example.ntor.presentation.run.preview

import androidx.lifecycle.ViewModel
import com.example.ntor.core.usecases.currentRun.CurrentRunBoundaryService
import javax.inject.Inject

class RunCompletedFragmentViewModel @Inject constructor(private val boundary: CurrentRunBoundaryService) : ViewModel() {}