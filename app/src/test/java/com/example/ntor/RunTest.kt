package com.example.ntor

import com.example.ntor.core.entities.Run
import com.example.ntor.presentation.run.started.RunFragmentViewModel
import org.junit.Assert
import org.junit.Test

class RunTest {

    private fun createRun(
        distance: Double,
        time: Int
    ) = Run(
        distance = distance,
        time = time
    )

    @Test
    fun `when distance is 100 m and time is 10 seconds then avgSpeed is 10`() {
        val run = createRun(100.0,10)
        Assert.assertEquals(10.0, run.avgSpeed)
    }


}