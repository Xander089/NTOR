package com.example.ntor.core.usecases.currentRun

import com.example.ntor.core.entities.Point
import com.example.ntor.core.entities.Run
import kotlinx.coroutines.flow.Flow


//Boundary to separate the use case interactor from the room dao
interface RunInfoDataAccessInterface {

    //RUN
    suspend fun insertRun(
        distance: Double,
        time: Int,
        date: Long,
        pacing: Double,
        calories: Double
    )

    fun getLatestRun(): Flow<Run>
    fun getLatestRunId(): Flow<Int>
    fun getRunsAsFlow(): Flow<List<Run>>
    fun getRunByTime(time: Long): Flow<Run>
    suspend fun deleteRunById(runId: Int)

    //POINT
    suspend fun getPointByTime(time: Long): Point
    suspend fun getPointsById(runId: Int): List<Point>
    fun getPointsByIdAsFlow(runId: Int): Flow<List<Point>>
    suspend fun deletePointsById(runId: Int)
    suspend fun insertPoint(runId: Int, latitude: Double, longitude: Double)

    //TEMP POINT
    fun getTempPoints(): Flow<List<Point>>
    suspend fun deleteTempPoints()
    suspend fun insertTempPoint(latitude: Double, longitude: Double)
}