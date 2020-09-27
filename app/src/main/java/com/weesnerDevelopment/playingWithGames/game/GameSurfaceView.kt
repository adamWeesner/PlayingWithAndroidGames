package com.weesnerDevelopment.playingWithGames.game

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.SurfaceHolder
import android.view.SurfaceView
import kotlinx.coroutines.*
import java.text.NumberFormat

abstract class GameSurfaceView : SurfaceView {
    lateinit var loopType: GameLoopType
    lateinit var looper: GameLooper

    var hardwareAccelerated = true

    @Volatile
    protected var drawing = false
    protected var scope: CoroutineScope? = null
    protected var screenWidth: Int? = null
    protected var screenHeight: Int? = null

    @Volatile
    protected var isRunning = false

    private val surfaceHolder: SurfaceHolder = holder
    private val textPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        textSize = 60f
        color = Color.BLACK
    }
    private val textBackgroundPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = Color.WHITE
        alpha = 200
    }

    constructor(c: Context) : super(c) {
        surfaceHolder.addCallback(object : SurfaceHolder.Callback {
            override fun surfaceCreated(p0: SurfaceHolder) {}

            override fun surfaceChanged(
                holder: SurfaceHolder,
                format: Int,
                width: Int,
                height: Int
            ) {
                screenWidth = width
                screenHeight = height

                onSurfaceChanged()
            }

            override fun surfaceDestroyed(p0: SurfaceHolder) {}
        })
    }

    constructor(c: Context, attrs: AttributeSet) : super(c, attrs)

    constructor(c: Context, attrs: AttributeSet, defAttrs: Int) : super(c, attrs, defAttrs)

    protected fun Canvas.drawFPSInfo(countLabel: String) {
        val avgFpsString = "Avg Fps: ${NumberFormat.getInstance().format(Timer.calcAvgFps())}"
        drawRect(0f, 0f, avgFpsString.length * 30f + 20f, 200f, textBackgroundPaint)
        text("Fps: ${NumberFormat.getInstance().format(Timer.calcFps())}", 60f)
        text(avgFpsString, 120f)
        text(countLabel, 180f)
    }

    protected fun Canvas.text(label: String, yPos: Float) {
        drawText(label, 15f, yPos, textPaint)
    }


    private fun render(canvas: Canvas) {
        if (isRunning)
            onRender(canvas)
    }


    private fun update() {
        if (isRunning && screenWidth != null && screenHeight != null)
            onUpdate()
    }

    private suspend fun runGameLoop() {
        if (!isRunning) return
        // verify the surface is ready
        if (!surfaceHolder.surface.isValid) return

        Timer.start()

        looper.loop(
            update = {
                update()
            },
            draw = {
                val canvas =
                    if (hardwareAccelerated) surfaceHolder.lockHardwareCanvas()
                    else surfaceHolder.lockCanvas()

                canvas?.let {
                    render(it)
                    surfaceHolder.unlockCanvasAndPost(it)
                }
            }
        )
        runGameLoop()
    }

    open fun resume() {
        if (isRunning) return

        isRunning = true
        scope = MainScope()
        looper = when (loopType) {
            GameLoopType.AS_FAST_AS_POSSIBLE -> GameLooperNoLimits
            GameLoopType.CONSTANT_GAME_SPEED -> GameLooperFPSDependentOnGameSpeed
            GameLoopType.VARIABLE_FPS -> GameLooperGameSpeedDependentOnVariableFPS
            GameLoopType.MAX_FPS -> GameLooperConstantGameSpeedWithMaxFps
            GameLoopType.CONSTANT_GAME_SPEED_VARIABLE_FPS -> GameLooperConstantGameSpeedIndependentOfVariableFPS
        }

        scope?.launch {
            withContext(Dispatchers.IO) {
                runGameLoop()
            }
        }
    }

    open fun pause() {
        if (!isRunning) return
        clear()
    }


    open fun stop() {
        if (!isRunning) return
        clear()
    }

    open fun clear() {
        if (!isRunning) return

        isRunning = false
        scope?.cancel()
        scope = null
    }

    open fun onSurfaceChanged() {}
    open fun onRender(canvas: Canvas) {}
    open fun onUpdate() {}
}
