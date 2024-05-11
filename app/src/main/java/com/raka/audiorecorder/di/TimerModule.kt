package com.raka.audiorecorder.di

import com.raka.audiorecorder.domain.timer.Timer
import com.raka.audiorecorder.domain.timer.TimerImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Dagger providers for Timer components
 */
@Module
@InstallIn(SingletonComponent::class)
abstract class TimerModule {
    /**
     * provides TimerUseCase
     * @param impl of TimerImpl type
     */
    @Binds
    @Singleton
    abstract fun provideTimerUseCase(impl: TimerImpl)
            : Timer
}