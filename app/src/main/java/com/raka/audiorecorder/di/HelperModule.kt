package com.raka.audiorecorder.di

import com.raka.audiorecorder.domain.timer.TimerHelper
import com.raka.audiorecorder.domain.timer.TimerHelperImpl
import com.raka.audiorecorder.ui.main.MainHelper
import com.raka.audiorecorder.ui.main.MainHelperImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Dagger providers for Helper components
 */
@Module
@InstallIn(SingletonComponent::class)
abstract class HelperModule {

    /**
     * provides TimerHelper
     * @param impl of TimerHelperImpl type
     */
    @Binds
    @Singleton
    abstract fun provideTimerHelper(impl: TimerHelperImpl)
            : TimerHelper

    /**
     * provides MainHelper
     * @param impl of MainHelperImpl type
     */
    @Binds
    @Singleton
    abstract fun provideMainHelper(impl: MainHelperImpl)
            : MainHelper
}