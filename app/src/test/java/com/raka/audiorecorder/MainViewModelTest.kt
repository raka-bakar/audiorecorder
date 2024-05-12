package com.raka.audiorecorder

import com.raka.audiorecorder.data.AudioRecordRepository
import com.raka.audiorecorder.domain.audio.AudioRecorder
import com.raka.audiorecorder.domain.timer.Timer
import com.raka.audiorecorder.ui.main.MainHelper
import com.raka.audiorecorder.ui.main.MainViewModel
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito
import org.mockito.kotlin.verify

class MainViewModelTest {
    private val audioRecordRepository = Mockito.mock(AudioRecordRepository::class.java)
    private val timer = Mockito.mock(Timer::class.java)
    private val audioRecorder = Mockito.mock(AudioRecorder::class.java)
    private val mainHelper = Mockito.mock(MainHelper::class.java)
    // System under test
    private lateinit var sut: MainViewModel
    private val testedPath = "path://"
    private val filename = "audio_record_2024.05.133_11.39.40.mp3"
    private val duration = "00:10:10"
    @Before
    fun setup(){
        sut = MainViewModel(audioRecordRepository, timer, audioRecorder, mainHelper)
    }

    @Test
    fun `verify the correct filename and directory path are sent to the audio recorder`(){
        val date = "2024.05.133_11.39"
        Mockito.`when`(mainHelper.getDate()).thenReturn(date)
        Mockito.`when`(mainHelper.getFileName(date)).thenReturn(filename)

        sut.startRecording(testedPath)
        verify(audioRecorder).start(testedPath,filename)
    }

    @Test
    fun `verify start timer is called when starting recording process`(){
        sut.startRecording(testedPath)
        verify(timer).startTimer()
    }

    @Test
    fun `verify the correct duration is sent to the Audio Record Repository`() = runBlocking{

        Mockito.`when`(mainHelper.getRecordingDuration(duration)).thenReturn(duration)

        sut.stopRecording(duration)
        verify(audioRecordRepository)
            .addAudioRecord(fileName ="", duration = duration, filePath = "")
    }
    @Test
    fun `verify audio recorder and timer are called when stopping a recorder`(){
        sut.stopRecording(duration)
        verify(timer).stopTimer()
        verify(audioRecorder).stop()
    }
}