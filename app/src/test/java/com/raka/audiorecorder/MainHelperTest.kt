package com.raka.audiorecorder

import com.raka.audiorecorder.ui.main.MainHelper
import com.raka.audiorecorder.ui.main.MainHelperImpl
import org.junit.Assert
import org.junit.Test
import java.text.SimpleDateFormat
import java.util.Date

class MainHelperTest {
    private var sut : MainHelper = MainHelperImpl()

    @Test
    fun `return the correct filename`() {
        val expected = "audio_record_2024.05.133_11.39.mp3"
        val result = sut.getFileName("2024.05.133_11.39")
        Assert.assertEquals(expected, result)
    }

    @Test
    fun `verify the audio file type is mp3`() {
        val expected = ".mp3"
        val result = sut.getFileName("2024.05.133_11.39")
        Assert.assertEquals(expected, result.takeLast(4))
    }

    @Test
    fun `return the correct formatted duration`() {
        val expected = "00:10"
        val result = sut.getRecordingDuration("00:10:00")
        Assert.assertEquals(expected,result)
    }

    @Test
    fun `return the correct formatted date`() {
        val expected = SimpleDateFormat("yyyy.MM.DD_HH.mm.ss").format(Date())
        val result = sut.getDate()
        Assert.assertEquals(expected,result)
    }
}