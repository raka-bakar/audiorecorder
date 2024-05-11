package com.raka.audiorecorder.di

import android.content.Context
import com.raka.audiorecorder.AudioRecorderApp
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Singleton
    @Provides
    fun providesApplication(@ApplicationContext context: Context): AudioRecorderApp =
        context as AudioRecorderApp
}