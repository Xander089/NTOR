package com.example.ntor.useCaseTest

import com.example.ntor.core.entities.Run
import com.example.ntor.core.usecases.currentRun.RunInfoDataAccessInterface
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking


import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito
import org.mockito.kotlin.verify

class RunInfoInteractorTest {


    private fun getFakeRunFlow() =
        flow {
            val run = Run(0.0, 0, 0, 0.0, 0.0)
            emit(run)
        }

    private val dataAccess = Mockito.mock(RunInfoDataAccessInterface::class.java)

    @Before
    fun setUp() {
    }

    @After
    fun tearDown() {
    }

    @Test
    fun test()  = runBlocking{
        /* Given */
        Mockito.`when`(dataAccess.getLatestRun()).thenReturn(getFakeRunFlow())

        /* When */
        val actual = dataAccess.getLatestRun()
        verify(dataAccess).getLatestRun()

        /* Then */
        assertEquals(0.0, actual.first().distance, 0.0)
    }

    @Test
    fun getLatestRun() {
    }

    @Test
    fun getLatestRunId() {
    }

    @Test
    fun insertRun() {
    }

    @Test
    fun getRunsAsFlow() {
    }

    @Test
    fun getRunByTime() {
    }

    @Test
    fun deleteRunById() {
    }

    @Test
    fun getPointByTime() {
    }

    @Test
    fun getPointsById() {
    }

    @Test
    fun getPointsByIdAsFlow() {
    }

    @Test
    fun deletePointsById() {
    }

    @Test
    fun insertPoint() {
    }

    @Test
    fun getTempPoints() {
    }

    @Test
    fun deleteTempPoints() {
    }

    @Test
    fun insertCurrentPoint() {
    }
}