package com.example.ntor.libraries.hilt

import com.example.ntor.core.usecases.currentRun.RunInfoDataAccessImpl
import com.example.ntor.core.usecases.currentRun.RunInfoDataAccessInterface
import com.example.ntor.core.usecases.currentRun.RunInfoIOBoundary
import com.example.ntor.core.usecases.currentRun.RunInfoInteractor
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
    abstract fun bindCurrentRunBoundary(interactor: RunInfoInteractor): RunInfoIOBoundary

    @Singleton
    @Binds
    abstract fun bindCurrentRunDataAccessInterface(dataAccessImpl: RunInfoDataAccessImpl): RunInfoDataAccessInterface

}