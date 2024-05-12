package com.raka.audiorecorder.domain.audio

import android.content.Context
import android.media.MediaRecorder
import android.os.Build
import timber.log.Timber
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import javax.inject.Inject

/**
 * AudioRecorder provides the functions to use the features of Audio recorder
 */
interface AudioRecorder {
    /**
     * start the audio recording process
     *@param dirPath directory path of the file, type String
     */
    fun start(dirPath: String, fileName: String)

    /**
     * stop the audio recording process
     */
    fun stop()
}

class AudioRecorderImpl @Inject constructor(private val context: Context) : AudioRecorder {

    private var recorder: MediaRecorder? = null

    override fun start(dirPath: String, fileName: String) {
        createRecorder().apply {
            // set properties of the audio
            setAudioSource(MediaRecorder.AudioSource.MIC)
            setOutputFormat(MediaRecorder.OutputFormat.MPEG_4)
            setAudioEncoder(MediaRecorder.AudioEncoder.AAC)
            setOutputFile(FileOutputStream(getFile(dirPath = dirPath, fileName = fileName)).fd)

            // preparing file to be recorded
            try {
                prepare()
                // starting to record
                start()
            } catch (error: IOException) {
                Timber.e(error)
            }
            recorder = this
        }
    }

    override fun stop() {
        // stop the recording process
        recorder?.stop()
        // reset the recording process so can be recorded again
        recorder?.reset()
        // set recorder to null
        recorder = null
    }

    /**
     * create a Media recorder instance based on api level
     * @return MediaRecorder
     */
    private fun createRecorder(): MediaRecorder {
        // if api level is above 31/Android 12
        // else below 31
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            MediaRecorder(context)
        } else MediaRecorder()
    }

    /**
     * get the file name and extension of the audio file
     * @param dirPath directory path of the file, String type
     * @return File
     */
    private fun getFile(dirPath: String, fileName: String): File {
        return File(dirPath, fileName)
    }
}