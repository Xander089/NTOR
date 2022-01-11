package com.example.ntor

import android.content.Context
import androidx.room.Room
import androidx.test.filters.SmallTest
import com.example.ntor.core.usecases.currentRun.RunInfoDataAccessImpl
import com.example.ntor.libraries.hilt.ApplicationModule
import com.example.ntor.libraries.room.ApplicationDao
import com.example.ntor.libraries.room.ApplicationDatabase
import com.example.ntor.libraries.room.RunRoomEntity
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject
import javax.inject.Named
import javax.inject.Singleton

@ExperimentalCoroutinesApi
@SmallTest
@UninstallModules(ApplicationModule::class)
@HiltAndroidTest
class DatabaseTest {

    @Module
    @InstallIn(SingletonComponent::class)
    object TestApplicationModule {

        @Provides
        @Named("test_db")
        fun provideDatabase(@ApplicationContext appContext: Context) = Room.databaseBuilder(
            appContext,
            ApplicationDatabase::class.java, "ntor_database"
        ).build()

        @Singleton
        @Provides
        fun provideCurrentDao(db: ApplicationDatabase) =
            db.applicationDao()


    }

    @get:Rule
    val hiltRule = HiltAndroidRule(this)

    @Inject
    @Named("test_db")
    lateinit var db: ApplicationDatabase
    private lateinit var dao: ApplicationDao

    @Before
    fun setup() {
        hiltRule.inject()
//        dao = db.applicationDao()
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
        assert(returnedEntity.calories.equals(0.0))
    }

}