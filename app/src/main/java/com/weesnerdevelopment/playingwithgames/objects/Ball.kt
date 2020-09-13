package com.weesnerdevelopment.playingwithgames.objects

import android.graphics.Canvas
import math.between
import math.minus
import math.times

data class Ball(
    override var pos: Vector,
    override var speed: Vector,
    override val radius: Number = 50
) : Circle(pos, speed, radius) {
    override fun draw(canvas: Canvas) {
        canvas.drawCircle()
    }

    override fun update(width: Number, height: Number) {
        if (pos.x.between(radius, width - radius)) speed.x *= -1
        if (pos.y.between(radius, height - radius)) speed.y *= -1

        pos += speed
    }
}
