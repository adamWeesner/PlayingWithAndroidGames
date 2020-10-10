package com.weesnerDevelopment.randomGameEngine.objects

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import com.weesnerDevelopment.gameEngine.math.*
import com.weesnerDevelopment.gameEngine.objects.Transform
import kotlin.random.Random

abstract class Circle(
    override var position: Vector2D,
    @Volatile
    open var velocity: Vector2D,
    open val radius: Number = 50
): Transform(position) {
    var path: Path? = null

    open val paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = Color.argb(100, 100, 100, 100)
    }

    open fun draw(canvas: Canvas) {}
    abstract fun update(width: Number, height: Number)

    open fun reset() {
        position = Vector2D(Random.nextInt(10, 1000), Random.nextInt(10, 1000))
        velocity = Vector2D(0, 0)
    }

    fun Canvas.drawCircle() =
        drawCircle(position.x.toFloat(), position.y.toFloat(), radius.toFloat(), paint)

    open fun addToPath(path: Path) {
        if (this.path == null) this.path = path

        path.addCircle(position.x.toFloat(), position.y.toFloat(), radius.toFloat(), Path.Direction.CW)
    }

    open fun adjustForInterpolation(interpolation: Number) {
        position += Vector2D(interpolation, interpolation)
    }

    /**
     * Checks for the edges and wraps around to the other side.
     */
    open fun wrapEdges(width: Number, height: Number) {
        position.x = when {
            position.x - radius > width -> radius
            position.x + radius < 0 -> width - radius
            else -> position.x
        }

        position.y = when {
            position.y - radius > height -> radius
            position.y + radius < 0 -> height - radius
            else -> position.y
        }
    }

    /**
     * Checks and "bounces" off the edge.
     */
    open fun checkEdges(width: Number, height: Number) {
        when {
            position.x > width - radius -> {
                position.x = width - radius
                velocity.x *= -1
            }
            position.x < 0 + radius -> {
                position.x = radius
                velocity.x *= -1
            }
        }

        if (position.y > height - radius) {
            velocity.y *= -1
            position.y = height - radius
        }
    }
}