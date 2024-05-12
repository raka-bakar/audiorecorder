package com.raka.audiorecorder

import com.raka.audiorecorder.data.AudioRecordRepository
import com.raka.audiorecorder.data.AudioRecordRepositoryImpl
import com.raka.audiorecorder.data.DataSource
import com.raka.audiorecorder.data.database.AudioRecord
import com.raka.audiorecorder.utils.CallState
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito
import org.mockito.kotlin.verify

class AudioRecordRepositoryTest {
    private val dataSource = Mockito.mock(DataSource::class.java)
    private val fileName = "filename"
    private val filePath = "file://filepath"
    private val duration = "00:00:01"
    private val expected = AudioRecord(
        filename = fileName, filepath = filePath,
        duration = duration
    )
    // sut = system under test
    private lateinit var sut: AudioRecordRepository

    @Before
    fun setup() {
        sut = AudioRecordRepositoryImpl(
            dataSource = dataSource
        )
    }

    @Test
    fun `verify the correct audio data from the database`() = runBlocking {
        Mockito.`when`(
            dataSource.getAudioRecords()
        ).thenReturn(flow { emit(listOf(expected)) })

        val result = sut.getAudioRecords()

        result.collect {
            Assert.assertEquals(expected, it.data?.get(0))
        }
    }

    @Test
    fun `when data is empty, return call state error`() = runBlocking {
        val expected = CallState.Status.ERROR

        Mockito.`when`(
            dataSource.getAudioRecords()
        ).thenReturn(flow { emit(listOf()) })

        val result = sut.getAudioRecords()
        result.collect {
            Assert.assertEquals(expected, it.status)
        }
    }

    @Test
    fun `verify the correct data is sent to database when adding a new audio record`() =
        runBlocking {
            sut.addAudioRecord(filePath = filePath, fileName = fileName, duration = duration)
            verify(dataSource).addAudioRecord(expected)
        }

    @Test
    fun `verify the correct data is sent when deleting an audio record`(): Unit = runBlocking {
        sut.deleteAudioRecord(expected)
        verify(dataSource).deleteAudioRecord(expected)
        verify(dataSource).deleteFileAudioRecord(expected)
    }
}