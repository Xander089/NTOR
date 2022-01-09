package com.example.ntor.useCaseTest

import com.example.ntor.core.entities.Point
import com.example.ntor.core.entities.Run
import com.example.ntor.core.usecases.currentRun.RunInfoDataAccessInterface
import kotlinx.coroutines.flow.Flow

class FakeDataAccessImpl : RunInfoDataAccessInterface {
    override suspend fun insertRun(
        distance: Double,
        time: Int,
        date: Long,
        pacing: Double,
        calories: Double
    ) {
        TODO("Not yet implemented")
    }

    override fun getLatestRun(): Flow<Run> {
        TODO("Not yet implemented")
    }

    override fun getLatestRunId(): Flow<Int> {
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

    override fun getTempPoints(): Flow<List<Point>> {
        TODO("Not yet implemented")
    }

    override suspend fun deleteTempPoints() {
        TODO("Not yet implemented")
    }

    override suspend fun insertTempPoint(latitude: Double, longitude: Double) {
        TODO("Not yet implemented")
    }
}