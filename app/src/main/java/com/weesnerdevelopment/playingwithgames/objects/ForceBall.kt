package com.weesnerdevelopment.playingwithgames.objects

import android.graphics.Canvas

data class ForceBall(
    override var pos: Vector,
    override var velocity: Vector = Vector(0,0),
    val acceleration: Vector = Vector(0, 0),
    override val radius: Number = 50,
    val mass: Number = 2
) : Circle(pos, velocity, radius) {
    override fun draw(canvas: Canvas) {
        canvas.drawCircle()
    }

    override fun update(width: Number, height: Number) {
        velocity + acceleration
        pos + velocity
        acceleration * 0
    }

    fun applyForce(force: Vector){
        val forceToUse = Vector.div(force, mass)
        acceleration + forceToUse
    }
}
