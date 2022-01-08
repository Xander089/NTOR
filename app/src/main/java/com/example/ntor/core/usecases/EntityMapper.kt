package com.example.ntor.core.usecases

import com.example.ntor.core.entities.Point
import com.example.ntor.core.entities.Run
import com.example.ntor.libraries.room.CurrentRunPointsRoomEntity
import com.example.ntor.libraries.room.PointRoomEntity
import com.example.ntor.libraries.room.RunRoomEntity

object EntityMapper {

    fun RunRoomEntity.toRun(): Run = Run(
        this.distance,
        this.time,
        this.date
    )

    fun PointRoomEntity.toPoint(): Point = Point(
        this.latitude,
        this.longitude,
        this.time
    )

    fun Run.toRunRoomEntity(): RunRoomEntity = RunRoomEntity(
        this.distance,
        this.time,
        this.date
    )

    fun Point.toPointRoomEntity(runId: Int): PointRoomEntity = PointRoomEntity(
        runId,
        this.latitude,
        this.longitude,
        this.time
    )


    fun Point.toCurrentRunPointsRoomEntity(): CurrentRunPointsRoomEntity = CurrentRunPointsRoomEntity(
        this.latitude,
        this.longitude,
        this.time
    )

    fun CurrentRunPointsRoomEntity.toPoint(): Point = Point(
        this.latitude,
        this.longitude,
        this.time
    )

}