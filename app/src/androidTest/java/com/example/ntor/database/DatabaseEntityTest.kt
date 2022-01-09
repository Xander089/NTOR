package com.example.ntor.database

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.example.ntor.libraries.room.ApplicationDao
import com.example.ntor.libraries.room.ApplicationDatabase
import com.example.ntor.libraries.room.PointRoomEntity
import com.example.ntor.presentation.run.started.RunFragmentViewModel
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.After

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*
import org.junit.Before
import java.io.IOException
import java.util.*
import java.util.concurrent.Executors

@ExperimentalCoroutinesApi
@SmallTest
@HiltAndroidTest
class DatabaseEntityTest {

    private lateinit var dao: ApplicationDao
    private lateinit var db: ApplicationDatabase

    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(
            context, ApplicationDatabase::class.java
        ).setTransactionExecutor(Executors.newSingleThreadExecutor())
            .build()
        dao = db.applicationDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Test
    fun test_get_point_by_time() = runBlocking {
        val time = Date().time
        val point = PointRoomEntity(
            latitude = 45.0,
            longitude = 7.0,
            time = time
        )
        dao.insertPoint(point)
        val readPoint = dao.getPointByTime(time)

        assertEquals(point,readPoint)

    }
}