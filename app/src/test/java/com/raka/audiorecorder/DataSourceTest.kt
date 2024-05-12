package com.raka.audiorecorder

import com.raka.audiorecorder.data.DataSource
import com.raka.audiorecorder.data.DataSourceImpl
import com.raka.audiorecorder.data.database.AudioRecord
import com.raka.audiorecorder.data.database.AudioRecordDao
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito
import org.mockito.kotlin.verify


class DataSourceTest {
    private val dao = Mockito.mock(AudioRecordDao::class.java)
    // sut = system under test
    private lateinit var sut: DataSource

    private val testData = AudioRecord(
        filename = "filename", filepath = "file://filepath",
        duration = "00:00:01"
    )

    @Before
    fun setup() {
        sut = DataSourceImpl(dao)
    }

    @Test
    fun `when the correct dao method is called when getting audio records`(): Unit = runBlocking {
        val expected = listOf(testData)
        Mockito.`when`(dao.getAudioRecords()).thenReturn(flow { emit(expected) })

        sut.getAudioRecords()
        verify(dao).getAudioRecords()
    }

    @Test
    fun `verify the correct audio record is sent when deleting an audio record `(): Unit =
        runBlocking {
            sut.deleteAudioRecord(testData)
            verify(dao).deleteAudioRecord(testData)
        }
}