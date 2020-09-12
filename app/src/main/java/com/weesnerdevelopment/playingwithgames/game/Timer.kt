package com.weesnerdevelopment.playingwithgames.game

import android.os.SystemClock

object Timer {
    const val maxFps = 60
    const val framePeriod = 1000 / maxFps
    const val maxFrameSkip = 10

    val now get() = System.currentTimeMillis()

    var startedAt = SystemClock.elapsedRealtime()
    var started = 0L
    var avgFps = 0.0

    fun start() {
        startedAt = SystemClock.elapsedRealtime()
        started = now
    }

    fun calcFrameTime() = SystemClock.elapsedRealtime() - startedAt

    fun calcFps() =
        maxFps.toDouble() / if (calcFrameTime() == 0L) 1L else calcFrameTime()

    fun calcAvgFps(): Double {
        avgFps += (calcFps() - avgFps) * 0.03
        return avgFps
    }
}
