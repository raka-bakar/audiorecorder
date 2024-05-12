package com.raka.audiorecorder.di

import com.raka.audiorecorder.data.DataSource
import com.raka.audiorecorder.data.DataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Dagger providers for DataSource components
 */
@Module
@InstallIn(SingletonComponent::class)
abstract class DataSourceModule {
    /**
     * provides DataSource
     * @param impl of DataSourceImpl type
     */
    @Binds
    @Singleton
    abstract fun provideDataSource(impl: DataSourceImpl): DataSource
}