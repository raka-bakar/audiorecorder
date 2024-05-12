package com.raka.audiorecorder.di

import com.raka.audiorecorder.domain.timer.Timer
import com.raka.audiorecorder.domain.timer.TimerHelper
import com.raka.audiorecorder.domain.timer.TimerHelperImpl
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
     * provides Timer
     * @param impl of TimerImpl type
     */
    @Binds
    @Singleton
    abstract fun provideTimer(impl: TimerImpl)
            : Timer

}