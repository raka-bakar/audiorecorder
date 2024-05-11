package com.raka.audiorecorder

import com.raka.audiorecorder.data.DataSource
import com.raka.audiorecorder.data.DataSourceImpl
import com.raka.audiorecorder.data.database.AudioRecordDao
import org.junit.Before
import org.mockito.Mockito

class DataSourceTest {
    private val dao = Mockito.mock(AudioRecordDao::class.java)

    private lateinit var sut : DataSource

    @Before
    fun setup(){
        sut = DataSourceImpl(dao)
    }


}