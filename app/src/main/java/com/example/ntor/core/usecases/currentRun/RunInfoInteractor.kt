package com.example.ntor.core.usecases.currentRun

import com.example.ntor.core.entities.Point
import com.example.ntor.core.entities.Run
import kotlinx.coroutines.flow.Flow

class RunInfoInteractor(
    private val dataAccess: RunInfoDataAccessInterface
) : RunInfoIOBoundary {

    override fun getLatestRun(): Flow<Run> = dataAccess.getLatestRun()
    override suspend fun insertRun(distance: Double, time: Int, date: Long) =
        dataAccess.insertRun(distance, time, date)


    override fun getRunsAsFlow(): Flow<List<Run>> =
        dataAccess.getRunsAsFlow()


    override fun getRunByTime(time: Long): Flow<Run> = dataAccess.getRunByTime(time)

    override suspend fun deleteRunById(runId: Int) = dataAccess.deleteRunById(runId)

    override suspend fun getPointByTime(time: Long): Point = dataAccess.getPointByTime(time)

    override suspend fun getPointsById(runId: Int): List<Point> = dataAccess.getPointsById(runId)

    override fun getPointsByIdAsFlow(runId: Int): Flow<List<Point>> =
        dataAccess.getPointsByIdAsFlow(runId)

    override suspend fun deletePointsById(runId: Int) = dataAccess.deletePointsById(runId)

    override suspend fun insertPoint(runId: Int, latitude: Double, longitude: Double) =
        dataAccess.insertPoint(runId, latitude, longitude)

    override  fun getTempPoints(): Flow<List<Point>> = dataAccess.getTempPoints()

    override suspend fun deleteTempPoints() = dataAccess.deleteTempPoints()

    override suspend fun insertCurrentPoint(latitude: Double, longitude: Double) =
        dataAccess.insertTempPoint(latitude, longitude)

}