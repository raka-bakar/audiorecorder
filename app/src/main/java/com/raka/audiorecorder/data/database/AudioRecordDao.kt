package com.raka.audiorecorder.data.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

/**
 * Room Dao interface, provides methods for persisting Audio Record
 */
@Dao
interface AudioRecordDao {

    /**
     * get a list of Audio Record from the database
     * @return List<AudioRecord>
     */
    @Query("SELECT * FROM audio_records")
    fun getAudioRecords(): Flow<List<AudioRecord>>

    /**
     * add a new AudioRecord to the database
     * @param item of AudioRecord
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addAudioRecord(item: AudioRecord)

    /**
     * delete an existing AudioRecord in the database
     * @param item of AudioRecord
     */
    @Delete
    suspend fun deleteAudioRecord(item: AudioRecord)
}