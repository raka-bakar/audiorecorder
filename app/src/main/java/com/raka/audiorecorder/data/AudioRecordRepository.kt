package com.raka.audiorecorder.data

import com.raka.audiorecorder.data.database.AudioRecord
import com.raka.audiorecorder.utils.CallState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

/**
 * Repository to provide Audio Record data
 */
interface AudioRecordRepository {
    /**
     * get a list of AudioRecordCompact from database
     * @return Flow of list of AudioRecordCompact type
     */
    fun getAudioRecords(): Flow<CallState<List<AudioRecord>>>

    /**
     * add a new Audio Record to the database
     * @param audioRecord of AudioRecord type
     */
    suspend fun addAudioRecord(audioRecord: AudioRecord)

    /**
     * delete an Audio Record from database
     * @param audioRecord of AudioRecord type
     */
    suspend fun deleteAudioRecord(audioRecord: AudioRecord)
}

class AudioRecordRepositoryImpl @Inject constructor(private val dataSource: DataSource) :
    AudioRecordRepository {
    override fun getAudioRecords(): Flow<CallState<List<AudioRecord>>> {
        return dataSource.getAudioRecords().map { list ->
            if (list.isEmpty()) {
                CallState.error(message = "Data is empty")
            } else {
                CallState.success(data = list)
            }
        }
    }

    override suspend fun addAudioRecord(audioRecord: AudioRecord) {
        dataSource.addAudioRecord(audioRecord = audioRecord)
    }

    override suspend fun deleteAudioRecord(audioRecord: AudioRecord) {
        dataSource.deleteFileAudioRecord(audioRecord = audioRecord)
        dataSource.deleteAudioRecord(audioRecord)
    }
}