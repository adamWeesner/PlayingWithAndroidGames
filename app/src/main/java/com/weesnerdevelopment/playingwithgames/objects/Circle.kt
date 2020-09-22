package com.weesnerdevelopment.playingwithgames.objects

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import com.weesnerdevelopment.playingwithgames.math.compareTo
import com.weesnerdevelopment.playingwithgames.math.minus
import com.weesnerdevelopment.playingwithgames.math.plus
import com.weesnerdevelopment.playingwithgames.math.times

abstract class Circle(
    @Volatile
    open var pos: Vector,
    @Volatile
    open var velocity: Vector,
    open val radius: Number = 50
) {
    var path: Path? = null

    open val paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = Color.argb(100, 100, 100, 100)
    }

    open fun draw(canvas: Canvas) {}
    abstract fun update(width: Number, height: Number)

    open fun reset() {
        pos = Vector.random
        velocity = Vector.zero
    }

    fun Canvas.drawCircle() =
        drawCircle(pos.x.toFloat(), pos.y.toFloat(), radius.toFloat(), paint)

    open fun addToPath(path: Path) {
        if (this.path == null) this.path = path

        path.addCircle(pos.x.toFloat(), pos.y.toFloat(), radius.toFloat(), Path.Direction.CW)
    }

    open fun adjustForInterpolation(interpolation: Number) {
        pos + Vector(interpolation, interpolation)
    }

    /**
     * Checks for the edges and wraps around to the other side.
     */
    open fun wrapEdges(width: Number, height: Number) {
        pos.x = when {
            pos.x - radius > width -> radius
            pos.x + radius < 0 -> width - radius
            else -> pos.x
        }

        pos.y = when {
            pos.y - radius > height -> radius
            pos.y + radius < 0 -> height - radius
            else -> pos.y
        }
    }

    /**
     * Checks and "bounces" off the edge.
     */
    open fun checkEdges(width: Number, height: Number){
        when {
            pos.x > width - radius -> {
                pos.x = width - radius
                velocity.x *= -1
            }
            pos.x < 0 + radius -> {
                pos.x = radius
                velocity.x *= -1
            }
        }

        if(pos.y > height - radius){
            velocity.y *= -1
            pos.y = height - radius
        }
    }
}