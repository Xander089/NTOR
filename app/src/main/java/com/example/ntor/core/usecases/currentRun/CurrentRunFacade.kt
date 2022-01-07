package com.example.ntor.core.usecases.currentRun

import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.ntor.core.entities.Run
import com.example.ntor.core.usecases.EntityMapper.toRun
import com.example.ntor.libraries.room.ApplicationDao
import com.example.ntor.libraries.room.CurrentRunPointsRoomEntity
import com.example.ntor.libraries.room.PointRoomEntity
import com.example.ntor.libraries.room.RunRoomEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.runBlocking
import java.util.*

class CurrentRunFacade(private val dao: ApplicationDao) {


    //RUN
    suspend fun insertRun(distance: Double, time: Int, date: Long) = dao.insertRun(RunRoomEntity(distance, time, date))
    fun getLatestRun(): Flow<Run> = dao.getLatestRun().map { it.toRun() }
    fun getRunsAsFlow(): Flow<List<RunRoomEntity>> = dao.getRunsAsFlow()
    fun getRunByTime(time: Long): Flow<RunRoomEntity> = dao.getRunByTime(time)
    suspend fun deleteRunById(runId: Int) = dao.deleteRunById(runId)

    //POINT
    suspend fun getPointByTime(time: Long): PointRoomEntity = dao.getPointByTime(time)
    suspend fun getPointsById(runId: Int): List<PointRoomEntity> = dao.getPointsById(runId)
    fun getPointsByIdAsFlow(runId: Int): Flow<List<PointRoomEntity>> = dao.getPointsByIdAsFlow(runId)
    suspend fun deletePointsById(runId: Int) = dao.deletePointsById(runId)
    suspend fun insertPoint(runId: Int, latitude: Double, longitude: Double) =
        dao.insertPoint(
            PointRoomEntity(
                runId,
                latitude,
                longitude,
                Date().time
            )
        )

    //TEMP POINT
    suspend fun getTempPoints(): List<CurrentRunPointsRoomEntity> = dao.getTempPoints()
    suspend fun deleteTempPoints() = dao.deleteTempPoints()
    suspend fun insertTempPoint(latitude: Double, longitude: Double) =
        dao.insertTempPoint(
            CurrentRunPointsRoomEntity(
                latitude = latitude,
                longitude = longitude,
                time = Date().time
            )
        )
}