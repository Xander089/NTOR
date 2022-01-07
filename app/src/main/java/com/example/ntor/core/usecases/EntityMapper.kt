package com.example.ntor.core.usecases

import com.example.ntor.core.entities.Run
import com.example.ntor.libraries.room.RunRoomEntity

object EntityMapper {

    fun RunRoomEntity.toRun() : Run = Run(
        this.distance,
        this.time,
        this.date
    )

}