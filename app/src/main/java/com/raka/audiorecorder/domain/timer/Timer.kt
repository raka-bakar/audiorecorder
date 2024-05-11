package com.raka.audiorecorder.domain.timer

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancelChildren
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 *  Timer provides duration data of the audio record
 */
interface Timer {
    /**
     * variable to emit the ticking timer
     */
    val ticker: StateFlow<String>

    /**
     * start timer
     */
    fun startTimer()

    /**
     *  stop timer
     */
    fun stopTimer()
}

class TimerImpl @Inject constructor(private val scope: CoroutineScope) : Timer {
    private var job: Job? = null
    private val mutableTicker = MutableStateFlow(START_VALUE)
    override val ticker: StateFlow<String> = mutableTicker

    private var duration = 0L
    private var delay = 100L

    override fun startTimer() {
        startJob()
    }


    override fun stopTimer() {
        stopJob()
        resetTicker()
    }

    /**
     * start job and emit new elapsed time every 100 millisecond
     */
    private fun startJob() {
        job = scope.launch {
            while (isActive) {
                duration += delay
                mutableTicker.value = formatTime(duration)
                delay(DELAY_DURATION)
            }
        }
    }

    /**
     * cancelling job
     */
    private fun stopJob() {
        scope.coroutineContext.cancelChildren()
        job = null
    }

    /**
     * reset the value of ticker into an empty string
     */
    private fun resetTicker() {
        duration = 0L
        mutableTicker.value = START_VALUE
    }

    /**
     * formatting duration into hour
     */
    private fun formatTime(value: Long): String {
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
        private const val DELAY_DURATION = 100L
        private const val TWO_DIGITS_FORMATTER = 10
        private const val START_VALUE = "00:00:00"
    }
}