package com.weesnerdevelopment.playingwithgames.buildingAndroidGames.graphics

import android.graphics.Bitmap
import android.graphics.Rect
import android.view.SurfaceHolder
import android.view.SurfaceView
import com.weesnerdevelopment.playingwithgames.buildingAndroidGames.game.AndroidGame

class AndroidRenderView(
    private val game: AndroidGame,
    private val frameBuffer: Bitmap,
    private val hardwareAccelerated: Boolean
) : SurfaceView(game), Runnable {
    private var renderThread: Thread? = null
    private val surfaceHolder: SurfaceHolder = holder

    @Volatile
    var isRunning: Boolean = false

    fun resume() {
        isRunning = true
        renderThread = Thread(this)
        renderThread?.start()
    }

    override fun run() {
        val destRect = Rect()
        var startTime: Long = System.nanoTime()

        while (isRunning) {
            if (!surfaceHolder.surface.isValid) continue

            val deltaTime: Float = (System.nanoTime() - startTime) / 100_0000_000.0f
            startTime = System.nanoTime()

            game.screen.apply {
                update(deltaTime)
                present(deltaTime)
            }

            val canvas =
                if (hardwareAccelerated) holder.lockHardwareCanvas()
                else holder.lockCanvas()

            canvas.apply {
                getClipBounds(destRect)
                drawBitmap(frameBuffer, null, destRect, null)
            }
            holder.unlockCanvasAndPost(canvas)
        }
    }

    fun pause() {
        isRunning = false
        while (true) {
            try {
                renderThread?.join()
                return
            } catch (e: InterruptedException) {
                e.printStackTrace()
            }
        }
    }
}
