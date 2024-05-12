package com.raka.audiorecorder

import com.raka.audiorecorder.domain.timer.TimerHelper
import com.raka.audiorecorder.domain.timer.TimerHelperImpl
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class TimerHelperTest {

    // sut = system under test
    private lateinit var sut: TimerHelper

    @Before
    fun setup() {
        sut = TimerHelperImpl()
    }

    @Test
    fun `return the correct formatted time`(): Unit = runBlocking {
        val expected = "00:01:00"
        val result = sut.formatTime(1000)
        Assert.assertEquals(expected, result)
    }

    @Test
    fun `return the reset time when value inputted is 0`(): Unit = runBlocking {
        val expected = "00:00:00"
        val result = sut.formatTime(0)
        Assert.assertEquals(expected, result)
    }
}