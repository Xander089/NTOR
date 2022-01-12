package com.example.ntor.libraries.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface ApplicationDao {


    //RUN
    @Query("SELECT id FROM RunRoomEntity ORDER BY id DESC LIMIT 1")
    fun getLatestId(): Flow<Int>

    @Query("SELECT id FROM RunRoomEntity ORDER BY id DESC LIMIT 1")
    suspend fun getLastId(): Int

    @Query("SELECT id FROM RunRoomEntity WHERE date = :date")
    suspend fun getRunIdByDate(date: Long): Int

    @Query("SELECT id FROM RunRoomEntity WHERE date = :date")
    fun getRunIdByDateAsFlow(date: Long): Flow<Int>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRun(run: RunRoomEntity)

    @Query("SELECT * FROM RunRoomEntity")
    fun getRunsAsFlow(): Flow<List<RunRoomEntity>>

    @Query("SELECT * FROM RunRoomEntity WHERE date = :date")
    fun getRunByTime(date: Long): Flow<RunRoomEntity>

    @Query("SELECT * FROM RunRoomEntity ORDER BY id LIMIT 1")
    fun getLatestRun(): Flow<RunRoomEntity>

    @Query("SELECT * FROM RunRoomEntity ORDER BY id LIMIT 1")
    fun getLatestRunInNonUIThread(): RunRoomEntity

    @Query("DELETE FROM RunRoomEntity WHERE id = :runId")
    suspend fun deleteRunById(runId: Int)

    @Query("DELETE FROM RunRoomEntity WHERE date = :date")
    suspend fun deleteRunByDate(date: Long)

    //POINTS
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPoint(point: PointRoomEntity)

    @Query("SELECT * FROM PointRoomEntity WHERE time = :time")
    suspend fun getPointByTime(time: Long): PointRoomEntity

    @Query("SELECT * FROM PointRoomEntity WHERE runId = :runId")
    suspend fun getPointsById(runId: Int): List<PointRoomEntity>

    @Query("SELECT * FROM PointRoomEntity WHERE runId = :runId")
    fun getPointsByIdAsFlow(runId: Int): Flow<List<PointRoomEntity>>

    @Query("DELETE FROM PointRoomEntity WHERE runId = :runId")
    suspend fun deletePointsById(runId: Int)


    //TEMP POINTS
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTempPoint(point: CurrentRunPointsRoomEntity)

    @Query("DELETE FROM CurrentRunPointsRoomEntity")
    suspend fun deleteTempPoints()

    @Query("SELECT * FROM CurrentRunPointsRoomEntity")
    fun getTempPoints(): Flow<List<CurrentRunPointsRoomEntity>>

}