package com.example.ntor.viewmodels

import com.example.ntor.presentation.DataHelper
import com.example.ntor.presentation.main.progress.list.AllRunsFragmentViewModel
import com.example.ntor.presentation.run.preview.RunCompletedFragmentViewModel
import com.example.ntor.presentation.run.started.RunFragmentViewModel
import org.junit.Before
import org.junit.Test

class RunViewModelTest {

    private lateinit var viewModel: RunFragmentViewModel
    private lateinit var  dataHelper: DataHelper

    @Before
    fun setup() {
        dataHelper = DataHelper()
        viewModel =  RunFragmentViewModel(FakeService(),dataHelper)
    }


    @Test
    fun test() {

    }
}