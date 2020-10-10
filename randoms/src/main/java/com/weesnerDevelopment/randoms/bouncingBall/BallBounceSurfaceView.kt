package com.weesnerDevelopment.randoms.bouncingBall

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import com.weesnerDevelopment.randomGameEngine.game.GameSurfaceView
import com.weesnerDevelopment.randomGameEngine.game.GameVariables
import com.weesnerDevelopment.randomGameEngine.objects.Ball
import com.weesnerDevelopment.randomGameEngine.objects.PathInfo
import kotlinx.coroutines.launch
import java.lang.IllegalArgumentException
import kotlin.random.Random.Default.nextInt

class BallBounceSurfaceView(context: Context) : GameSurfaceView(context) {
    var balls: MutableList<Ball> = mutableListOf()

    private val randomColor
        get() = Color.argb(
            nextInt(100, 255),
            nextInt(255),
            nextInt(255),
            nextInt(255)
        )

    var pathsData = listOf<PathInfo>()

    private var oldPathCount = pathsData.size

    private fun resetPaths() = pathsData.forEach { it.path.reset() }

    override fun resume() {
        super.resume()
        pathsData = (0 until GameVariables.pathColorCount.value!!).map {
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

                ball.addToPath(ballPath.path)
            }
        } catch (e: ConcurrentModificationException) {
            println("Cannot modify from multiple places at the same time...")
        } catch (e: IllegalArgumentException) {
            println("Somehow an argument was illegal ${e.stackTrace}")
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
                ball.adjustForInterpolation(looper.interpolation)
                ball.update(screenWidth!!, screenHeight!!)
            }
            drawing = false

        }
    }

    override fun clear() {
        super.clear()
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
