package com.raka.audiorecorder.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.raka.audiorecorder.data.AudioRecordRepository
import com.raka.audiorecorder.domain.audio.AudioRecorder
import com.raka.audiorecorder.domain.timer.Timer
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val audioRecordRepository: AudioRecordRepository,
    private val timer: Timer,
    private val audioRecorder: AudioRecorder,
    private val mainHelper: MainHelper
) : ViewModel() {

    private var directoryPath: String = ""
    private var fileName: String = ""
    val ticker = timer.ticker

    /**
     * start audio recording by calling audio recorder and starting the timer
     * @param path directory path which the audio record will be saved
     */
    fun startRecording(path: String) {
        directoryPath = path
        val date = mainHelper.getDate()
        fileName = mainHelper.getFileName(date)
        audioRecorder.start(dirPath = directoryPath, fileName = fileName)
        timer.startTimer()
    }

    /**
     * stop audio recording by stopping audio recorder and the timer
     * then saved the audio to the local databse
     * @param duration of the recorded audio type String
     */
    fun stopRecording(duration: String) {
        audioRecorder.stop()
        timer.stopTimer()
        val formattedDuration = mainHelper.getRecordingDuration(duration)
        viewModelScope.launch(Dispatchers.IO) {
            audioRecordRepository.addAudioRecord(
                fileName = fileName,
                duration = formattedDuration,
                filePath = directoryPath
            )
        }
    }
}