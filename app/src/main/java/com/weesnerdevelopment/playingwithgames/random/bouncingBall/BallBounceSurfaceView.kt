package com.weesnerdevelopment.playingwithgames.random.bouncingBall

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import com.weesnerdevelopment.playingwithgames.game.GameSurfaceView
import com.weesnerdevelopment.playingwithgames.game.GameVariables
import com.weesnerdevelopment.playingwithgames.random.PathInfo
import kotlinx.coroutines.launch
import kotlin.random.Random.Default.nextInt

class BallBounceSurfaceView(context: Context) : GameSurfaceView(context) {
    var balls: MutableList<Ball> = mutableListOf()

    val randomColor
        get() = Color.argb(
            nextInt(100, 255),
            nextInt(255),
            nextInt(255),
            nextInt(255)
        )

    var pathsData = listOf<PathInfo>()

    private var oldPathCount = pathsData.size

    private fun resetPaths() = pathsData.forEach { it.path.reset() }

    override fun onResume() {
        pathsData = (0 until GameVariables.pathColorCount.value).map {
            PathInfo(Path(), Paint(Paint.ANTI_ALIAS_FLAG).apply { color = randomColor })
        }
    }

    override fun onRender(canvas: Canvas) {
        resetPaths()

        canvas.drawColor(Color.WHITE)

        drawing = true
        try {
            balls.forEach { ball ->
                val ballPath = if (oldPathCount != pathsData.size) {
                    pathsData[nextInt(pathsData.size)].also {
                        ball.path = it.path
                    }
                } else {
                    pathsData.firstOrNull { it.path == ball.path }
                        ?: pathsData[nextInt(pathsData.size)]
                }

                ball.draw(ballPath.path, looper.interpolation)
            }
        } catch (e: ConcurrentModificationException) {
            println("Cannot modify from multiple places at the same time...")
        }
        if (oldPathCount != pathsData.size) oldPathCount = pathsData.size
        drawing = false

        pathsData.forEach {
            canvas.drawPath(it.path, it.paint)
        }

        canvas.drawFPSInfo("Balls: ${balls.size}")
    }

    override fun onUpdate() {
        scope?.launch {
            drawing = true
            balls.forEach { ball ->
                ball.updateOffset(screenWidth!!, screenHeight!!)
            }
            drawing = false

        }
    }

    override fun onDestroy() {
        balls = mutableListOf()
        oldPathCount = 0
        pathsData = listOf()
    }

    fun addBall(ball: Ball) {
        scope?.launch {
            if (!drawing) balls.add(ball)

        }
    }
}