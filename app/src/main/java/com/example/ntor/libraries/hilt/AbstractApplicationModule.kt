package com.example.ntor.libraries.hilt

import com.example.ntor.core.usecases.currentRun.CurrentRunBoundaryService
import com.example.ntor.core.usecases.currentRun.CurrentRunInteractor
import com.example.ntor.core.usecases.showPastRunInfo.RunInfoBoundaryService
import com.example.ntor.core.usecases.showPastRunInfo.RunInfoInteractor
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class AbstractApplicationModule {

    @Singleton
    @Binds
    abstract fun bindRunInfoBoundary(interactor: RunInfoInteractor): RunInfoBoundaryService

    @Singleton
    @Binds
    abstract fun bindCurrentRunBoundary(interactor: CurrentRunInteractor): CurrentRunBoundaryService

}