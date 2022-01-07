package com.example.ntor.core.usecases.showPastRunInfo

import com.example.ntor.core.entities.Point
import com.example.ntor.core.entities.Run
import kotlinx.coroutines.flow.Flow

interface RunInfoBoundaryService {

    //RUN
    fun getLatestRun(): Flow<Run>
    suspend fun insertRun(distance: Double, time: Int, date: Long)
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
    suspend fun getTempPoints(): List<Point>
    suspend fun deleteTempPoints()
    suspend fun insertCurrentPoint(latitude: Double, longitude: Double)
}