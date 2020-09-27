package com.weesnerDevelopment.playingWithGames.objects

import android.graphics.Canvas
import com.weesnerDevelopment.gameEngine.math.Vector
import com.weesnerDevelopment.gameEngine.math.minus
import com.weesnerDevelopment.gameEngine.math.outsideOf
import com.weesnerDevelopment.gameEngine.math.times

data class Ball(
    override var pos: Vector,
    override var velocity: Vector,
    @Volatile
    var acceleration: Vector = Vector(-0.001, 0.01),
    override val radius: Number = 50,
    val topSpeed: Number = 10
) : Circle(pos, velocity, radius) {
    override fun draw(canvas: Canvas) {
        canvas.drawCircle()
    }

    override fun update(width: Number, height: Number) {
        velocity + acceleration

        if (pos.x.outsideOf(radius, width - radius)) velocity.x *= -1
        if (pos.y.outsideOf(radius, height - radius)) velocity.y *= -1

        pos + velocity
    }

    fun updatePos() {
        updateTowardPoint(acceleration)
    }

    fun updateTowardPoint(point: Vector){
        velocity + point
        velocity.limit(topSpeed)
        pos + velocity
    }

    fun updateWrap(width: Number, height: Number){
        updatePos()
        wrapEdges(width, height)
    }
}
