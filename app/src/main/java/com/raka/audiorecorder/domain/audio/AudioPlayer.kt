package com.raka.audiorecorder.domain.audio

import android.content.Context
import android.media.MediaPlayer
import androidx.core.net.toUri
import java.io.File
import javax.inject.Inject

/**
 * AudioPlayer provides the functions to use the features of Audio player
 */
interface AudioPlayer {

    /**
     *  play the audio file
     *  @param directoryPath directory path of the file, type String
     *  @param fileName of the file, type String
     */
    fun playAudioFile(directoryPath: String, fileName: String)

    /**
     *  stop the audio file
     */
    fun stop()
}

class AudioPlayerImpl @Inject constructor(private val context: Context) : AudioPlayer {

    private var player: MediaPlayer? = null

    override fun playAudioFile(directoryPath: String, fileName: String) {
        val file = File(directoryPath, fileName)
        MediaPlayer.create(context, file.toUri()).apply {
            player = this
            start()
        }
    }

    override fun stop() {
        player?.let {
            // stop the audio file
            it.stop()
            // release the resource of audio file
            it.release()
        }
        player = null
    }
}