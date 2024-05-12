package com.raka.audiorecorder.di

import android.content.Context
import com.raka.audiorecorder.AudioRecorderApp
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Dagger providers for App components
 */
@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    /**
     * provides AudioRecorderApp
     * @param context
     */
    @Singleton
    @Provides
    fun providesApplication(@ApplicationContext context: Context): AudioRecorderApp =
        context as AudioRecorderApp
}