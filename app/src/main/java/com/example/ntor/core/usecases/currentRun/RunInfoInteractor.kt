package com.example.ntor.core.usecases.currentRun

import com.example.ntor.core.entities.Point
import com.example.ntor.core.entities.Run
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class RunInfoInteractor @Inject constructor(
    private val dataAccess: RunInfoDataAccessInterface
) : RunInfoIOBoundary {

    override fun getLatestRun(): Flow<Run> = dataAccess.getLatestRun()
    override fun getLatestRunId(): Flow<Int> = dataAccess.getLatestRunId()
    override suspend fun getRunIdByDate(date: Long): Int = dataAccess.getRunIdByDate(date)
    override fun getRunIdByDateAsFlow(date: Long): Flow<Int> = dataAccess.getRunIdByDateAsFlow(date)
    override suspend fun getLastId(): Int = dataAccess.getLastId()

    override suspend fun insertRun(
        distance: Double,
        time: Int,
        date: Long,
        pacing: Double,
        calories: Double
    ) =
        dataAccess.insertRun(distance, time, date, pacing, calories)


    override fun getRunsAsFlow(): Flow<List<Run>> =
        dataAccess.getRunsAsFlow()


    override fun getRunByTime(time: Long): Flow<Run> = dataAccess.getRunByTime(time)

    override suspend fun deleteRunById(runId: Int) = dataAccess.deleteRunById(runId)
    override suspend fun deleteRunByDate(date: Long) {
        dataAccess.deleteRunByDate(date)
    }

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