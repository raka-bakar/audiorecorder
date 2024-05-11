package com.raka.audiorecorder.data.model


data class AudioRecordCompact(
    var id: Int,
    var filename: String,
    var filepath: String,
    var timestamp: Long,
    var duration: String,
)
