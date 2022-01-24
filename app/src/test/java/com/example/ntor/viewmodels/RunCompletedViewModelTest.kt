package com.example.ntor.viewmodels

import com.example.ntor.presentation.utils.DataHelper
import com.example.ntor.presentation.run.preview.RunCompletedFragmentViewModel
import org.junit.Before
import org.junit.Test

class RunCompletedViewModelTest {

    private lateinit var viewModel: RunCompletedFragmentViewModel
    private lateinit var  dataHelper: DataHelper
    @Before
    fun setup() {
        dataHelper = DataHelper()
        viewModel =  RunCompletedFragmentViewModel(FakeService(), dataHelper)
    }


    @Test
    fun test() {

    }
}