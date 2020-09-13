package com.weesnerdevelopment.playingwithgames.game

import android.os.SystemClock
import math.minus
import math.plus
import math.times

object Timer {
    val maxFps = 60
    val framePeriod = 1000 / maxFps
    val maxFrameSkip = 10

    val now get() = System.currentTimeMillis()

    var startedAt = SystemClock.elapsedRealtime()
    var started: Number = 0
    var avgFps: Number = 0

    fun start() {
        startedAt = SystemClock.elapsedRealtime()
        started = now
    }

    fun calcFrameTime() = SystemClock.elapsedRealtime() - startedAt

    fun calcFps() =
        maxFps / if (calcFrameTime() == 0L) 1 else calcFrameTime()

    fun calcAvgFps(): Number {
        avgFps += (calcFps() - avgFps) * 0.03
        return avgFps
    }
}
