package com.raka.audiorecorder.ui.recordings

import javax.inject.Inject

/**
 * class that provides helper functions for RecordingsViewModel
 */
interface RecordingsHelper {
    /**
     * variable to hold current filename
     */
    var currentFile: String

    /**
     * helper function determine whether there is an audio being played
     * @param fileName of audio
     */
    fun isSameAudio(fileName: String): Boolean
}

class RecordingsHelperImpl @Inject constructor() : RecordingsHelper {
    override var currentFile: String = ""

    override fun isSameAudio(fileName: String): Boolean {
        return fileName == currentFile
    }
}