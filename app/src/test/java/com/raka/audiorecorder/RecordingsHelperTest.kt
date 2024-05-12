package com.raka.audiorecorder

import com.raka.audiorecorder.ui.recordings.RecordingsHelper
import com.raka.audiorecorder.ui.recordings.RecordingsHelperImpl
import org.junit.Assert
import org.junit.Test

class RecordingsHelperTest {

    private var sut: RecordingsHelper = RecordingsHelperImpl()

    @Test
    fun `return true when audio file is the same`() {
        sut.currentFile = "filename1"
        val result = sut.isSameAudio("filename1")
        Assert.assertEquals(true, result)
    }

    @Test
    fun `return false when audio file is different`() {
        sut.currentFile = "filename0"
        val result = sut.isSameAudio("filename1")
        Assert.assertEquals(false, result)
    }
}