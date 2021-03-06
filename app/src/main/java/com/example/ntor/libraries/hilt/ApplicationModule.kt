package com.example.ntor.libraries.hilt

import android.content.Context
import androidx.room.Room
import com.example.ntor.core.usecases.currentRun.RunInfoDataAccessImpl
import com.example.ntor.core.usecases.currentRun.RunInfoIOBoundary
import com.example.ntor.core.usecases.currentRun.RunInfoInteractor
import com.example.ntor.libraries.mapbox.MapboxManager
import com.example.ntor.libraries.room.ApplicationDatabase
import com.example.ntor.presentation.main.progress.detail.RunDetailFragmentViewModel
import com.example.ntor.presentation.run.countdown.CountDownAudioPlayer
import com.example.ntor.presentation.run.countdown.CountDownViewModel
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApplicationModule {

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext appContext: Context) = Room.databaseBuilder(
        appContext,
        ApplicationDatabase::class.java, "ntor_database"
    ).build()


    @Singleton
    @Provides
    fun provideCurrentRunFacade(db: ApplicationDatabase) =
        RunInfoDataAccessImpl(db.applicationDao())

    @Singleton
    @Provides
    fun provideCurrentRunInteractor(dataAccessImpl: RunInfoDataAccessImpl) =
        RunInfoInteractor(dataAccessImpl)

    @Provides
    fun provideViewModel() = CountDownViewModel()

    @Singleton
    @Provides
    fun provideCountDownPlayer() = CountDownAudioPlayer()

    @Singleton
    @Provides
    fun provideMapboxManager() = MapboxManager()

    @Provides
    fun provideDetailViewModel(boundary: RunInfoIOBoundary) = RunDetailFragmentViewModel(boundary)



}