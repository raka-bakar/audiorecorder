package com.raka.audiorecorder.ui.main

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.raka.audiorecorder.data.AudioRecordRepository
import com.raka.audiorecorder.data.database.AudioRecord
import com.raka.audiorecorder.domain.audio.AudioRecorder
import com.raka.audiorecorder.domain.timer.Timer
import com.raka.audiorecorder.utils.CallState
import com.raka.audiorecorder.utils.RefreshFlow
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.io.File
import java.text.SimpleDateFormat
import java.util.Date
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val audioRecordRepository: AudioRecordRepository,
    private val timer: Timer,
    private val audioRecorder: AudioRecorder
) : ViewModel() {

    private var directoryPath: String = ""
    private var fileName: String = ""
    val ticker = timer.ticker

    private var _isRecording = MutableStateFlow(false)
    val isRecording : StateFlow<Boolean> = _isRecording

    fun startRecording(path: String) {
        _isRecording.value = true
        directoryPath = path
        fileName = getFileName()
        audioRecorder.start(dirPath = directoryPath, fileName = fileName)
        timer.startTimer()
    }

    fun stopRecording(duration:String){
        audioRecorder.stop()
        timer.stopTimer()
        val recordingDuration = duration.dropLast(3)
        val audioRecord = AudioRecord(filename = fileName,
            duration = recordingDuration,
            filepath = directoryPath)
        viewModelScope.launch {
            audioRecordRepository.addAudioRecord(audioRecord = audioRecord)
        }
        _isRecording.value = false
    }

    /**
     * get file name of the audio file using current timestamp
     * @return String of file name
     */
    private fun getFileName(): String {
        val simpleDateFormat = SimpleDateFormat("yyyy.MM.DD_HH.mm.ss")
        val date = simpleDateFormat.format(Date())
        val fileName = "audio_record_$date.mp3"
        return fileName
    }
}