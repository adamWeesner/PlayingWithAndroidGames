package com.weesnerdevelopment.playingwithgames.game

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.SurfaceHolder
import android.view.SurfaceView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.cancel
import java.text.NumberFormat

abstract class GameSurfaceView : SurfaceView, Runnable {
    lateinit var loopType: GameLoopType
    lateinit var looper: GameLooper

    var hardwareAccelerated = true

    protected var drawing = false
    protected var scope: CoroutineScope? = null
    protected var screenWidth: Int? = null
    protected var screenHeight: Int? = null
    protected val textPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        textSize = 60f
        color = Color.BLACK
    }
    protected val textBackgroundPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = Color.WHITE
        alpha = 200
    }

    private var gameThread: Thread? = null

    private var isRunning = false
    private var surfaceHolder: SurfaceHolder = holder

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

    override fun run() {
        while (isRunning) {
            // verify the surface is ready
            if (!surfaceHolder.surface.isValid) continue

            Timer.start()

            looper.loop(
                update = {
                    update()
                },
                draw = {
                    val canvas =
                        if (hardwareAccelerated) surfaceHolder.lockHardwareCanvas()
                        else surfaceHolder.lockCanvas()

                    if (canvas != null) {
                        render(canvas)
                        surfaceHolder.unlockCanvasAndPost(canvas)
                    }
                }
            )
        }
    }

    protected fun Canvas.drawFPSInfo(countLabel: String){
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
        onRender(canvas)
    }


    private fun update() {
        if (screenWidth != null && screenHeight != null)
            onUpdate()
    }

    fun resume() {
        looper = when (loopType) {
            GameLoopType.AS_FAST_AS_POSSIBLE -> GameLooperNoLimits
            GameLoopType.CONSTANT_GAME_SPEED -> GameLooperFPSDependentOnGameSpeed
            GameLoopType.VARIABLE_FPS -> GameLooperGameSpeedDependentOnVariableFPS
            GameLoopType.MAX_FPS -> GameLooperConstantGameSpeedWithMaxFps
            GameLoopType.CONSTANT_GAME_SPEED_VARIABLE_FPS -> GameLooperConstantGameSpeedIndependentOfVariableFPS
        }

        scope = MainScope()
        isRunning = true
        gameThread = Thread(this)
        gameThread?.start()

        onResume()
    }

    fun pause() {
        scope?.cancel()
        scope = null
        isRunning = false
        var retry = true

        while (retry) {
            try {
                gameThread?.join()
                surfaceHolder.surface.release()
                retry = false
            } catch (e: InterruptedException) {
                println("interruption happened")
            }
        }
    }


    fun destroy() {
        clear()
        onDestroy()
    }

    fun clear() {
        scope?.cancel()
        scope = null
        isRunning = false
        gameThread?.interrupt()
        gameThread = null
        onClear()
    }

    open fun onSurfaceChanged() {}
    open fun onRender(canvas: Canvas) {}
    open fun onUpdate() {}
    open fun onResume() {}
    open fun onClear() {}
    open fun onDestroy() {}
}
