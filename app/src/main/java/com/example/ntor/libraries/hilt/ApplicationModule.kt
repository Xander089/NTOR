package com.example.ntor.libraries.hilt

import android.content.Context
import androidx.room.Room
import com.example.ntor.core.usecases.currentRun.RunInfoDataAccessImpl
import com.example.ntor.core.usecases.currentRun.RunInfoInteractor
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
        RunInfoDataAccessImpl(db.applicationDao())

    @Singleton
    @Provides
    fun provideCurrentRunInteractor(dataAccessImpl: RunInfoDataAccessImpl) =
        RunInfoInteractor(dataAccessImpl)


}