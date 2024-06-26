package com.raka.audiorecorder

import com.raka.audiorecorder.data.AudioRecordRepository
import com.raka.audiorecorder.data.database.AudioRecord
import com.raka.audiorecorder.domain.audio.AudioPlayer
import com.raka.audiorecorder.ui.recordings.RecordingsHelper
import com.raka.audiorecorder.ui.recordings.RecordingsViewModel
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito
import org.mockito.kotlin.verify

class RecordingsViewModelTest {
    private val audioRecordRepository = Mockito.mock(AudioRecordRepository::class.java)
    private val audioPlayer = Mockito.mock(AudioPlayer::class.java)
    private val recordingsHelper = Mockito.mock(RecordingsHelper::class.java)

    // sut = system under test
    private lateinit var sut: RecordingsViewModel
    private val testData = AudioRecord(
        filename = "filename", filepath = "file://filepath",
        duration = "00:00:01"
    )

    @Before
    fun setup() {
        sut = RecordingsViewModel(
            audioRecordRepository = audioRecordRepository,
            audioPlayer = audioPlayer,
            recordingsHelper = recordingsHelper
        )
    }

    @Test
    fun `when click on the same item, stop the current audio being played`() {
        Mockito.`when`(recordingsHelper.isSameAudio(testData.filename)).thenReturn(true)
        sut.onItemClicked(testData)
        verify(audioPlayer).stop()
    }

    @Test
    fun `when click on the different item while an audio is being played, play the next audio file`() {
        Mockito.`when`(recordingsHelper.isSameAudio(testData.filename)).thenReturn(false)
        sut.onItemClicked(
            AudioRecord(
                filename = "Anotherfilename", filepath = "file://filepath1",
                duration = "00:00:03"
            )
        )
        verify(audioPlayer).stop()
        verify(audioPlayer).playAudioFile(
            directoryPath = "file://filepath1",
            fileName = "Anotherfilename"
        )
    }

    @Test
    fun `verify the correct audio record data is sent when deleting an audio record`() =
        runBlocking {
            sut.deleteAudio(testData)
            verify(audioRecordRepository).deleteAudioRecord(testData)
        }

    @Test
    fun `verify audioplayer stop is called`() {
        sut.stopAudio()
        verify(audioPlayer).stop()
    }
}