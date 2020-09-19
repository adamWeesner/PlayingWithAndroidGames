package com.weesnerdevelopment.playingwithgames.random.circlePhysics

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import com.weesnerdevelopment.playingwithgames.game.GameSurfaceView
import com.weesnerdevelopment.playingwithgames.game.GameVariables
import com.weesnerdevelopment.playingwithgames.objects.PathInfo
import com.weesnerdevelopment.playingwithgames.objects.PhysicsBall
import kotlinx.coroutines.launch
import kotlin.random.Random.Default.nextInt

class CirclePhysicsSurfaceView(context: Context) : GameSurfaceView(context) {
    var balls: List<PhysicsBall> = listOf()

    private val randomColor
        get() = Color.argb(
            nextInt(150, 255),
            nextInt(255),
            nextInt(255),
            nextInt(255)
        )

    var pathsData = listOf<PathInfo>()

    private var oldPathCount = pathsData.size

    private fun resetPaths() = pathsData.forEach { it.path.reset() }

    override fun resume() {
        super.resume()
        pathsData = (0 until GameVariables.pathColorCount.value.toInt()).map {
            PathInfo(Path(), Paint(Paint.ANTI_ALIAS_FLAG).apply { color = randomColor })
        }
    }

    override fun onRender(canvas: Canvas) {
        resetPaths()

        canvas.drawColor(Color.WHITE)

        drawing = true
        try {
            balls.forEach { circle ->
                val path =
                    if (circle.path == null) pathsData[nextInt(pathsData.size)].path else circle.path!!

                circle.addToPath(path)
            }
        } catch (e: ConcurrentModificationException) {
            println("Cannot modify from multiple places at the same time...")
        }
        if (oldPathCount != pathsData.size) oldPathCount = pathsData.size
        drawing = false

        pathsData.forEach {
            canvas.drawPath(it.path, it.paint)
        }

        canvas.drawFPSInfo("Circles: ${balls.size}")
    }

    override fun onUpdate() {
        scope?.launch {
            drawing = true
            balls.forEach { circle ->
                circle.update(screenWidth!!, screenHeight!!)
                circle.adjustForInterpolation(looper.interpolation)
                circle.checkHitBottom(screenHeight!!)
            }
            drawing = false
            balls = balls.filter { !it.hitBottom }
        }
    }

    override fun clear() {
        super.clear()
        balls = listOf()
        oldPathCount = 0
        pathsData = listOf()
    }

    fun addCircle(ball: PhysicsBall) {
        scope?.launch {
            if (!drawing) balls = balls + ball
        }
    }
}
