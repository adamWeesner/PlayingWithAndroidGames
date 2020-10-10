package com.weesnerDevelopment.randomGameEngine.objects

import com.weesnerDevelopment.gameEngine.math.*

data class PhysicsBall(
    override var position: Vector2D,
    override val radius: Number,
    val gravity: Vector2D = Vector2D(0, radius * 0.01),
    override var velocity: Vector2D = Vector2D(0, 0)
) : Circle(position, velocity, radius) {
    var hitBottom = false

    override fun update(width: Number, height: Number) {
        if (hitBottom) velocity.y = 0
        else velocity += gravity

        position += velocity
    }

    override fun adjustForInterpolation(interpolation: Number) {
        if (!hitBottom) position.y += interpolation
    }

    fun checkHitBottom(bottom: Number) {
        if (position.y - radius >= bottom) {
            hitBottom = true
            position.y = bottom - radius
        }
    }
}