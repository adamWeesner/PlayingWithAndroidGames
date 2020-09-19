package com.weesnerdevelopment.playingwithgames.objects

import android.graphics.Canvas

data class ForceBall(
    @Volatile
    override var pos: Vector,
    @Volatile
    override var velocity: Vector = Vector.zero,
    @Volatile
    var acceleration: Vector = Vector.zero,
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

    override fun reset() {
        pos = Vector.random
        velocity = Vector.zero
        acceleration * 0
    }
}
