package com.example.ntor.database

import androidx.room.*
import com.example.ntor.libraries.room.ApplicationDao
import com.example.ntor.libraries.room.CurrentRunPointRoomEntity
import com.example.ntor.libraries.room.PointRoomEntity
import com.example.ntor.libraries.room.RunRoomEntity

@Database(
    entities = [
        RunRoomEntity::class,
        PointRoomEntity::class,
        CurrentRunPointRoomEntity::class
    ], version = 1
)
abstract class TestDatabase : RoomDatabase() {

    abstract fun applicationDao(): ApplicationDao
}