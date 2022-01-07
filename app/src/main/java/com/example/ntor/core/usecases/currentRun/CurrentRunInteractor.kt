package com.example.ntor.core.usecases.currentRun

import com.example.ntor.core.entities.Point
import com.example.ntor.core.entities.Run
import kotlinx.coroutines.flow.Flow

class CurrentRunInteractor(
    private val facade: CurrentRunFacade
) : CurrentRunBoundaryService {

    override fun getLatestRun(): Flow<Run> = facade.getLatestRun()
    override suspend fun insertRun(distance: Double, time: Int, date: Long) {
        TODO("Not yet implemented")
    }

    override fun getRunsAsFlow(): Flow<List<Run>> {
        TODO("Not yet implemented")
    }

    override fun getRunByTime(time: Long): Flow<Run> {
        TODO("Not yet implemented")
    }

    override suspend fun deleteRunById(runId: Int) {
        TODO("Not yet implemented")
    }

    override suspend fun getPointByTime(time: Long): Point {
        TODO("Not yet implemented")
    }

    override suspend fun getPointsById(runId: Int): List<Point> {
        TODO("Not yet implemented")
    }

    override fun getPointsByIdAsFlow(runId: Int): Flow<List<Point>> {
        TODO("Not yet implemented")
    }

    override suspend fun deletePointsById(runId: Int) {
        TODO("Not yet implemented")
    }

    override suspend fun insertPoint(runId: Int, latitude: Double, longitude: Double) {
        TODO("Not yet implemented")
    }

    override suspend fun getTempPoints(): List<Point> {
        TODO("Not yet implemented")
    }

    override suspend fun deleteTempPoints() {
        TODO("Not yet implemented")
    }

    override suspend fun insertCurrentPoint(latitude: Double, longitude: Double) =
        facade.insertTempPoint(latitude, longitude)

}