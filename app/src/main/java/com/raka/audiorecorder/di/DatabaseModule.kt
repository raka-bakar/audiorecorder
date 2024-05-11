package com.raka.audiorecorder.di

import android.content.Context
import androidx.room.Room
import com.raka.audiorecorder.data.database.AudioRecordDao
import com.raka.audiorecorder.data.database.AudioRecordDatabase
import com.raka.audiorecorder.utils.Constants.DB_NAME
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Dagger providers for Database components
 */
@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {
    /**
     * provides room database instance
     * @param context
     */
    @Singleton
    @Provides
    fun providesRoomDatabase(@ApplicationContext context: Context): AudioRecordDatabase {
        return Room.databaseBuilder(
            context,
            AudioRecordDatabase::class.java,
            DB_NAME
        ).build()
    }

    /**
     * provides room AudioRecordDao
     * @param db of AudioRecordDatabase type
     */
    @Provides
    @Singleton
    fun provideMovieBookmarkDao(db: AudioRecordDatabase): AudioRecordDao {
        return db.audioRecordDao()
    }
}