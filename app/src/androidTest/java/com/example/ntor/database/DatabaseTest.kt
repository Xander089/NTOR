package com.example.ntor.database

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.ntor.libraries.room.ApplicationDao
import com.example.ntor.libraries.room.ApplicationDatabase
import com.example.ntor.libraries.room.RunRoomEntity
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class DatabaseTest {

    private lateinit var dao: ApplicationDao
    private lateinit var db: ApplicationDatabase

    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(
            context, ApplicationDatabase::class.java
        ).build()
        dao = db.applicationDao()
    }


    @After
    fun tearDown() {
        db.close()
    }

    @Test
    fun test() = runBlocking {
        val entity = RunRoomEntity(0.0,0,0L,0.0,0.0)
        dao.insertRun(entity)
        val returnedEntity = dao.getLatestRunInNonUIThread()
        dao.deleteRunByDate(returnedEntity.date)
        assert(returnedEntity.calories.equals(0.0))
    }

}