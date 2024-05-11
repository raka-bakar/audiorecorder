package com.raka.audiorecorder.di

import android.content.Context
import com.raka.audiorecorder.domain.audio.AudioPlayer
import com.raka.audiorecorder.domain.audio.AudioPlayerImpl
import com.raka.audiorecorder.domain.audio.AudioRecorder
import com.raka.audiorecorder.domain.audio.AudioRecorderImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Dagger providers for Audio components
 */
@Module
@InstallIn(SingletonComponent::class)
class AudioModule {

    /**
     * provides AudioPlayer
     * @param context
     */
    @Singleton
    @Provides
    fun provideAudioPlayer(@ApplicationContext context: Context): AudioPlayer {
        return AudioPlayerImpl(context)
    }

    /**
     * provides AudioRecorder
     * @param context
     */
    @Singleton
    @Provides
    fun provideAudioRecorder(@ApplicationContext context: Context): AudioRecorder {
        return AudioRecorderImpl(context)
    }
}