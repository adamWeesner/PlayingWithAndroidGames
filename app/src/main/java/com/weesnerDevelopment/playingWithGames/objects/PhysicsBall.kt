package com.weesnerDevelopment.playingWithGames.objects

import com.weesnerDevelopment.gameEngine.math.*

data class PhysicsBall(
    override var pos: Vector,
    override val radius: Number,
    val gravity: Vector = Vector(0, radius * 0.01),
    override var velocity: Vector = Vector.zero
) : Circle(pos, velocity, radius) {
    var hitBottom = false
    var collided = false

    override fun update(width: Number, height: Number) {
        if (hitBottom) velocity.y = 0
        else velocity + gravity

        pos + velocity
    }

    override fun adjustForInterpolation(interpolation: Number) {
        if (!hitBottom) pos.y += interpolation
    }

    fun checkHitBottom(bottom: Number) {
        if (pos.y - radius >= bottom) {
            hitBottom = true
            pos.y = bottom - radius
        }
    }

    fun checkCollision(other: PhysicsBall) {
        val distance = pos.distance(other.pos)
        if (distance <= radius + other.radius) {
            val xDistance = (pos.x - other.pos.x).absoluteValue
            val yDistance = (pos.y - other.pos.y).absoluteValue

            val distance = sqrt(xDistance.pow(2) + yDistance.pow(2))

            if (distance <= (radius + other.radius).pow(2)) {
                collided = true
                other.collided = true
            }
        }
    }
}