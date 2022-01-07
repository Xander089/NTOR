package com.example.ntor.libraries.hilt

import android.content.Context
import androidx.room.Room
import com.example.ntor.core.usecases.currentRun.CurrentRunFacade
import com.example.ntor.core.usecases.currentRun.CurrentRunInteractor
import com.example.ntor.core.usecases.showPastRunInfo.RunInfoFacade
import com.example.ntor.core.usecases.showPastRunInfo.RunInfoInteractor
import com.example.ntor.libraries.room.ApplicationDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ApplicationModule {

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext appContext: Context) = Room.databaseBuilder(
        appContext,
        ApplicationDatabase::class.java, "ntor_database"
    ).build()

    @Singleton
    @Provides
    fun provideCurrentRunFacade(db: ApplicationDatabase) =
        CurrentRunFacade(db.applicationDao())

    @Singleton
    @Provides
    fun provideCurrentRunInteractor(facade: CurrentRunFacade) =
        CurrentRunInteractor(facade)

    @Singleton
    @Provides
    fun provideRunInfoFacade(db: ApplicationDatabase) =
        RunInfoFacade(db.applicationDao())

    @Singleton
    @Provides
    fun provideRunInfoInteractor(facade: RunInfoFacade) =
        RunInfoInteractor(facade)



}