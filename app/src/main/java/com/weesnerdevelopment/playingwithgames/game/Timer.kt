package com.weesnerdevelopment.playingwithgames.game

import android.os.SystemClock
import math.*

object Timer {
    val maxFps: Number get() = GameVariables.fps.value
    val framePeriod: Number = 1000 / maxFps
    val maxFrameSkip: Number = 10

    val now get() = System.currentTimeMillis()

    @Volatile
    var startedAt = SystemClock.elapsedRealtime()
    @Volatile
    var started: Number = 0
    @Volatile
    var avgFps: Number = 0

    fun start() {
        startedAt = SystemClock.elapsedRealtime()
        started = now
    }

    private fun calcFrameTime() = (SystemClock.elapsedRealtime() - startedAt).absoluteValue

    fun calcFps(): Number {
        val frameTime = calcFrameTime()
        return maxFps / if (frameTime == 0L) 1 else frameTime
    }

    fun calcAvgFps(): Number {
        avgFps += (calcFps() - avgFps) * 0.03
        return avgFps
    }
}
