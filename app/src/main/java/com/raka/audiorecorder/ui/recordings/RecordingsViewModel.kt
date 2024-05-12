package com.raka.audiorecorder.ui.recordings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.raka.audiorecorder.data.AudioRecordRepository
import com.raka.audiorecorder.data.database.AudioRecord
import com.raka.audiorecorder.domain.audio.AudioPlayer
import com.raka.audiorecorder.utils.CallState
import com.raka.audiorecorder.utils.RefreshFlow
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
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

    /**
     * Handling item clicked and check if there is an audio is being played
     * @param item of type AudioRecord
     */
    fun onItemClicked(item: AudioRecord) {
        if (isPlaying) {
            stopAudio()
        } else {
            playAudio(directoryPath = item.filepath, fileName = item.filename)
        }
    }

    /**
     * To play an audio record,any previous audio being played is stopped
     * then set status isPlaying, then call audioPlayer
     * @param directoryPath of type String
     * @param fileName of type String
     */
    private fun playAudio(directoryPath: String, fileName: String) {
        audioPlayer.stop()
        isPlaying = true
        audioPlayer.playAudioFile(directoryPath = directoryPath, fileName = fileName)
    }

    /**
     * Stop an audioplayer and update isPlaying status
     * then set status isPlaying, then call audioPlayer
     */
    fun stopAudio() {
        isPlaying = false
        audioPlayer.stop()
    }

    /**
     * delete an audio record data from local storage and database
     * any audio record being played will be stopped first before delete process
     * then refresh the list
     * @param audioRecord of type AudioRecord
     */
    fun deleteAudio(audioRecord: AudioRecord) {
        stopAudio()
        viewModelScope.launch(Dispatchers.IO) {
            audioRecordRepository.deleteAudioRecord(audioRecord)
            recordingList.refresh()
        }
    }
}