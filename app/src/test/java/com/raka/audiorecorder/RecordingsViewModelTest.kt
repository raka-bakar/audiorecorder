package com.raka.audiorecorder

import com.raka.audiorecorder.data.AudioRecordRepository
import com.raka.audiorecorder.domain.audio.AudioPlayer
import com.raka.audiorecorder.ui.recordings.RecordingsViewModel
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito

class RecordingsViewModelTest {
    private val audioRecordRepository = Mockito.mock(AudioRecordRepository::class.java)
    private val audioPlayer = Mockito.mock(AudioPlayer::class.java)

    private lateinit var sut : RecordingsViewModel

    @Before
    fun setup() {
        sut = RecordingsViewModel(
            audioRecordRepository = audioRecordRepository,
            audioPlayer = audioPlayer
        )
    }
    @Test
    fun `fetch the right data when CallResult is success`(){

    }
}