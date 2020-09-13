package com.weesnerdevelopment.playingwithgames.objects

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import math.compareTo
import math.minus
import math.plus

abstract class Circle(
    open var pos: Vector,
    open var velocity: Vector,
    open val radius: Number = 50
) {
    private val startPos by lazy { pos }
    private val startSpeed by lazy { velocity }
    var path: Path? = null

    open val paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = Color.rgb(100, 100, 100)
    }

    open fun draw(canvas: Canvas) {}
    abstract fun update(width: Number, height: Number)

    open fun reset() {
        pos = startPos
        velocity = startSpeed
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
}