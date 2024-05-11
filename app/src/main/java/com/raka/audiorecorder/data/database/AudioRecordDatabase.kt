package com.raka.audiorecorder.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.raka.audiorecorder.utils.Constants.DB_VERSION

@Database(entities = [AudioRecord::class], version = DB_VERSION, exportSchema = false)
abstract class AudioRecordDatabase : RoomDatabase() {
    abstract fun audioRecordDao(): AudioRecordDao
}