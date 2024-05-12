package com.raka.audiorecorder.ui.main

import com.raka.audiorecorder.utils.Constants
import java.text.SimpleDateFormat
import java.util.Date
import javax.inject.Inject

/**
 * class that provides helper functions for MainViewModel
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
        return "${Constants.AUDIO_FILE_NAME_FORMAT}$date${Constants.AUDIO_FILE_FORMAT}"
    }

    override fun getRecordingDuration(duration: String): String {
        return duration.dropLast(DROP_LAST_DIGITS)
    }

    companion object {
        private const val DROP_LAST_DIGITS = 3
    }
}