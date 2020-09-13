package com.weesnerdevelopment.playingwithgames.random.circlePhysics.collision

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

class CircleCollisionPhysicsSurfaceView(context: Context) : GameSurfaceView(context) {
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
                val path =
                    if (ball.path == null) pathsData[nextInt(pathsData.size)].path else ball.path!!

                ball.addToPath(path)
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
            balls.forEachIndexed { index, ball ->
                ball.update(screenWidth!!, screenHeight!!)
                ball.adjustForInterpolation(looper.interpolation)

                if (!ball.hitBottom) ball.checkHitBottom(screenHeight!!)

                val nextItem = if (index + 1 != balls.size) balls[index + 1] else null

                if (nextItem != null) ball.checkCollision(nextItem)
            }
            drawing = false
        }
    }

    override fun onDestroy() {
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
