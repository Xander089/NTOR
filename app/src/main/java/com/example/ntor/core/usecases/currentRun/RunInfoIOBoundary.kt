package com.example.ntor.core.usecases.currentRun

import com.example.ntor.core.entities.Point
import com.example.ntor.core.entities.Run
import com.example.ntor.core.usecases.EntityMapper.toRun
import com.example.ntor.libraries.room.CurrentRunPointsRoomEntity
import com.example.ntor.libraries.room.PointRoomEntity
import com.example.ntor.libraries.room.RunRoomEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.util.*

//Boundary to separate the use case interactor from the controller/presentation layer (the viewModels)
interface RunInfoIOBoundary {

    //RUN
    fun getLatestRun(): Flow<Run>
    fun getLatestRunId(): Flow<Int>
    suspend fun getLastId(): Int

    suspend fun getRunIdByDate(date: Long): Int
    fun getRunIdByDateAsFlow(date: Long): Flow<Int>
    suspend fun insertRun(
        distance: Double,
        time: Int,
        date: Long,
        pacing: Double,
        calories: Double
    )

    fun getRunsAsFlow(): Flow<List<Run>>
    fun getRunByTime(time: Long): Flow<Run>
    suspend fun deleteRunById(runId: Int)
    suspend fun deleteRunByDate(date: Long)


    //POINT
    suspend fun getPointByTime(time: Long): Point
    suspend fun getPointsById(runId: Int): List<Point>
    fun getPointsByIdAsFlow(runId: Int): Flow<List<Point>>
    suspend fun deletePointsById(runId: Int)
    suspend fun insertPoint(runId: Int, latitude: Double, longitude: Double)
    suspend fun insertPoints(runId: Int, points: List<Point>)


    //TEMP POINT
    fun getTempPoints(): Flow<List<Point>>
    suspend fun deleteTempPoints()
    suspend fun insertCurrentPoint(latitude: Double, longitude: Double)


}