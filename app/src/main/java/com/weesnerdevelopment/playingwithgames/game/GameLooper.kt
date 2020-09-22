package com.weesnerdevelopment.playingwithgames.game

import com.weesnerdevelopment.playingwithgames.math.*

sealed class GameLooper {
    var interpolation: Number = 0
        protected set

    abstract suspend fun loop(update: () -> Unit, draw: suspend () -> Unit)

    protected fun trySleep(time: Number) {
        try {
            Thread.sleep(time.toLong())
        } catch (e: InterruptedException) {
            println("interruption happened trying to sleep")
        }
    }
}

/**
 * This looper is basically not throttled, it runs as fast as the hardware will update and draw the
 * data, the downside to this is that there are potentially a lot of unnecessary loops since the ui
 * cannot keep up with the game loop.
 */
object GameLooperNoLimits : GameLooper() {
    override suspend fun loop(update: () -> Unit, draw: suspend () -> Unit) {
        update()
        draw()
    }
}

/**
 * This looper runs at a constant game speed. It can run to slow or fast depending on the hardware,
 * it can appear to be janky since the game speed can vary so much.
 */
object GameLooperFPSDependentOnGameSpeed : GameLooper() {
    private var nextFrame: Number = Timer.now
    private var sleepTime: Number = 0

    override suspend fun loop(update: () -> Unit, draw: suspend () -> Unit) {
        update()
        draw()

        nextFrame += Timer.framePeriod
        sleepTime = nextFrame - Timer.now

        if (sleepTime >= 0)
            trySleep(sleepTime)
    }
}

/**
 * This looper runs as fast as possible letting the FPS dictate the game speed. The loop is updated
 * with the time difference of the previous frame.
 */
object GameLooperGameSpeedDependentOnVariableFPS : GameLooper() {
    private var previousFrame: Number = 0
    private var currentFrame: Number = Timer.now

    override suspend fun loop(update: () -> Unit, draw: suspend () -> Unit) {
        previousFrame = currentFrame
        currentFrame = Timer.now

        var frames = currentFrame - previousFrame
        while (frames >= 0) {
            update()
            frames--
        }
        draw()
    }
}

/**
 * This looper runs at a constant rate of the FPS. Trying to use high FPS on slow hardware will cause
 * a lot of jank since it will have to work harder to maintain the FPS.
 */
object GameLooperConstantGameSpeedWithMaxFps : GameLooper() {
    private var nextFrame: Number = Timer.now
    private var loops: Number = 0

    override suspend fun loop(update: () -> Unit, draw: suspend () -> Unit) {
        loops = 0
        while (Timer.now > nextFrame && loops < Timer.maxFrameSkip) {
            update()

            nextFrame += Timer.framePeriod
            loops++
        }

        draw()
    }
}

/**
 * This looper runs at the most optimal FPS and constant game speed, introduces an interpolation
 * difference to handle frame skip to help reduce jank and make things draw smoother.
 */
object GameLooperConstantGameSpeedIndependentOfVariableFPS : GameLooper() {
    private var nextFrame: Number = Timer.now
    private var loops: Number = 0

    override suspend fun loop(update: () -> Unit, draw: suspend () -> Unit) {
        loops = 0
        while (Timer.now > nextFrame && loops < Timer.maxFrameSkip) {
            update()

            nextFrame += Timer.framePeriod
            loops++
        }

        interpolation =
            (Timer.now + Timer.maxFrameSkip - nextFrame) / Timer.maxFrameSkip

        draw()
    }
}
