package com.example.ntor.presentation.main.progress.detail

import androidx.lifecycle.ViewModel
import com.example.ntor.core.usecases.currentRun.RunInfoIOBoundary
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class RunDetailFragmentViewModel @Inject constructor(private val boundary: RunInfoIOBoundary) : ViewModel() {}