package com.weesnerdevelopment.playingwithgames.objects

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path

abstract class Circle(
    open var pos: Vector,
    open var speed: Vector,
    open val radius: Number = 50
) {
    private val startPos by lazy { pos }
    private val startSpeed by lazy { speed }
    var path: Path? = null

    open val paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = Color.rgb(100, 100, 100)
    }

    open fun draw(canvas: Canvas) {}
    abstract fun update(width: Number, height: Number)

    fun reset() {
        pos = startPos
        speed = startSpeed
    }

    fun Canvas.drawCircle() =
        drawCircle(pos.x.toFloat(), pos.y.toFloat(), radius.toFloat(), paint)

    open fun addToPath(path: Path) {
        if (this.path == null) this.path = path

        path.addCircle(pos.x.toFloat(), pos.y.toFloat(), radius.toFloat(), Path.Direction.CW)
    }

    open fun adjustForInterpolation(interpolation: Number){
        pos += Vector(interpolation, interpolation)
    }
}