package com.raka.audiorecorder.ui.recordings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.raka.audiorecorder.data.AudioRecordRepository
import com.raka.audiorecorder.data.database.AudioRecord
import com.raka.audiorecorder.domain.audio.AudioPlayer
import com.raka.audiorecorder.utils.CallState
import com.raka.audiorecorder.utils.RefreshFlow
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RecordingsViewModel @Inject constructor(
    private val audioRecordRepository: AudioRecordRepository,
    private val audioPlayer: AudioPlayer
) : ViewModel() {

    val recordingList = RefreshFlow {
        audioRecordRepository.getAudioRecords()
            .stateIn(viewModelScope, SharingStarted.Eagerly, CallState.loading())
    }

    private var isPlaying = false

    fun onItemClicked(item: AudioRecord) {
        if (isPlaying) {
            stopAudio()
        } else {
            playAudio(directoryPath = item.filepath, fileName = item.filename)
        }
    }


    private fun playAudio(directoryPath: String, fileName:String){
        audioPlayer.stop()
        isPlaying = true
        audioPlayer.playAudioFile(directoryPath = directoryPath, fileName = fileName)
    }

    fun stopAudio() {
        isPlaying = false
        audioPlayer.stop()
    }

    fun deleteAudio(audioRecord: AudioRecord) {
        stopAudio()
        viewModelScope.launch {
            audioRecordRepository.deleteAudioRecord(audioRecord)
            recordingList.refresh()
        }
    }
}