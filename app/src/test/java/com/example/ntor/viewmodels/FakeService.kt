package com.example.ntor.viewmodels

import com.example.ntor.core.entities.Point
import com.example.ntor.core.entities.Run
import com.example.ntor.core.usecases.currentRun.RunInfoIOBoundary
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FakeService : RunInfoIOBoundary {
    override fun getLatestRun(): Flow<Run> {
        return flow {}
    }

    override fun getLatestRunId(): Flow<Int> {
        return flow<Int> {}
    }

    override suspend fun insertRun(
        distance: Double,
        time: Int,
        date: Long,
        pacing: Double,
        calories: Double
    ) {

    }

    override fun getRunsAsFlow(): Flow<List<Run>> =
        flow {
            emit(listOf(Run(0.0, 0, 0L, 0.0)))
        }

    override fun getRunByTime(time: Long): Flow<Run> {
        return flow {}
    }

    override suspend fun deleteRunById(runId: Int) {

    }

    override suspend fun getPointByTime(time: Long): Point {
        return Point(0.0, 0.0, 0L)
    }

    override suspend fun getPointsById(runId: Int): List<Point> {
        return emptyList<Point>()
    }

    override fun getPointsByIdAsFlow(runId: Int): Flow<List<Point>> {
        return flow {}
    }

    override suspend fun deletePointsById(runId: Int) {

    }

    override suspend fun insertPoint(runId: Int, latitude: Double, longitude: Double) {

    }

    override fun getTempPoints(): Flow<List<Point>> {
        return flow {}
    }

    override suspend fun deleteTempPoints() {

    }

    override suspend fun insertCurrentPoint(latitude: Double, longitude: Double) {

    }
}