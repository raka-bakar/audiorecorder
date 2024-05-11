package com.raka.audiorecorder.data.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "audio_records")
data class AudioRecord(
    val filename:String,
    val filepath:String,
    val duration:String,
){
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}
