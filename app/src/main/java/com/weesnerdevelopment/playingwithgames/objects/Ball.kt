package com.weesnerdevelopment.playingwithgames.objects

import android.graphics.Canvas
import math.outsideOf
import math.minus
import math.times

data class Ball(
    override var pos: Vector,
    override var velocity: Vector,
    val acceleration: Vector = Vector(-0.001, 0.01),
    override val radius: Number = 50,
    val topSpeed: Number = 100
) : Circle(pos, velocity, radius) {
    override fun draw(canvas: Canvas) {
        canvas.drawCircle()
    }

    override fun update(width: Number, height: Number) {
        velocity + acceleration

        if (pos.x.outsideOf(radius, width - radius)) velocity.x *= -1
        if (pos.y.outsideOf(radius, height - radius)) velocity.y *= -1

        pos += velocity
    }

    fun updatePos() {
        velocity + acceleration
        velocity.limit(topSpeed)
        pos + velocity
    }

    fun updateWrap(width: Number, height: Number){
        updatePos()
        wrapEdges(width, height)
    }
}
