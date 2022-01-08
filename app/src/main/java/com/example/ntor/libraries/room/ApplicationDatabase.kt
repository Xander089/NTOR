package com.example.ntor.libraries.room

import androidx.room.*

@Database(
    entities = [
        RunRoomEntity::class,
        PointRoomEntity::class,
        CurrentRunPointsRoomEntity::class
    ], version = 1
)
abstract class ApplicationDatabase : RoomDatabase() {

    abstract fun applicationDao(): ApplicationDao
}

@Entity
data class RunRoomEntity(
    @ColumnInfo(name = "distance") val distance: Double = 0.0,
    @ColumnInfo(name = "time") val time: Int = 0,
    @ColumnInfo(name = "date") val date: Long = 0,
    @ColumnInfo(name = "pacing") val pacing: Double = 0.0,
    @ColumnInfo(name = "calories") val calories: Double = 0.0,

    ) {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}

@Entity
data class PointRoomEntity(
    @ColumnInfo(name = "runId") val runId: Int = 0,
    @ColumnInfo(name = "latitude") val latitude: Double = 0.0,
    @ColumnInfo(name = "longitude") val longitude: Double = 0.0,
    @ColumnInfo(name = "time") val time: Long = 0L

) {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0

}
@Entity
data class CurrentRunPointsRoomEntity(
    @ColumnInfo(name = "latitude") val latitude: Double = 0.0,
    @ColumnInfo(name = "longitude") val longitude: Double = 0.0,
    @ColumnInfo(name = "time") val time: Long = 0L

) {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0

}