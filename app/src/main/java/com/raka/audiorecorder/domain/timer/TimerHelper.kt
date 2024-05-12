package com.raka.audiorecorder.domain.timer

import javax.inject.Inject

/**
 * class that provides helper functions for Timer
 */
interface TimerHelper {
    /**
     * formatting millis to hours
     * @param value milisecond value that will be formatted
     */
    fun formatTime(value: Long): String
}

class TimerHelperImpl @Inject constructor() : TimerHelper {
    override fun formatTime(value: Long): String {
        if (value == 0L)
            return "00:00:00"

        val millis = value % 1000
        val seconds = (value / 1000) % 60
        val minutes = (value / (1000 * 60)) % 60
        val hours = (value / (1000 * 60 * 60))

        val formatted = if (hours > 0)
            "%02d:%02d:%02d.%02d".format(hours, minutes, seconds, millis / TWO_DIGITS_FORMATTER)
        else
            "%02d:%02d:%02d".format(minutes, seconds, millis / TWO_DIGITS_FORMATTER)
        return formatted
    }

    companion object {
        private const val TWO_DIGITS_FORMATTER = 10
    }
}