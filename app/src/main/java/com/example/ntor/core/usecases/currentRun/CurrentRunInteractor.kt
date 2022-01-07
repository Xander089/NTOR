package com.example.ntor.core.usecases.currentRun

import com.example.ntor.core.entities.Run
import kotlinx.coroutines.flow.Flow

class CurrentRunInteractor(
    private val facade: CurrentRunFacade
) : CurrentRunBoundaryService{

    override fun getLatestRun(): Flow<Run> = facade.getLatestRun()

}