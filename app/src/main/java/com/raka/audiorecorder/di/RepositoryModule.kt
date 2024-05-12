package com.raka.audiorecorder.di

import com.raka.audiorecorder.data.AudioRecordRepository
import com.raka.audiorecorder.data.AudioRecordRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Dagger providers for Repository components
 */
@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    /**
     * provides AudioRecordRepository
     * @param impl of AudioRecordRepositoryImpl type
     */
    @Binds
    @Singleton
    abstract fun provideAudioRecordRepository(impl: AudioRecordRepositoryImpl)
            : AudioRecordRepository
}