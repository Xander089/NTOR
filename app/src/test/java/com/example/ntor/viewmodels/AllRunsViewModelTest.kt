package com.example.ntor.viewmodels

import com.example.ntor.core.entities.Run
import com.example.ntor.presentation.main.progress.list.AllRunsFragmentViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class AllRunsViewModelTest {

    private lateinit var viewModel: AllRunsFragmentViewModel

    @Before
    fun setup() {
        viewModel =  AllRunsFragmentViewModel(FakeService())
    }


    @Test
    fun `when fake service has only one instance with all variables zeros then the view model returns it`() = runBlocking{
        val runs = viewModel.getRuns().first()
        assert(runs == listOf(Run(0.0, 0, 0L, 0.0)))
    }
}