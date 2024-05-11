package com.raka.audiorecorder.data

import com.raka.audiorecorder.data.database.AudioRecord
import com.raka.audiorecorder.data.database.AudioRecordDao
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import timber.log.Timber
import java.io.File
import javax.inject.Inject

/**
 * DataSource to handle data transaction from or to a local database or local file
 */
interface DataSource {

    /**
     * get a list of DBAudioRecord from database,
     * @return Flow of list of DBAudioRecord type
     */
    fun getAudioRecords(): Flow<List<AudioRecord>>

    /**
     * add a new DBAudioRecord to the database
     * @param audioRecord of DBAudioRecord type
     */
    suspend fun addAudioRecord(audioRecord:AudioRecord)

    /**
     * delete an AudioRecord from database
     * @param audioRecord of AudioRecord type
     */
    suspend fun deleteAudioRecord(audioRecord:AudioRecord)

    /**
     * delete a recording audio file from local storage
     * @param audioRecord of AudioRecord type
     */
    suspend fun deleteFileAudioRecord(audioRecord:AudioRecord)
}

class DataSourceImpl @Inject constructor(private val audioRecordDao: AudioRecordDao): DataSource{
    override fun getAudioRecords(): Flow<List<AudioRecord>> = flow {
        coroutineScope {
            emit(audioRecordDao.getAudioRecords())
        }
    }

    override suspend fun addAudioRecord(audioRecord: AudioRecord) {
        audioRecordDao.addAudioRecord(item = audioRecord)
    }

    override suspend fun deleteAudioRecord(audioRecord: AudioRecord) {
        audioRecordDao.deleteAudioRecord(item = audioRecord)
    }

    override suspend fun deleteFileAudioRecord(audioRecord: AudioRecord) {
        val file = File(audioRecord.filepath, audioRecord.filename)
        try {
            if (file.exists()){
                file.delete()
            }
        }catch (e: Exception){
            Timber.e(e)
        }
    }
}