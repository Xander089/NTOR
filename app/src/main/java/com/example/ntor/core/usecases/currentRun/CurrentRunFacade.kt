package com.example.ntor.core.usecases.currentRun

import com.example.ntor.core.entities.Run
import com.example.ntor.core.usecases.EntityMapper.toRun
import com.example.ntor.libraries.room.ApplicationDao
import com.example.ntor.libraries.room.RunRoomEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.runBlocking
import java.util.*

class CurrentRunFacade(private val dao: ApplicationDao) {


    fun getLatestRun(): Flow<Run> = dao.getLatestRun().map { it.toRun() }

}