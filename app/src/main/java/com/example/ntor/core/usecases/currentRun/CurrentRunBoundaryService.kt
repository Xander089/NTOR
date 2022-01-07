package com.example.ntor.core.usecases.currentRun

import com.example.ntor.core.entities.Run
import kotlinx.coroutines.flow.Flow

interface  CurrentRunBoundaryService{

    fun getLatestRun(): Flow<Run>

}