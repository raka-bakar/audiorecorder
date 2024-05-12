package com.raka.audiorecorder.ui.main

import java.text.SimpleDateFormat
import java.util.Date
import javax.inject.Inject

/**
 * class that provides helper functions for Main View Model
 */
interface MainHelper {
    /**
     * get current date
     */
    fun getDate(): String

    /**
     * get file name of the audio file using current timestamp
     * @return String of file name
     */
    fun getFileName(date: String): String

    /**
     * get recording duration in correct format
     * @param duration of type String
     * @return formatted recording duration
     */
    fun getRecordingDuration(duration: String): String
}

class MainHelperImpl @Inject constructor() : MainHelper {
    override fun getDate(): String {
        return SimpleDateFormat("yyyy.MM.DD_HH.mm.ss").format(Date())
    }

    override fun getFileName(date: String): String {
        return "audio_record_$date.mp3"
    }

    override fun getRecordingDuration(duration: String): String {
        return duration.dropLast(3)
    }
}