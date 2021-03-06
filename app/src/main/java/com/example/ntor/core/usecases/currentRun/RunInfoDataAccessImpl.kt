package com.example.ntor.core.usecases.currentRun

import android.util.Log
import com.example.ntor.core.entities.Point
import com.example.ntor.core.entities.Run
import com.example.ntor.core.usecases.EntityMapper.toPoint
import com.example.ntor.core.usecases.EntityMapper.toPointRoomEntity
import com.example.ntor.core.usecases.EntityMapper.toRun
import com.example.ntor.libraries.room.ApplicationDao
import com.example.ntor.libraries.room.CurrentRunPointsRoomEntity
import com.example.ntor.libraries.room.PointRoomEntity
import com.example.ntor.libraries.room.RunRoomEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.runBlocking
import java.util.*
import javax.inject.Inject

class RunInfoDataAccessImpl @Inject constructor(private val dao: ApplicationDao) :
    RunInfoDataAccessInterface {


    //RUN
    override suspend fun insertRun(
        distance: Double,
        time: Int,
        date: Long,
        pacing: Double,
        calories: Double
    ) =
        dao.insertRun(RunRoomEntity(distance, time, date, pacing, calories))

    override suspend fun getRunIdByDate(date: Long): Int = dao.getRunIdByDate(date)
    override fun getRunIdByDateAsFlow(date: Long): Flow<Int> = dao.getRunIdByDateAsFlow(date)
    override suspend fun getLastId(): Int = dao.getLastId()

    override fun getLatestRun(): Flow<Run> = dao.getLatestRun().map { it.toRun() }
    override fun getLatestRunId(): Flow<Int> = dao.getLatestId()

    override fun getRunsAsFlow(): Flow<List<Run>> = dao.getRunsAsFlow().map { list ->
        list.map { runEntity ->
            runEntity.toRun()
        }
    }

    override fun getRunByTime(time: Long): Flow<Run> = dao.getRunByTime(time).map { it.toRun() }
    override suspend fun deleteRunById(runId: Int) = dao.deleteRunById(runId)
    override suspend fun deleteRunByDate(date: Long) {
        dao.deleteRunByDate(date)
    }

    //POINT
    override suspend fun getPointByTime(time: Long): Point = dao.getPointByTime(time).toPoint()
    override suspend fun getPointsById(runId: Int): List<Point> =
        dao.getPointsById(runId).map { it.toPoint() }

    override fun getPointsByIdAsFlow(runId: Int): Flow<List<Point>> =
        dao.getPointsByIdAsFlow(runId).map {
            it.map { point -> point.toPoint() }
        }

    override suspend fun deletePointsById(runId: Int) = dao.deletePointsById(runId)
    override suspend fun insertPoint(runId: Int, latitude: Double, longitude: Double) =
        dao.insertPoint(
            PointRoomEntity(
                runId,
                latitude,
                longitude,
                Date().time
            )
        )

    override suspend fun insertPoints(runId: Int, points: List<Point>) {
        val pointsEntities = points.map { it.toPointRoomEntity(runId) }
        dao.insertPoints(pointsEntities)
    }

    //TEMP POINT
    override fun getTempPoints(): Flow<List<Point>> =
        dao.getTempPoints().map { list -> list.map { it.toPoint() } }

    override suspend fun deleteTempPoints() = dao.deleteTempPoints()
    override suspend fun insertTempPoint(latitude: Double, longitude: Double) =
        dao.insertTempPoint(
            CurrentRunPointsRoomEntity(
                latitude = latitude,
                longitude = longitude,
                time = Date().time
            )
        )
}