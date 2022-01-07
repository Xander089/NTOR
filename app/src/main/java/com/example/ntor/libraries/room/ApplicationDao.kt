package com.example.ntor.libraries.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface ApplicationDao {


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPoint(point: PointRoomEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRun(run: RunRoomEntity)

    @Query("SELECT * FROM RunRoomEntity ORDER BY id LIMIT 1")
    fun getLatestRun() : Flow<RunRoomEntity>

    @Query("SELECT * FROM PointRoomEntity WHERE time = :time")
    suspend fun getPointByTime(time: Long) : PointRoomEntity

}