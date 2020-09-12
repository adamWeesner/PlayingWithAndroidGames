package com.weesnerdevelopment.playingwithgames.random.circlePhysics

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import com.weesnerdevelopment.playingwithgames.game.GameSurfaceView
import com.weesnerdevelopment.playingwithgames.game.GameVariables
import kotlinx.coroutines.launch
import kotlin.random.Random.Default.nextInt

data class PathInfo(
    val path: Path,
    val paint: Paint
)

class CirclePhysicsSurfaceView(context: Context) : GameSurfaceView(context) {
    var circles: List<Circle> = listOf()

    val randomColor
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
            circles.forEach { circle ->
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

        canvas.drawFPSInfo("Circles: ${circles.size}")
    }

    override fun onUpdate() {
        scope?.launch {
            drawing = true
            circles.forEach { circle ->
                circle.update(looper.interpolation)
                circle.checkHitBottom(screenHeight!!)
            }
            drawing = false
            circles = circles.filter { !it.hitBottom }
        }
    }

    override fun onDestroy() {
        circles = listOf()
        oldPathCount = 0
        pathsData = listOf()
    }

    fun addCircle(circle: Circle) {
        scope?.launch {
            if (!drawing) circles = circles + circle
        }
    }
}
